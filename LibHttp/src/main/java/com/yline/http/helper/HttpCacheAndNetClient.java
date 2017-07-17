package com.yline.http.helper;

import com.yline.http.interceptor.CacheAndNetInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class HttpCacheAndNetClient
{
	private HttpCacheAndNetClient()
	{
	}

	public static OkHttpClient getInstance()
	{
		return HttpCacheAndNetHolder.getHttpClient();
	}

	private static class HttpCacheAndNetHolder
	{
		private static OkHttpClient getHttpClient()
		{
			OkHttpClient.Builder builder = new OkHttpClient.Builder();

			// 设置超时
			builder.connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS);

			// 添加拦截器；默认走网络，如果没有网，则走缓存
			builder.addInterceptor(new CacheAndNetInterceptor());

			return builder.build();
		}
	}
}
