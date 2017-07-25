package com.yline.http.response;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yline.http.XHttpAdapter;
import com.yline.http.cache.XCache;
import com.yline.http.request.IRequestParam;
import com.yline.http.util.LogUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Http 统一处理类
 *
 * @author yline 2017/7/21 -- 15:32
 * @version 1.0.0
 */
public class XHttpResponse implements IResponse
{
	protected HttpHandler httpHandler;

	protected IRequestParam iRequestParam;

	protected XHttpAdapter adapter;

	public XHttpResponse(XHttpAdapter adapter, IRequestParam iRequestParam)
	{
		this.adapter = adapter;
		this.iRequestParam = iRequestParam;

		if (iRequestParam.isResponseHandler())
		{
			httpHandler = HttpHandler.build();
		}
	}

	/**
	 * 这部分 允许 自定义
	 * 读取Response，并复制成两份；一份输出，一份保存
	 *
	 * @param response
	 * @return
	 */
	protected String setCache(Response response) throws IOException
	{
		ByteArrayOutputStream baoStream = null;
		InputStream cacheStream = null;
		InputStream returnStream = null;
		try
		{
			InputStream responseStream = response.body().byteStream();

			baoStream = new ByteArrayOutputStream();

			byte[] buffer = new byte[1024];
			int len;
			while ((len = responseStream.read(buffer)) > -1)
			{
				baoStream.write(buffer, 0, len);
			}
			baoStream.flush();

			cacheStream = new ByteArrayInputStream(baoStream.toByteArray());
			getCacheInstance().setCache(response, cacheStream);

			returnStream = new ByteArrayInputStream(baoStream.toByteArray());
			StringBuffer stringBuffer = new StringBuffer();
			byte[] returnBuffer = new byte[1024];
			int returnLen;
			while ((returnLen = returnStream.read(returnBuffer)) > -1)
			{
				stringBuffer.append(new String(returnBuffer, 0, returnLen));
			}
			return stringBuffer.toString();
		}
		finally
		{
			if (null != baoStream)
			{
				baoStream.close();
			}

			if (null != cacheStream)
			{
				cacheStream.close();
			}

			if (null != returnStream)
			{
				returnStream.close();
			}
		}
	}

	/**
	 * 自定义 Cache 单例类
	 *
	 * @return
	 */
	protected XCache getCacheInstance()
	{
		return XCache.getInstance();
	}

	@Override
	public <T> void handleSuccess(Call call, Response response, Class<T> clazz) throws IOException
	{
		// 实现缓存策略
		String responseData;
		if (iRequestParam.isResponseCache())
		{
			responseData = setCache(response);
		}
		else
		{
			responseData = response.body().string();
		}

		// 提供 http 出口日志
		if (iRequestParam.isDebug())
		{
			LogUtil.v("response = " + responseData);
		}

		handleSuccess(responseData, clazz);
	}

	/**
	 * 输出结果给 adapter
	 *
	 * @param responseData
	 * @param clazz
	 * @param <T>
	 */
	protected <T> void handleSuccess(String responseData, Class<T> clazz) throws IOException
	{
		try
		{
			if (null == clazz)
			{
				handleAdapter(null);
			}
			else if (clazz == String.class)
			{
				handleAdapter(responseData);
			}
			else
			{
				// json 解析 -> 返回数据
				T result = new Gson().fromJson(responseData, clazz);
				handleAdapter(result);
			}
		}
		catch (JsonSyntaxException e)
		{
			e.printStackTrace();
		}
	}

	protected <T> void handleAdapter(final T result)
	{
		if (iRequestParam.isResponseHandler())
		{
			httpHandler.post(new Runnable()
			{
				@Override
				public void run()
				{
					adapter.onSuccess(result);
				}
			});
		}
		else
		{
			adapter.onSuccess(result);
		}
	}

	@Override
	public <T> void handleFailure(Call call, final IOException ex)
	{
		if (iRequestParam.isResponseHandler())
		{
			httpHandler.post(new Runnable()
			{
				@Override
				public void run()
				{
					adapter.onFailure(ex, iRequestParam.isDebug());
				}
			});
		}
		else
		{
			adapter.onFailure(ex, iRequestParam.isDebug());
		}
	}
}
