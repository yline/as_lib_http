package com.lib.http.demo.activity;

import android.os.Bundle;
import android.view.View;

import com.lib.http.demo.XHttpUtil;
import com.lib.http.demo.bean.VNewsMultiplexBean;
import com.lib.http.demo.bean.VNewsSingleBean;
import com.lib.http.demo.bean.WNewsMultiplexBean;
import com.yline.http.XHttpAdapter;
import com.yline.http.interceptor.OnCacheResponseCallback;
import com.yline.log.LogFileUtil;
import com.yline.test.BaseTestActivity;

import java.io.IOException;

import okhttp3.Response;

public class MainActivity extends BaseTestActivity
{
	@Override
	protected void testStart(Bundle savedInstanceState)
	{
		addButton("Get default", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				XHttpUtil.doGet(new XHttpAdapter<VNewsSingleBean>()
				{
					@Override
					public void onSuccess(VNewsSingleBean vNewsSingleBean)
					{

					}
				});
			}
		});

		addButton("Get OnlyNetInterceptor", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				XHttpUtil.doGetOnlyNet(new XHttpAdapter<VNewsSingleBean>()
				{
					@Override
					public void onSuccess(VNewsSingleBean vNewsSingleBean)
					{

					}
				});
			}
		});

		XHttpUtil.doGet(new XHttpAdapter<VNewsSingleBean>()
		{
			@Override
			public void onSuccess(VNewsSingleBean vNewsSingleBean)
			{

			}
		}, new OnCacheResponseCallback()
		{
			@Override
			public void onCacheResponse(Response cacheResponse) throws IOException
			{
				if (null != cacheResponse)
				{
					LogFileUtil.v(cacheResponse.body().string().toString());
				}
				else
				{
					LogFileUtil.v("cacheResponse is null");
				}
			}
		});

		addButton("Get CacheAndNetInterceptor", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				XHttpUtil.doGet(new XHttpAdapter<VNewsSingleBean>()
				{
					@Override
					public void onSuccess(VNewsSingleBean vNewsSingleBean)
					{

					}
				}, new OnCacheResponseCallback()
				{
					@Override
					public void onCacheResponse(Response cacheResponse) throws IOException
					{
						if (null != cacheResponse)
						{
							LogFileUtil.v(cacheResponse.body().string().toString());
						}
						else
						{
							LogFileUtil.v("cacheResponse is null");
						}
					}
				});
			}
		});

		addButton("Post default", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				XHttpUtil.doPost(new XHttpAdapter<VNewsMultiplexBean>()
				{
					@Override
					public void onSuccess(VNewsMultiplexBean vNewsMultiplexBean)
					{

					}
				}, new WNewsMultiplexBean(0, 3));
			}
		});
	}
}
