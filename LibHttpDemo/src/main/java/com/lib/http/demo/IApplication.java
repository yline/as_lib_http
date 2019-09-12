package com.lib.http.demo;

import android.app.Application;

import com.yline.application.SDKConfig;
import com.yline.application.SDKManager;
import com.yline.http.OkHttpUtils;

public class IApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SDKManager.init(this, new SDKConfig());
        OkHttpUtils.setIsDebug(true);
    }
}
