package com.lib.http.dev;

import android.app.Application;

import com.yline.application.SDKConfig;
import com.yline.application.SDKManager;

public class IApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SDKManager.init(this, new SDKConfig());
    }
}
