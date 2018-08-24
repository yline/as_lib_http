package com.lib.http.demo;

import android.os.Bundle;
import android.view.View;

import com.lib.http.demo.cache.model.NewsMultiplexModel;
import com.lib.http.demo.cache.model.NewsSingleModel;
import com.lib.http.demo.normal.KjtHttpManager;
import com.lib.http.demo.normal.model.LoginModel;
import com.lib.http.demo.cache.CacheHttpManager;
import com.yline.application.SDKManager;
import com.yline.http.callback.OnJsonCallback;
import com.yline.test.BaseTestActivity;
import com.yline.utils.LogUtil;

public class MainActivity extends BaseTestActivity {
	@Override
	public void testStart(View view, Bundle savedInstanceState) {
		addButton("Normal Version", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String phone = "17682305090";
				String password = "123456";
				
				KjtHttpManager.loginNormal(phone, password, new OnJsonCallback<LoginModel>() {
					@Override
					public void onFailure(int code, String msg) {
						// todo
					}
					
					@Override
					public void onResponse(LoginModel loginModel) {
						LogUtil.v("success, loginModel = " + loginModel);
						SDKManager.toast("success, loginModel = " + loginModel);
					}
				});
			}
		});
		
		addButton("doGetDefault", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CacheHttpManager.doGetDefault(new OnJsonCallback<NewsSingleModel>() {
					@Override
					public void onFailure(int code, String msg) {
						// todo
					}
					
					@Override
					public void onResponse(NewsSingleModel vNewsSingleModel) {
						// todo
					}
				});
			}
		});
		
		addButton("doPostDefault", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CacheHttpManager.doPostDefault(0, 10, new OnJsonCallback<NewsMultiplexModel>() {
					@Override
					public void onFailure(int code, String msg) {
						// todo
					}
					
					@Override
					public void onResponse(NewsMultiplexModel vNewsMultiplexModel) {
						// todo
					}
				});
			}
		});
		
		addButton("doGetNetPrior", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CacheHttpManager.doGetNetPrior(new OnJsonCallback<NewsSingleModel>() {
					@Override
					public void onFailure(int code, String msg) {
						// todo
					}
					
					@Override
					public void onResponse(NewsSingleModel vNewsSingleModel) {
						// todo
					}
				});
			}
		});
		
		addButton("doPostNetPrior", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CacheHttpManager.doPostNetPrior(0, 10, new OnJsonCallback<NewsMultiplexModel>() {
					@Override
					public void onFailure(int code, String msg) {
						// todo
					}
					
					@Override
					public void onResponse(NewsMultiplexModel vNewsMultiplexModel) {
						// todo
					}
				});
			}
		});
	}
}
