package com.mstage.appkit.activity;

/**
 * Created by Khang NT on 5/9/17.
 * Email: khang.neon.1997@gmail.com
 */

public class MainScreen extends BaseActivity {
    public static final String EXTRA_CONFIG_DATA = "extra:config_data";
    public static final String KEY_MESSAGE_ID = "key:message_id";
//
//    @Inject
//    ConfigurationStore mConfigurationStore;
//    @Inject
//    PreferenceStore mPreferenceStore;
//    @Inject
//    DataStore mDataStore;
//
//    private ActivityMainScreenBinding mBinding;
//    private AccountProfileViewModel accountProfileViewModel;
//    private ActionBarDrawerToggle mDrawerToggle;
//    private PagerAdapter mPagerAdapter;
//    private List<SectionMetadata> sectionMetadataList;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        MainApplication.getAppKitInjector(this).inject(this);
//
//
//        MainScreenConfig mainScreenConfig = getIntent().getParcelableExtra(EXTRA_CONFIG_DATA);
//        if (mainScreenConfig == null)
//            mainScreenConfig = mConfigurationStore.getMainScreenConfig().blockingFirst();
//        Preconditions.checkNotNull(mainScreenConfig);
//
//
//        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_screen);
//        mBinding.setConfig(mainScreenConfig);
//
//        setStatusBarConfig(mainScreenConfig.getStatusBarConfig());
//
//        setSupportActionBar(mBinding.toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setTitle("");
//
//        mConfigurationStore
//                .getMainScreenConfig()
//                .skip(1)
//                .compose(bindToLifecycle())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(config -> {
//                    getIntent().putExtra(EXTRA_CONFIG_DATA, config);
//                    recreate();
//                }, Throwable::printStackTrace);
//
//        if (UserKitIdentity.getInstance().getAccountManager().isLoggedIn()) {
//            // already logged in but did't select any profile
//            if (mPreferenceStore.getSelectedProfileId() == null) {
//                Intent intent = new Intent(this, SelectProfileActivity.class);
//                startActivity(intent);
//                finish();
//                return;
//            }
//        }
//
//        NavHeaderBinding navHeaderBinding = NavHeaderBinding.inflate(LayoutInflater.from(this),
//                null, false);
//
//        navHeaderBinding.setOnLoginClick(v -> {
//            Intent intent = new Intent(this, AuthenticateActivity.class);
//            startActivity(intent);
//        });
//
//        navHeaderBinding.setOnViewProfileClick(v -> {
//            Intent intent = new Intent(this, SelectProfileActivity.class);
//            intent.putExtra(SelectProfileActivity.KEY_IS_SWITCH_PROIFLE, true);
//            startActivity(intent);
//        });
//
//        accountProfileViewModel = new AccountProfileViewModel(this);
//        navHeaderBinding.setViewModel(accountProfileViewModel);
//        mBinding.navView.addHeaderView(navHeaderBinding.getRoot());
//
//        mDrawerToggle = new ActionBarDrawerToggle(this,
//                mBinding.drawerLayout, mBinding.toolbar, 0, 0);
//        mDrawerToggle.setDrawerIndicatorEnabled(true);
//        mBinding.drawerLayout.addDrawerListener(mDrawerToggle);
//
//        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
//        mBinding.libraryPager.setOffscreenPageLimit(3);
//        mBinding.libraryPager.setAdapter(mPagerAdapter);
//        mBinding.libraryPager.addOnPageChangeListener(mPagerAdapter);
//
//        handleIntent(getIntent());
//
//        loadSectionMetadata();
//
//        if (getIntent().getStringExtra(KEY_MESSAGE_ID) != null) {
//            String messageId = getIntent().getStringExtra(KEY_MESSAGE_ID);
//            UserKit.getInstance().pushNotificationOpened(messageId);
//        }
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        handleIntent(intent);
//    }
//
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState)
//    {
//        super.onPostCreate(savedInstanceState);
//        mDrawerToggle.syncState();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig)
//    {
//        super.onConfigurationChanged(newConfig);
//        mDrawerToggle.onConfigurationChanged(newConfig);
//    }
//
//    void handleIntent(Intent intent) {
//        accountProfileViewModel.loadData();
//    }
//
//    void loadSectionMetadata() {
//        mBinding.progressWheel.setVisibility(View.VISIBLE);
//        mBinding.drawerLayout.setEnabled(false);
//        mDataStore.getSectionMetadata()
//                .compose(bindToLifecycle())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(sectionMetadataList -> {
//                    if (sectionMetadataList.size() > 0) {
//                        mBinding.progressWheel.setVisibility(View.GONE);
//                        mBinding.drawerLayout.setEnabled(true);
//                    }
//                    this.sectionMetadataList = sectionMetadataList;
//                    this.mPagerAdapter.notifyDataSetChanged();
////                    this.buildNavigationMenu();
//                }, throwable -> showSnackBar(throwable.getMessage()));
//    }
//
//    void showSnackBar(String message) {
//        Snackbar.make(mBinding.mainContainer, message, Snackbar.LENGTH_SHORT).show();
//    }
//
//    public class PagerAdapter extends FragmentStatePagerAdapter
//            implements ViewPager.OnPageChangeListener {
//
//        public PagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            SectionMetadata sectionMetadata = sectionMetadataList.get(position);
//
//            Bundle args = new Bundle();
//            args.putString(MoviesFragment.KEY_PATH, "sections/" + position);
//            args.putString(MoviesFragment.KEY_CATEGORY, sectionMetadata.getTitle());
//
//            if (sectionMetadata.getType().equals(C.TYPE_MOVIES)) {
//                MoviesFragment moviesFragment = new MoviesFragment();
//                moviesFragment.setArguments(args);
//                return moviesFragment;
//            } else if (sectionMetadata.getType().equals(C.TYPE_NEWS)) {
//                NewsFragment newsFragment = new NewsFragment();
//                newsFragment.setArguments(args);
//                return newsFragment;
//            }
//
//            return new Fragment();
//        }
//
//
//
//        @Override
//        public int getCount() {
//            return sectionMetadataList != null ?
//                    sectionMetadataList.size() : 0;
//        }
//
//        @Override
//        public int getItemPosition(Object object) {
//            if (object instanceof HasMetadata) {
//                HasMetadata metadata = (HasMetadata) object;
//                for (int i = 0; i < getCount(); i++) {
//                    if (sectionMetadataList.get(i).getTitle().equals(metadata.getCategory())
//                            && sectionMetadataList.get(i).getType().equals(metadata.getType())
//                            && ("sections/" + i).equals(metadata.getPath()))
//                        return i;
//                }
//            }
//            return POSITION_NONE;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return sectionMetadataList.get(position).getTitle();
//        }
//
//        @Override
//        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//        }
//
//        @Override
//        public void onPageSelected(int position) {
////            for (int i = 0; i < getCount(); i++) {
////                MenuItem item = mBinding.navView.getMenu().findItem(i);
////                if (item != null) item.setChecked(i == position);
////            }
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int state) {
//
//        }
//    }
}
