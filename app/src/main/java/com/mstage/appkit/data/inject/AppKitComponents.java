package com.mstage.appkit.data.inject;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Khang NT on 5/5/17.
 * Email: khang.neon.1997@gmail.com
 */

@Singleton
@Component(modules = {ContextModule.class, AppKitModule.class})
public interface AppKitComponents extends AppKitInjector {
}
