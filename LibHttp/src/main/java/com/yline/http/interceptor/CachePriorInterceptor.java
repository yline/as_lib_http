package com.yline.http.interceptor;

import com.yline.http.cache.XCache;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 缓存优先的策略
 * 有缓存 -- 读取缓存
 * 无缓存 -- 请求网络
 *
 * @author yline 2017/7/22 -- 15:41
 * @version 1.0.0
 */
public class CachePriorInterceptor extends BaseInterceptor
{
	@Override
	public Response intercept(Chain chain) throws IOException
	{
		Request request = chain.request();
		preLog(chain, request);

		long time1 = System.nanoTime();
		Response cacheResponse = XCache.getInstance().getCache(request);
		if (null == cacheResponse)
		{
			nullLog("cacheResponse");
			Response response = chain.proceed(request);

			postLog(response, System.nanoTime() - time1);
			return response;
		}
		else
		{
			postLog(cacheResponse, System.nanoTime() - time1);
			return cacheResponse;
		}
	}
}
