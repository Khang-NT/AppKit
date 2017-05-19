package com.mstage.appkit.data.inject;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Khang NT on 5/5/17.
 * Email: khang.neon.1997@gmail.com
 */

@Module
public class ContextModule {
    private Context mContext;

    public ContextModule(Context context) {
        this.mContext = context;
    }

    @Provides
    public Context provideApplicationContext() {
        return mContext;
    }

//    @Provides
//    @Singleton
//    public PreferenceStore preferenceStore(Context context) {
//        return new PreferenceStore(context.getSharedPreferences("prefs", Context.MODE_PRIVATE));
//    }
}
