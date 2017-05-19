package com.mstage.appkit.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.mstage.appkit.model.StatusBarConfig;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Created by Khang NT on 5/5/17.
 * Email: khang.neon.1997@gmail.com
 */

public abstract class BaseActivity extends RxAppCompatActivity {
    private StatusBarConfig statusBarConfig;

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setStatusBarConfig(StatusBarConfig statusBarConfig) {
        this.statusBarConfig = statusBarConfig;
        onStatusBarConfigChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onStatusBarConfigChanged();
    }

    protected void onStatusBarConfigChanged() {
        if (statusBarConfig != null) {
            if (statusBarConfig.isShow()) {
                Window window = getWindow();
                WindowManager.LayoutParams attrs = window.getAttributes();
                attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                window.setAttributes(attrs);
                if (Build.VERSION.SDK_INT >= 21) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.setStatusBarColor(Color.parseColor(statusBarConfig.getColor()));
                }
            } else {
                WindowManager.LayoutParams attrs = getWindow().getAttributes();
                attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                getWindow().setAttributes(attrs);
            }
        }
    }
}
