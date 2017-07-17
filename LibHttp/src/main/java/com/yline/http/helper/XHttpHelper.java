package com.yline.http.helper;

/**
 * 主要处理返回结果
 *
 * @author yline 2017/7/14 -- 12:24
 * @version 1.0.0
 */
public class XHttpHelper
{
	private HttpHandler httpHandler;

	private IHttpResponse httpResponse;

	private boolean isHandler;

	/**
	 * @param httpResponse
	 * @param isHandler
	 */
	public XHttpHelper(IHttpResponse httpResponse, boolean isHandler)
	{
		this.isHandler = isHandler;
		if (isHandler)
		{
			this.httpHandler = HttpHandler.build();
		}
		
		this.httpResponse = httpResponse;
	}

	/**
	 * @param jsonData 最开始的Json数据
	 * @param isHandle 是否 进行默认处理
	 * @param clazz    如果处理，那么对应的json类是什么；若不处理，则为null
	 */
	public <T> void handleSuccess(final String jsonData, final boolean isHandle, final Class<T> clazz)
	{
		if (isHandler)
		{
			httpHandler.post(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						httpResponse.onSuccess(jsonData, isHandle, clazz);
					}
					catch (Exception e)
					{
						httpResponse.onFailure(e);
					}
				}
			});
		}
		else
		{
			try
			{
				httpResponse.onSuccess(jsonData, isHandle, clazz);
			}
			catch (Exception e)
			{
				httpResponse.onFailure(e);
			}
		}
	}

	/**
	 * 网络请求失败
	 *
	 * @param ex
	 */
	public void handleFailure(final Exception ex)
	{
		// 是否需要 Handler 处理一次
		if (isHandler)
		{
			httpHandler.post(new Runnable()
			{
				@Override
				public void run()
				{
					httpResponse.onFailure(ex);
				}
			});
		}
		else
		{
			httpResponse.onFailure(ex);
		}
	}
}
