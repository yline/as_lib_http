package com.yline.http.interceptor;

import com.yline.http.XHttpConfig;
import com.yline.http.cache.XCache;

import java.io.IOException;

import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;

/**
 * 按照 网络优先的 规则
 * 有网络 -- 读取网络
 * 无网络 -- 读取缓存 -- 无缓存 -- 返回失败
 *
 * @author yline 2017/7/22 -- 15:51
 * @version 1.0.0
 */
public class NetPriorInterceptor extends BaseInterceptor
{
	@Override
	public Response intercept(Chain chain) throws IOException
	{
		Request request = chain.request();
		preLog(chain, request);

		long time1 = System.nanoTime();
		boolean isNetWorkable = isNetConnected(XHttpConfig.getInstance().getContext());
		hintLog("isNetWorkable = " + isNetWorkable);

		if (isNetWorkable)
		{
			Response response = chain.proceed(request);

			postLog(response, System.nanoTime() - time1);
			return response;
		}
		else
		{
			Response cacheResponse = XCache.getInstance().getCache(request);
			if (null == cacheResponse)
			{
				nullLog("cacheResponse");
				return new Response.Builder().request(request).protocol(Protocol.HTTP_1_1).code(504).message("Unsatisfiable Request (cache is null)").body(Util.EMPTY_RESPONSE).sentRequestAtMillis(-1L).receivedResponseAtMillis(System.currentTimeMillis()).build();
			}
			else
			{
				postLog(cacheResponse, System.nanoTime() - time1);
				return cacheResponse;
			}
		}
	}
}
