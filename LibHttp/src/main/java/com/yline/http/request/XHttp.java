package com.yline.http.request;

import com.google.gson.Gson;
import com.yline.http.XHttpAdapter;
import com.yline.http.cache.CacheManager;
import com.yline.http.helper.HttpDefaultClient;
import com.yline.http.response.IResponse;
import com.yline.http.response.XHttpResponse;
import com.yline.http.util.LogUtil;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class XHttp implements IRequest, IRequestParam
{
	public XHttp()
	{
	}

	@Override
	public <T> void doGet(String actionUrl, Map<String, Object> actionMap, final Class<T> clazz, XHttpAdapter<T> adapter)
	{
		final IResponse iResponse = new XHttpResponse(adapter, this);

		// 配置Client
		OkHttpClient okHttpClient = getHttpClient();

		// 配置请求参数
		Request request = genGetRequest(actionUrl, actionMap);

		// 发送请求
		okHttpClient.newCall(request).enqueue(new Callback()
		{
			@Override
			public void onFailure(Call call, final IOException e)
			{
				iResponse.handleFailure(call, e);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException
			{
				iResponse.handleSuccess(call, response, clazz);
			}
		});
	}

	@Override
	public <T> void doPost(String actionUrl, Object jsonParam, final Class<T> clazz, XHttpAdapter<T> adapter)
	{
		final IResponse iResponse = new XHttpResponse(adapter, this);

		// 配置Client
		OkHttpClient okHttpClient = getHttpClient();

		// 配置请求参数
		Request request = genPostJsonRequest(actionUrl, jsonParam);

		// 发送请求
		okHttpClient.newCall(request).enqueue(new Callback()
		{
			@Override
			public void onFailure(Call call, final IOException e)
			{
				iResponse.handleFailure(call, e);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException
			{
				iResponse.handleSuccess(call, response, clazz);
			}
		});
	}

	@Override
	public <T> void doPost(String actionUrl, MultipartBody.Builder bodyBuilder, final Class<T> clazz, XHttpAdapter<T> adapter)
	{
		final IResponse iResponse = new XHttpResponse(adapter, this);

		// 配置Client
		OkHttpClient okHttpClient = getHttpClient();

		// 配置请求参数
		Request request = genPostMultiRequest(actionUrl, bodyBuilder);

		// 发送请求
		okHttpClient.newCall(request).enqueue(new Callback()
		{
			@Override
			public void onFailure(Call call, IOException e)
			{
				iResponse.handleFailure(call, e);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException
			{
				iResponse.handleSuccess(call, response, clazz);
			}
		});
	}

	private Request genPostMultiRequest(String actionUrl, MultipartBody.Builder bodyBuilder)
	{
		Request.Builder builder = new Request.Builder();

		// 1,cache; 采用拦截器的方式实现
		// 2,post区分
		String postHttpUrl = actionUrl;
		builder.url(postHttpUrl);

		// body
		bodyBuilder.setType(MultipartBody.FORM);
		MultipartBody multipartBody = bodyBuilder.build();
		builder.post(multipartBody);

		if (isDebug())
		{
			LogUtil.v("post request url = " + postHttpUrl + ", body size = " + multipartBody.size());
		}

		onRequestBuilder(builder);

		return builder.build();
	}

	private Request genPostJsonRequest(String actionUrl, Object jsonParam)
	{
		Request.Builder builder = new Request.Builder();

		// 1,cache; 采用拦截器的方式实现
		// 2,post区分
		String postHttpUrl = actionUrl;
		if (isDebug())
		{
			LogUtil.v("post request url = " + postHttpUrl);
		}

		builder.url(postHttpUrl);
		builder.post(genPostJsonRequestBody(jsonParam));

		onRequestBuilder(builder);

		return builder.build();
	}

	private RequestBody genPostJsonRequestBody(Object jsonParam)
	{
		String jsonBody;
		if (null == jsonParam)
		{
			jsonBody = "";
		}
		else
		{
			jsonBody = new Gson().toJson(jsonParam);
		}

		if (isDebug())
		{
			LogUtil.v("post requestBody = " + jsonBody);
		}
		return RequestBody.create(CacheManager.DEFAULT_MEDIA_TYPE, jsonBody);
	}

	/**
	 * 生成 Http的Url
	 *
	 * @param actionUrl
	 * @param actionMap
	 * @return
	 */
	private Request genGetRequest(String actionUrl, Map<String, Object> actionMap)
	{
		Request.Builder builder = new Request.Builder();

		// 1,cache; 采用拦截器的方式实现
		// 2,get区分
		String getHttpUrl = String.format("%s?%s", actionUrl, genGetParamUrl(actionMap));
		if (isDebug())
		{
			LogUtil.v("get request url = " + getHttpUrl);
		}
		builder.url(getHttpUrl);

		onRequestBuilder(builder);

		return builder.build();
	}

	/**
	 * 将 Map 生成为 Get请求的 Url部分
	 *
	 * @param actionMap
	 * @return
	 */
	private String genGetParamUrl(Map<String, Object> actionMap)
	{
		if (null == actionMap)
		{
			return "";
		}
		else
		{
			StringBuffer stringBuffer = new StringBuffer();
			boolean isFirst = true;
			for (String key : actionMap.keySet())
			{
				if (isFirst)
				{
					isFirst = false;
				}
				else
				{
					stringBuffer.append("&");
				}
				stringBuffer.append(key);
				stringBuffer.append("=");
				stringBuffer.append(actionMap.get(key));
			}
			return stringBuffer.toString();
		}
	}

	/**
	 * 添加 Request信息
	 * 丢给子类 实现更多功能
	 *
	 * @param builder
	 */
	protected void onRequestBuilder(Request.Builder builder)
	{
		// To Do
	}

	protected OkHttpClient getHttpClient()
	{
		return HttpDefaultClient.getInstance();
	}

	@Override
	public boolean isResponseCodeHandle()
	{
		return true;
	}

	@Override
	public boolean isResponseHandler()
	{
		return true;
	}

	@Override
	public boolean isResponseJsonType()
	{
		return true;
	}

	@Override
	public boolean isDebug()
	{
		return true;
	}
}
