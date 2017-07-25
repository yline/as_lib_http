package com.lib.http.demo.http;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yline.http.XHttpAdapter;
import com.yline.http.request.IRequestParam;
import com.yline.http.response.XHttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 自定义 处理类
 *
 * @author yline 2017/7/22 -- 10:09
 * @version 1.0.0
 */
public class YlineResponse extends XHttpResponse
{
	public YlineResponse(XHttpAdapter adapter, IRequestParam iRequestParam)
	{
		super(adapter, iRequestParam);
	}

	@Override
	protected <T> void handleSuccess(String responseData, Class<T> clazz) throws IOException
	{
		if (iRequestParam.isResponseCodeHandle() && adapter instanceof YlineAdapter)
		{
			try
			{
				JSONObject jsonObject = new JSONObject(responseData);
				int code = jsonObject.getInt("code");
				String data = jsonObject.getString("data");

				if (null == clazz)
				{
					handlerYlineAdapter((YlineAdapter) adapter, Integer.MIN_VALUE, null);
				}
				else if (clazz == String.class)
				{
					handlerYlineAdapter((YlineAdapter) adapter, code, data);
				}
				else
				{
					// code -> json 解析 -> 返回数据
					T result = new Gson().fromJson(data, clazz);
					handlerYlineAdapter((YlineAdapter) adapter, code, result);
					handleAdapter(result);
				}
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
			catch (JsonSyntaxException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			super.handleSuccess(responseData, clazz);
		}
	}

	private <T> void handlerYlineAdapter(final YlineAdapter ylineAdapter, final int code, final T result)
	{
		if (iRequestParam.isResponseHandler())
		{
			httpHandler.post(new Runnable()
			{
				@Override
				public void run()
				{
					ylineAdapter.onSuccess(code, result);
				}
			});
		}
		else
		{
			ylineAdapter.onSuccess(code, result);
		}
	}
}
