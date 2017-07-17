package com.lib.http.demo;

import com.lib.http.demo.bean.VNewsMultiplexBean;
import com.lib.http.demo.bean.VNewsSingleBean;
import com.lib.http.demo.bean.WNewsMultiplexBean;
import com.yline.http.XHttpAdapter;
import com.yline.http.helper.HttpCacheAndNetClient;
import com.yline.http.helper.HttpOnlyNetClient;
import com.yline.http.helper.XTextHttp;
import com.yline.http.interceptor.CacheAndNetInterceptor;
import com.yline.http.interceptor.OnCacheResponseCallback;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

public class XHttpUtil
{
	public static void doGet(XHttpAdapter<VNewsSingleBean> adapter)
	{
		String httpUrl = "http://120.92.35.211/wanghong/wh/index.php/Api/ApiNews/new_tui";
		new XTextHttp<VNewsSingleBean>(adapter).doGet(httpUrl, null, VNewsSingleBean.class);
	}

	public static void doGetOnlyNet(XHttpAdapter<VNewsSingleBean> adapter)
	{
		String httpUrl = "http://120.92.35.211/wanghong/wh/index.php/Api/ApiNews/new_tui";
		new XTextHttp<VNewsSingleBean>(adapter)
		{
			@Override
			protected OkHttpClient getClient()
			{
				return HttpOnlyNetClient.getInstance();
			}
		}.doGet(httpUrl, null, VNewsSingleBean.class);
	}

	public static void doGet(XHttpAdapter<VNewsSingleBean> adapter, final OnCacheResponseCallback cacheResponseCallback)
	{
		String httpUrl = "http://120.92.35.211/wanghong/wh/index.php/Api/ApiNews/new_tui";
		new XTextHttp<VNewsSingleBean>(adapter)
		{
			@Override
			protected OkHttpClient getClient()
			{
				OkHttpClient okHttpClient = HttpCacheAndNetClient.getInstance();

				for (Interceptor inter : okHttpClient.interceptors())
				{
					if (inter instanceof CacheAndNetInterceptor)
					{
						((CacheAndNetInterceptor) inter).setOnCacheResponseCallback(cacheResponseCallback);
					}
				}

				return okHttpClient;
			}

		}.doGet(httpUrl, null, VNewsSingleBean.class);
	}

	public static void doPost(XHttpAdapter<VNewsMultiplexBean> adapter, WNewsMultiplexBean wNewsMultiplexBean)
	{
		String httpUrl = "http://120.92.35.211/wanghong/wh/index.php/Api/ApiNews/news";
		new XTextHttp<VNewsMultiplexBean>(adapter).doPost(httpUrl, wNewsMultiplexBean, VNewsMultiplexBean.class);
	}
}
