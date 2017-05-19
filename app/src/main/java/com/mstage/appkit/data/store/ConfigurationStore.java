package com.mstage.appkit.data.store;

import com.mstage.appkit.model.FlashScreenConfig;
import com.mstage.appkit.model.MainScreenConfig;

import io.reactivex.Observable;

/**
 * Created by Khang NT on 5/5/17.
 * Email: khang.neon.1997@gmail.com
 */

public interface ConfigurationStore {
    Observable<FlashScreenConfig> getFlashScreenConfig();
    Observable<MainScreenConfig> getMainScreenConfig();
}
