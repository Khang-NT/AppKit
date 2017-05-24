package com.mstage.appkit.data.inject;

import com.mstage.appkit.activity.FlashScreen;
import com.mstage.appkit.activity.MainScreen;
import com.mstage.appkit.model.FontConfig;
import com.mstage.appkit.model.HttpDataSourceConfig;

/**
 * Created by Khang NT on 5/5/17.
 * Email: khang.neon.1997@gmail.com
 */

public interface AppKitInjector {
    void inject(FlashScreen flashScreenActivity);

    void inject(MainScreen mainScreen);

    void inject(FontConfig font);

    void inject(HttpDataSourceConfig httpDataSourceConfig);
}
