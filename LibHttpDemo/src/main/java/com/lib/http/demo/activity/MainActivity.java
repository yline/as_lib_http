package com.lib.http.demo.activity;

import android.os.Bundle;
import android.view.View;

import com.lib.http.demo.bean.VNewsMultiplexBean;
import com.lib.http.demo.bean.VNewsSingleBean;
import com.lib.http.demo.bean.WNewsMultiplexBean;
import com.lib.http.demo.http.XHttpUtil;
import com.yline.http.XHttpAdapter;
import com.yline.test.BaseTestActivity;

public class MainActivity extends BaseTestActivity
{
	@Override
	protected void testStart(Bundle savedInstanceState)
	{
		addButton("doGetDefault", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				XHttpUtil.doGetDefault(new XHttpAdapter<VNewsSingleBean>()
				{
					@Override
					public void onSuccess(VNewsSingleBean vNewsSingleBean)
					{

					}
				});
			}
		});

		addButton("doPostDefault", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				XHttpUtil.doPostDefault(new WNewsMultiplexBean(0, 10), new XHttpAdapter<VNewsMultiplexBean>()
				{
					@Override
					public void onSuccess(VNewsMultiplexBean vNewsMultiplexBean)
					{

					}
				});
			}
		});

		addButton("doGetNetPrior", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				XHttpUtil.doGetNetPrior(new XHttpAdapter<VNewsSingleBean>()
				{
					@Override
					public void onSuccess(VNewsSingleBean vNewsSingleBean)
					{

					}
				});
			}
		});

		addButton("doPostNetPrior", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				XHttpUtil.doPostNetPrior(new WNewsMultiplexBean(0, 10), new XHttpAdapter<VNewsMultiplexBean>()
				{
					@Override
					public void onSuccess(VNewsMultiplexBean vNewsMultiplexBean)
					{

					}
				});
			}
		});

		addButton("doGetCachePrior", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				XHttpUtil.doGetCachePrior(new XHttpAdapter<VNewsSingleBean>()
				{
					@Override
					public void onSuccess(VNewsSingleBean vNewsSingleBean)
					{

					}
				});
			}
		});
		
		addButton("doPostCachePrior", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				XHttpUtil.doPostCachePrior(new WNewsMultiplexBean(0, 10), new XHttpAdapter<VNewsMultiplexBean>()
				{
					@Override
					public void onSuccess(VNewsMultiplexBean vNewsMultiplexBean)
					{

					}
				});
			}
		});
	}
}
