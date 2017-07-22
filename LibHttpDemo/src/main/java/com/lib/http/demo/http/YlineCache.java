package com.lib.http.demo.http;

import com.yline.http.cache.XCache;

import java.io.File;
import java.io.IOException;

import okhttp3.Request;

/**
 * 自定义 缓存 规则
 *
 * @author yline 2017/7/22 -- 16:44
 * @version 1.0.0
 */
public class YlineCache extends XCache
{
	protected YlineCache(File dir, long maxSize)
	{
		super(dir, maxSize);
	}

	@Override
	protected String key(Request request) throws IOException
	{
		return super.key(request);
	}
}
