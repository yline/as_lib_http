package com.lib.http.demo.cache.client;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class OkHttpClientManager {
	/**
	 * 按照 网络优先的 规则
	 * 有网络 -- 读取网络
	 * 无网络 -- 读取缓存 -- 无缓存 -- 返回失败
	 */
	private static OkHttpClient netPriorHttpClient;
	
	public static OkHttpClient getNetPriorHttpClient(){
		if (null == netPriorHttpClient) {
			synchronized (OkHttpClient.class) {
				if (null == netPriorHttpClient) {
					netPriorHttpClient = initNetPriorHttpClient();
				}
			}
		}
		return netPriorHttpClient;
	}
	
	private static OkHttpClient initNetPriorHttpClient(){
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		
		// 设置参数
		builder.connectTimeout(10, TimeUnit.SECONDS).
				readTimeout(10, TimeUnit.SECONDS).
				writeTimeout(10, TimeUnit.SECONDS);
		builder.addInterceptor(new NetPriorInterceptor());
		
		return builder.build();
	}
}
