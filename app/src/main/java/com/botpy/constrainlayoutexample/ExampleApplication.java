package com.botpy.constrainlayoutexample;

import android.app.Application;

import com.botpy.framelibrary.skin.SkinManager;

/**
 * @author liuxuhui
 * @date 2019-07-03
 */
public class ExampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);
    }
}
