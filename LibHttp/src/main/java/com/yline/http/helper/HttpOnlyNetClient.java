package com.yline.http.helper;

import com.yline.http.interceptor.OnlyNetInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class HttpOnlyNetClient
{
	private HttpOnlyNetClient()
	{
	}

	public static OkHttpClient getInstance()
	{
		return HttpOnlyNetHolder.getHttpClient();
	}

	private static class HttpOnlyNetHolder
	{
		private static OkHttpClient getHttpClient()
		{
			OkHttpClient.Builder builder = new OkHttpClient.Builder();

			// 设置超时
			builder.connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS);

			// 添加拦截器；默认走网络，如果没有网，则走缓存
			builder.addInterceptor(new OnlyNetInterceptor());

			return builder.build();
		}
	}
}
