package com.yline.http.cache;

import com.yline.http.util.LogUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

public class CacheManager
{
	public static final MediaType DEFAULT_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

	private CacheManager()
	{
	}

	public static CacheManager getInstance()
	{
		return CacheManagerHolder.sInstance;
	}

	private static class CacheManagerHolder
	{
		private static CacheManager sInstance = new CacheManager();
	}

	private CacheCode textCache;

	public void init(File dir, int maxSize)
	{
		this.textCache = new CacheCode(dir, maxSize);
	}
	
	/**
	 * 写入缓存数据
	 *
	 * @param response 返回参数
	 */
	public void write(Response response)
	{
		try
		{
			textCache.put(response);
		} catch (IOException e)
		{
			LogUtil.e("write CacheCode Failed", e);
		}
	}

	/**
	 * 获取缓存 结果
	 *
	 * @param request
	 * @return
	 */
	public Response get(Request request)
	{
		return textCache.get(request);
	}
}
