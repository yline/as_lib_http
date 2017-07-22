package com.yline.http.cache;

import com.yline.http.XHttpConfig;

import java.io.File;
import java.io.InputStream;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 文件 Cache 缓存，单例
 *
 * @author yline 2017/7/22 -- 11:52
 * @version 1.0.0
 */
public class XCache extends OkioCache
{
	protected XCache(File dir, long maxSize)
	{
		super(dir, maxSize);
	}

	public static XCache getInstance()
	{
		return XCacheHolder.getInstance();
	}

	/**
	 * 获取缓存
	 *
	 * @param request
	 * @return
	 */
	public Response getCache(Request request)
	{
		return getResponse(request);
	}

	/**
	 * 存入缓存
	 *
	 * @param response    返回结构体
	 * @param inputStream 返回数据内容
	 */
	public void setCache(Response response, InputStream inputStream)
	{
		boolean isSuccess = putResponse(response, inputStream);
	}

	private static class XCacheHolder
	{
		private static XCache sInstance;

		private static XCache getInstance()
		{
			if (null == sInstance)
			{
				File dirFile = XHttpConfig.getInstance().getCacheDir();
				long maxSize = XHttpConfig.getInstance().getCacheMaxSize();
				sInstance = new XCache(dirFile, maxSize);
			}
			return sInstance;
		}
	}
}
