package com.yline.http;

import android.content.Context;

import com.yline.http.util.LogUtil;

import java.io.File;

public class XHttpConfig
{
	private Context sContext;

	private File cacheDir;

	private int cacheMaxSize = 128 * 1024 * 1024;

	private boolean isProcessLog = true;

	private XHttpConfig()
	{
	}

	public static XHttpConfig getInstance()
	{
		return HttpConfigHolder.sInstance;
	}

	/* 这个类，必须被初始化；否则程序出错 */
	public XHttpConfig init(Context context)
	{
		this.sContext = context;
		if (null == cacheDir)
		{
			this.cacheDir = sContext.getExternalCacheDir();
		}
		return this;
	}

	public boolean isProcessLog()
	{
		return isProcessLog;
	}

	public XHttpConfig setProcessLog(boolean processLog)
	{
		this.isProcessLog = processLog;
		return this;
	}

	/**
	 * 库工程 是否 打印日志
	 *
	 * @param utilLog
	 * @return
	 */
	public XHttpConfig setUtilLog(boolean utilLog)
	{
		LogUtil.setUtilLog(utilLog);
		return this;
	}

	/**
	 * 库工程 日志 是否具有定位功能
	 *
	 * @param utilLogLocation
	 * @return
	 */
	public XHttpConfig setUtilLogLocation(boolean utilLogLocation)
	{
		LogUtil.setUtilLogLocation(utilLogLocation);
		return this;
	}

	public Context getContext()
	{
		return sContext;
	}

	public File getCacheDir()
	{
		if (null == cacheDir)
		{
			this.cacheDir = sContext.getExternalCacheDir();
		}
		return cacheDir;
	}

	public XHttpConfig setCacheDir(File cacheDir)
	{
		this.cacheDir = cacheDir;
		return this;
	}

	public int getCacheMaxSize()
	{
		return cacheMaxSize;
	}

	public XHttpConfig setCacheMaxSize(int cacheMaxSize)
	{
		this.cacheMaxSize = cacheMaxSize;
		return this;
	}

	private static class HttpConfigHolder
	{
		private static XHttpConfig sInstance = new XHttpConfig();
	}
}
