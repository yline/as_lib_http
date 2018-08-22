package com.yline.http.adapter.helper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 默认的 Http请求 Client
 *
 * @author yline 2018/8/21 -- 18:30
 */
public class ClientHelper {
	private static OkHttpClient okHttpClient;
	
	private static final int READ_SECONDS = 10;
	private static final int WRITE_SECONDS = 10;
	private static final int CONNECT_SECONDS = 10;
	
	public static OkHttpClient getDefaultHttpClient() {
		if (null == okHttpClient) {
			synchronized (OkHttpClient.class) {
				if (null == okHttpClient) {
					okHttpClient = initDefaultHttpClient();
				}
			}
		}
		return okHttpClient;
	}
	
	private static OkHttpClient initDefaultHttpClient() {
		OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
		
		// 超时
		clientBuilder.readTimeout(READ_SECONDS, TimeUnit.SECONDS);
		clientBuilder.writeTimeout(WRITE_SECONDS, TimeUnit.SECONDS);
		clientBuilder.connectTimeout(CONNECT_SECONDS, TimeUnit.SECONDS);
		
		// 返回
		clientBuilder.retryOnConnectionFailure(false); // 失败不重试
		return clientBuilder.build();
	}
}
