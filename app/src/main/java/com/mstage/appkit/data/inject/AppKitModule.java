package com.mstage.appkit.data.inject;

import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;
import com.mstage.appkit.BuildConfig;
import com.mstage.appkit.data.store.ConfigurationStore;
import com.mstage.appkit.data.store.FirebaseConfigStore;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by Khang NT on 5/5/17.
 * Email: khang.neon.1997@gmail.com
 */

@Module
public class AppKitModule {
    @Singleton
    @Provides
    public ConfigurationStore provideConfigurationStore() {
        return new FirebaseConfigStore(FirebaseDatabase.getInstance().getReferenceFromUrl(BuildConfig.FIREBASE_PATH));
    }


    @Singleton
    @Provides
    public OkHttpClient provideMainHttpClient(Context context) {
        return new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .cache(new Cache(new File(context.getCacheDir(), "network-cache"), 15*1024*1024L))
                .build();
    }
}
