package com.yline.http.interceptor;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yline.http.XHttpConfig;
import com.yline.http.util.LogUtil;

import java.util.Arrays;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 注意：若无网络，则chain.proceed(request)之后就不会执行
 *
 * @author yline 2017/7/22 -- 15:40
 * @version 1.0.0
 */
public abstract class BaseInterceptor implements Interceptor
{
	protected void preLog(Chain chain, Request request)
	{
		if (isDebug())
		{
			LogUtil.v(String.format("Network request %s on %s%n%s", request.url(), chain.connection(), request.headers()), LogUtil.LOG_LOCATION_PARENT);
		}
	}

	protected void postLog(Response response, long milliTime)
	{
		if (isDebug())
		{
			LogUtil.v(String.format("Network response %s in %.1fms%n%s", response.request().url(), milliTime / 1e6d, response.headers()), LogUtil.LOG_LOCATION_PARENT);
		}
	}

	protected void nullLog(String... strings)
	{
		if (isDebug())
		{
			LogUtil.v(Arrays.toString(strings) + " is null", LogUtil.LOG_LOCATION_PARENT);
		}
	}

	protected void hintLog(String... strings)
	{
		if (isDebug())
		{
			LogUtil.v(Arrays.toString(strings), LogUtil.LOG_LOCATION_PARENT);
		}
	}

	protected boolean isNetConnected(Context context)
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (null == connectivityManager)
		{
			return false;
		}

		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (null == networkInfo)
		{
			return false;
		}

		int type = networkInfo.getType();
		if (type == -1)
		{
			return false;
		}

		return true;
	}

	protected boolean isDebug()
	{
		return XHttpConfig.getInstance().isProcessLog();
	}
}
