package com.mstage.appkit.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.mstage.appkit.AppKitApplication;
import com.mstage.appkit.R;
import com.mstage.appkit.data.store.ConfigurationStore;
import com.mstage.appkit.databinding.ActivityMainScreenBinding;
import com.mstage.appkit.fragment.PageFragment;
import com.mstage.appkit.model.MainScreenConfig;
import com.mstage.appkit.model.PageConfig;
import com.mstage.appkit.util.Preconditions;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Khang NT on 5/9/17.
 * Email: khang.neon.1997@gmail.com
 */

public class MainScreen extends BaseActivity {
    public static final String EXTRA_CONFIG_DATA = "extra:config_data";
    public static final String KEY_MESSAGE_ID = "key:message_id";

    @Inject
    ConfigurationStore mConfigurationStore;

    private ActivityMainScreenBinding mBinding;
    private PagerAdapter mPagerAdapter;
    private List<PageConfig> pagesConfig;
    private Disposable mPagesConnectionDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppKitApplication.getAppKitInjector(this).inject(this);


        MainScreenConfig mainScreenConfig = getIntent().getParcelableExtra(EXTRA_CONFIG_DATA);
        if (mainScreenConfig == null)
            mainScreenConfig = mConfigurationStore.getMainScreenConfig().blockingFirst();
        Preconditions.checkNotNull(mainScreenConfig);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_screen);
        mBinding.setConfig(mainScreenConfig);

        setStatusBarConfig(mainScreenConfig.getStatusBarConfig());

        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        // note: have to manual set toolbar icons tint color
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu_black_24dp, getTheme());
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable.mutate(), mainScreenConfig.getTopBarConfig().getIconColor(Color.WHITE));
        mBinding.toolbar.setNavigationIcon(drawable);

        mConfigurationStore
                .getMainScreenConfig()
                .skip(1)
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(config -> {
                    getIntent().putExtra(EXTRA_CONFIG_DATA, config);
                    recreate();
                }, Throwable::printStackTrace);



        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mBinding.libraryPager.setAdapter(mPagerAdapter);
        mBinding.libraryPager.addOnPageChangeListener(mPagerAdapter);

        loadPages();


    }

    void loadPages() {
        mBinding.progressWheel.setVisibility(View.VISIBLE);
        mBinding.drawerLayout.setEnabled(false);
        if (mPagesConnectionDisposable != null) mPagesConnectionDisposable.dispose();
        mPagesConnectionDisposable = mConfigurationStore.getPagesConfig()
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pageConfigs -> {
                    this.pagesConfig = pageConfigs;
                    this.mPagerAdapter.notifyDataSetChanged();
                    if (pageConfigs.size() > 0) {
                        this.mBinding.libraryPager.setCurrentItem(0);
                        this.mBinding.topBarTitle.setText(pageConfigs.get(0).getTitle());
                    }
                    mBinding.progressWheel.setVisibility(View.GONE);
                    mBinding.drawerLayout.setEnabled(true);
                }, throwable -> {
                    Snackbar.make(mBinding.mainContainer, "Error while loading pages", Snackbar.LENGTH_INDEFINITE)
                            .setAction("RETRY", v -> loadPages()).show();
                    throwable.printStackTrace();
                });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPagesConnectionDisposable != null) mPagesConnectionDisposable.dispose();
    }

    public class PagerAdapter extends FragmentStatePagerAdapter
            implements ViewPager.OnPageChangeListener {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            PageConfig pageConfig = pagesConfig.get(position);
            PageFragment pageFragment = new PageFragment();
            Bundle args = new Bundle();
            args.putParcelable(PageFragment.KEY_PAGE_CONFIG, pageConfig);
            pageFragment.setArguments(args);
            return pageFragment;
        }



        @Override
        public int getCount() {
            return pagesConfig != null ? pagesConfig.size() : 0;
        }

        @Override
        public int getItemPosition(Object object) {
            // todo: handle reload
            return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pagesConfig.get(position).getTitle();
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mBinding.topBarTitle.setText(getPageTitle(position));
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
