package com.yline.http.interceptor;

import com.yline.http.XHttpConfig;
import com.yline.http.cache.CacheManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;

/**
 * 由于缓存读取的时候，采用的是默认的io；会导致只能读取一次的bug，因此，先缓存+返回，的策略，有问题
 *
 * @author yline 2017/7/14 -- 15:24
 * @version 1.0.0
 */
public class CacheAndNetInterceptor extends BaseInterceptor
{
	private OnCacheResponseCallback onCacheResponseCallback;

	public CacheAndNetInterceptor()
	{
	}

	public void setOnCacheResponseCallback(OnCacheResponseCallback onCacheResponseCallback)
	{
		this.onCacheResponseCallback = onCacheResponseCallback;
	}

	@Override
	public Response intercept(Interceptor.Chain chain) throws IOException
	{
		Request request = chain.request();
		preLog(chain, request);

		Response cacheResponse = CacheManager.getInstance().get(request);
		if (null != onCacheResponseCallback)
		{
			onCacheResponseCallback.onCacheResponse(cacheResponse);
		}

		long time1 = System.nanoTime();
		boolean isNetWorkable = isNetConnected(XHttpConfig.getInstance().getContext());
		if (isNetWorkable)
		{
			Response response = chain.proceed(request);

			postLog(response, System.nanoTime() - time1);
			CacheManager.getInstance().write(response);
			Response resultResponse = CacheManager.getInstance().get(request);

			return resultResponse;
		}
		else
		{
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
