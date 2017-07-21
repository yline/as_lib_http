package com.yline.http.response;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.yline.http.XHttpAdapter;
import com.yline.http.helper.HttpHandler;
import com.yline.http.request.IRequestParam;
import com.yline.http.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
	private HttpHandler httpHandler;

	private IRequestParam iRequestParam;

	private XHttpAdapter adapter;

	public XHttpResponse(XHttpAdapter adapter, IRequestParam iRequestParam)
	{
		this.adapter = adapter;
		this.iRequestParam = iRequestParam;

		if (iRequestParam.isResponseHandler())
		{
			httpHandler = HttpHandler.build();
		}
	}

	@Override
	public <T> void handleSuccess(Call call, Response response, Class<T> clazz) throws IOException
	{
		String responseData = response.body().string();

		// http 出口日志
		if (iRequestParam.isDebug())
		{
			LogUtil.v("response = " + responseData);
		}

		try
		{
			if (iRequestParam.isResponseJsonType())
			{
				if (iRequestParam.isResponseCodeHandle())
				{
					JSONObject jsonObject = new JSONObject(responseData);
					int code = jsonObject.getInt("code");
					String data = jsonObject.getString("data");

					if (code == XHttpAdapter.REQUEST_SUCCESS_CODE)
					{
						// code -> json 解析 -> 返回数据
						T result = new Gson().fromJson(data, clazz);
						handleAdapter(result);
					}
					else
					{
						// code 解析 -> 返回数据
						handleAdapter(code, data);
					}
				}
				else
				{
					// json 解析 -> 返回数据
					T result = new Gson().fromJson(responseData, clazz);
					handleAdapter(result);
				}
			}
			else
			{
				// 直接，返回数据
				handleAdapter(Integer.MIN_VALUE, responseData);
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

	private void handleAdapter(final int code, final String data)
	{
		if (iRequestParam.isResponseHandler())
		{
			httpHandler.post(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						adapter.onSuccess(code, data);
					}
					catch (JSONException e)
					{
						e.printStackTrace();
					}
					catch (JsonParseException e)
					{
						e.printStackTrace();
					}
				}
			});
		}
		else
		{
			try
			{
				adapter.onSuccess(code, data);
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
			catch (JsonParseException e)
			{
				e.printStackTrace();
			}
		}
	}

	private <T> void handleAdapter(final T result)
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
