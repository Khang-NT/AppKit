package com.mstage.appkit;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.mstage.appkit.data.inject.AppKitInjector;
import com.mstage.appkit.data.inject.AppKitModule;
import com.mstage.appkit.data.inject.ContextModule;
import com.mstage.appkit.data.inject.DaggerAppKitComponents;

/**
 * Created by Khang NT on 5/17/17.
 * Email: khang.neon.1997@gmail.com
 */

public class AppKitApplication extends Application {

    private AppKitInjector mAppKitInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(this);

        mAppKitInjector = DaggerAppKitComponents.builder()
                .contextModule(new ContextModule(this))
                .appKitModule(new AppKitModule())
                .build();

        FirebaseDatabase.getInstance()
                .getReferenceFromUrl(BuildConfig.FIREBASE_PATH).keepSynced(true);
    }

    public static AppKitInjector getAppKitInjector(Context context) {
        return ((AppKitApplication)context.getApplicationContext()).mAppKitInjector;
    }

    public static AppKitInjector getAppKitInjector(Fragment fragment) {
        return getAppKitInjector(fragment.getContext());
    }
}
