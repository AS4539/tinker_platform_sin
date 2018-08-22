package com.xiuxiu;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.xiuxiu.assembly.ThinkerPatchHelper;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ${weicheng} on 2018/8/20.
 */

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        ThinkerPatchHelper.install();
    }

}
