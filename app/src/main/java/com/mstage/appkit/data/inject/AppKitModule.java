package com.mstage.appkit.data.inject;

import com.google.firebase.database.FirebaseDatabase;
import com.mstage.appkit.data.store.ConfigurationStore;
import com.mstage.appkit.data.store.FirebaseConfigStore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Khang NT on 5/5/17.
 * Email: khang.neon.1997@gmail.com
 */

@Module
public class AppKitModule {
    @Singleton
    @Provides
    public ConfigurationStore provideConfigurationStore() {
        return new FirebaseConfigStore(FirebaseDatabase.getInstance().getReference("Testing"));
    }

//    @Provides
//    @Singleton
//    public DataStore provideDataStore() {
//        return new FireBaseDataStore();
//    }
}
