package com.mstage.appkit.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mstage.appkit.AppKitApplication;
import com.mstage.appkit.R;
import com.mstage.appkit.data.store.ConfigurationStore;
import com.mstage.appkit.databinding.ActivityFlashScreenBinding;
import com.mstage.appkit.model.FlashScreenConfig;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Khang NT on 5/4/17.
 * Email: khang.neon.1997@gmail.com
 */

public class FlashScreen extends BaseActivity {

    private ActivityFlashScreenBinding mBinding;

    @Inject
    ConfigurationStore mConfigurationStore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppKitApplication.getAppKitInjector(this).inject(this);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_flash_screen);

        load();

        mBinding.setOnRetryClick(view -> load());
    }

    void load() {
        if (true) {
            mBinding.setLoading(true);
            mBinding.setError(false);
            mConfigurationStore.getFlashScreenConfig()
                    .take(1)
                    .compose(bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> mBinding.setLoading(false))
                    .subscribe(flashScreenConfig -> {
                        mBinding.setFlashScreenConfig(flashScreenConfig);
                        setStatusBarConfig(flashScreenConfig.getStatusBarConfig());
                        mBinding.setError(false);

                        preloadData(flashScreenConfig);
                    }, throwable -> {
                        mBinding.setError(true);
                        throwable.printStackTrace();
                    });
        } else {
            mBinding.setLoading(false);
            mBinding.setError(true);
        }
    }

    void loadMainScreen() {
        mConfigurationStore.getMainScreenConfig()
                .debounce(2, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mainScreenConfig -> {
                    Intent intent = new Intent(this, MainScreen.class);
                    intent.putExtra(MainScreen.EXTRA_CONFIG_DATA, mainScreenConfig);
                    startActivity(intent);
                    finish();
                }, throwable -> {
                    mBinding.setError(true);
                    throwable.printStackTrace();
                });
    }

    void preloadData(FlashScreenConfig flashScreenConfig) {
        Completable.concat(Flowable.fromIterable(flashScreenConfig.getPreloadImages()).map(this::fetchImageIgnoreError))
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadMainScreen, throwable -> {
                    mBinding.setError(true);
                    throwable.printStackTrace();
                });
    }

    Completable fetchImageIgnoreError(String url) {
        return Completable.create(emitter -> {
            Picasso.with(this).load(url).fetch(new Callback() {
                @Override
                public void onSuccess() {
                    emitter.onComplete();
                }

                @Override
                public void onError() {
                    emitter.onComplete();
                }
            });
        });
    }
}
