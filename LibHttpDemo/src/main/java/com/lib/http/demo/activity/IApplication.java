package com.lib.http.demo.activity;

import android.app.Application;

import com.yline.application.SDKConfig;
import com.yline.application.SDKManager;
import com.yline.http.XHttpConfig;

public class IApplication extends Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();

		SDKManager.init(this, new SDKConfig());
		XHttpConfig.getInstance().init(this);
	}
}
