package com.yline.http.helper;

import com.yline.http.interceptor.NetThanCacheInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 提供,统一处理Http的Client;
 * 这个用来处理文字和不被清除的内存(排除图片+视频)
 *
 * @author yline 2017/2/28 -- 17:29
 * @version 1.0.0
 */
public class HttpNetThanCacheClient extends OkHttpClient
{
	private HttpNetThanCacheClient()
	{
	}
	
	public static OkHttpClient getInstance()
	{
		return HttpNetThanCacheHolder.getHttpClient();
	}

	private static class HttpNetThanCacheHolder
	{
		private static OkHttpClient getHttpClient()
		{
			Builder builder = new Builder();

			// 设置超时
			builder.connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS);

			// 添加拦截器；默认走网络，如果没有网，则走缓存
			builder.addInterceptor(new NetThanCacheInterceptor());

			return builder.build();
		}
	}
}
