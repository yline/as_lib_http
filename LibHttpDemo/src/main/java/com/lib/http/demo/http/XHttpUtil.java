package com.lib.http.demo.http;

import com.lib.http.demo.bean.VNewsMultiplexBean;
import com.lib.http.demo.bean.VNewsSingleBean;
import com.lib.http.demo.bean.WNewsMultiplexBean;
import com.yline.http.XHttpAdapter;
import com.yline.http.client.HttpCachePriorClient;
import com.yline.http.client.HttpNetPriorClient;

import okhttp3.OkHttpClient;

public class XHttpUtil
{
	public static void doGetDefault(XHttpAdapter<VNewsSingleBean> adapter)
	{
		String httpUrl = "http://120.92.35.211/wanghong/wh/index.php/Api/ApiNews/new_tui";
		new YlineHttp().doGet(httpUrl, null, VNewsSingleBean.class, adapter);
	}

	public static void doPostDefault(WNewsMultiplexBean wNewsMultiplexBean, XHttpAdapter<VNewsMultiplexBean> adapter)
	{
		String httpUrl = "http://120.92.35.211/wanghong/wh/index.php/Api/ApiNews/news";
		new YlineHttp().doPost(httpUrl, wNewsMultiplexBean, VNewsMultiplexBean.class, adapter);
	}

	public static void doGetNetPrior(XHttpAdapter<VNewsSingleBean> adapter)
	{
		String httpUrl = "http://120.92.35.211/wanghong/wh/index.php/Api/ApiNews/new_tui";
		new YlineHttp()
		{
			@Override
			protected OkHttpClient getHttpClient()
			{
				return HttpNetPriorClient.getInstance();
			}
		}.doGet(httpUrl, null, VNewsSingleBean.class, adapter);
	}

	public static void doPostNetPrior(WNewsMultiplexBean wNewsMultiplexBean, XHttpAdapter<VNewsMultiplexBean> adapter)
	{
		String httpUrl = "http://120.92.35.211/wanghong/wh/index.php/Api/ApiNews/news";
		new YlineHttp()
		{
			@Override
			protected OkHttpClient getHttpClient()
			{
				return HttpNetPriorClient.getInstance();
			}
		}.doPost(httpUrl, wNewsMultiplexBean, VNewsMultiplexBean.class, adapter);
	}

	public static void doGetCachePrior(XHttpAdapter<VNewsSingleBean> adapter)
	{
		String httpUrl = "http://120.92.35.211/wanghong/wh/index.php/Api/ApiNews/new_tui";
		new YlineHttp()
		{
			@Override
			protected OkHttpClient getHttpClient()
			{
				return HttpCachePriorClient.getInstance();
			}
		}.doGet(httpUrl, null, VNewsSingleBean.class, adapter);
	}

	public static void doPostCachePrior(WNewsMultiplexBean wNewsMultiplexBean, XHttpAdapter<VNewsMultiplexBean> adapter)
	{
		String httpUrl = "http://120.92.35.211/wanghong/wh/index.php/Api/ApiNews/news";
		new YlineHttp()
		{
			@Override
			protected OkHttpClient getHttpClient()
			{
				return HttpCachePriorClient.getInstance();
			}
		}.doPost(httpUrl, wNewsMultiplexBean, VNewsMultiplexBean.class, adapter);
	}
}
