package com.yline.http.helper;

import com.google.gson.Gson;
import com.yline.http.XHttpAdapter;
import com.yline.http.cache.CacheManager;
import com.yline.http.util.LogUtil;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 实现Http请求管理,并进行Http请求
 *
 * @author yline 2017/3/9 -- 13:14
 * @version 1.0.0
 */
public class XTextHttp<Result>
{
	public static final int REQUEST_POST = 0;

	public static final int REQUEST_GET = 1;

	private XHttpHelper httpHelper;

	private boolean isDebug;

	public XTextHttp(XHttpAdapter adapter)
	{
		this.isDebug = adapter.isDebug();
		this.httpHelper = new XHttpHelper(adapter, isResponseHandler());
	}

	/**
	 * Get方式的网络请求
	 *
	 * @param actionUrl
	 * @param map
	 * @param clazz
	 */
	public void doGet(String actionUrl, Map<String, String> map, final Class<Result> clazz)
	{
		// 配置Client
		OkHttpClient okHttpClient = getClient();

		// 配置请求参数
		Request request = getRequest(actionUrl, REQUEST_GET, null, map);

		// 发送请求
		okHttpClient.newCall(request).enqueue(new Callback()
		{
			@Override
			public void onFailure(Call call, final IOException e)
			{
				httpHelper.handleFailure(e);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException
			{
				handleResponse(httpHelper, response, clazz);
			}
		});
	}

	/**
	 * Post方式的网络请求
	 *
	 * @param actionUrl
	 * @param clazz
	 */
	public void doPost(String actionUrl, Object requestParam, final Class<Result> clazz)
	{
		// 配置Client
		OkHttpClient okHttpClient = getClient();

		// 配置请求参数
		Request request = getRequest(actionUrl, REQUEST_POST, requestParam, null);

		// 发送请求
		okHttpClient.newCall(request).enqueue(new Callback()
		{
			@Override
			public void onFailure(Call call, final IOException e)
			{
				httpHelper.handleFailure(e);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException
			{
				handleResponse(httpHelper, response, clazz);
			}
		});
	}

	/* --------------------------- 设置请求配置参数 --------------------------- */

	/**
	 * 默认不设置的一些配置,可以让用户自定义配置；例如Header
	 * 但是如果有该文件内已经配置好的,则已配置的优先
	 *
	 * @param actionUrl
	 * @param requestType
	 * @param postRequestBody
	 * @return
	 */
	private Request getRequest(String actionUrl, int requestType, Object postRequestBody, Map<String, String> getMap)
	{
		Request.Builder builder = new Request.Builder();

		// 1,cache; 采用拦截器的方式实现

		// 2,get、post区分
		if (REQUEST_POST == requestType)
		{
			String postHttpUrl = getRequestUrlBase() + actionUrl;
			if (isDebug)
			{
				LogUtil.v("Request post Url " + postHttpUrl);
			}

			builder.url(postHttpUrl);
			builder.post(getPostRequestBody(postRequestBody));
		}
		else if (REQUEST_GET == requestType)
		{
			String getHttpUrl = String.format("%s%s?%s", getRequestUrlBase(), actionUrl, getGetParamUrl(getMap));
			if (isDebug)
			{
				LogUtil.v("Request get Url " + getHttpUrl);
			}

			builder.url(getHttpUrl);
		}

		// 丢给子类 实现更多功能
		onRequestBuild(builder);

		return builder.build();
	}

	private String getGetParamUrl(Map<String, String> map)
	{
		if (null == map)
		{
			return "";
		}
		else
		{
			StringBuffer stringBuffer = new StringBuffer();
			boolean isFirst = true;
			for (String key : map.keySet())
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
				stringBuffer.append(map.get(key));
			}
			return stringBuffer.toString();
		}
	}

	private RequestBody getPostRequestBody(Object object)
	{
		String jsonBody;
		if (null == object)
		{
			jsonBody = "";
		}
		else
		{
			jsonBody = new Gson().toJson(object);
		}

		if (isDebug)
		{
			LogUtil.v("post requestBody jsonBody = " + jsonBody);
		}
		return RequestBody.create(getRequestPostMediaType(), jsonBody);
	}

	/* --------------------------- 处理返回参数 --------------------------- */
	protected void handleResponse(XHttpHelper httpHelper, Response response, Class<Result> clazz) throws IOException
	{
		String jsonResult = response.body().string();

		// 入口日志
		if (isDebug)
		{
			LogUtil.v("response" + (null == jsonResult ? "null" : jsonResult.toString()));
		}

		httpHelper.handleSuccess(jsonResult, isResponseCodeHandler(), isResponseParse() ? clazz : null);
	}
	
	/**
	 * 默认 单例方式获取 HttpNetThanCacheClient
	 *
	 * @return
	 */
	protected OkHttpClient getClient()
	{
		// return HttpDefaultClient.getInstance();
		// return HttpOnlyNetClient.getInstance();
		return HttpNetThanCacheClient.getInstance();
		// return HttpCacheAndNetClient.getInstance();
	}

	/**
	 * 添加 Request信息
	 *
	 * @param builder
	 */
	protected void onRequestBuild(Request.Builder builder)
	{

	}

	/**
	 * 设置请求,头Url:例如：工程未确定之前,就不要弄前缀在这里了
	 * http://120.92.35.211/wanghong/wh/index.php/Back/Api/:
	 *
	 * @return
	 */
	protected String getRequestUrlBase()
	{
		return "";
	}

	protected MediaType getRequestPostMediaType()
	{
		return CacheManager.DEFAULT_MEDIA_TYPE;
	}

	/**
	 * 是否需要 Handler 处理一次
	 *
	 * @return
	 */
	protected boolean isResponseHandler()
	{
		return true;
	}

	/**
	 * 是否先进行一次Code解析
	 *
	 * @return
	 */
	protected boolean isResponseCodeHandler()
	{
		return true;
	}

	/**
	 * 如果返回的,以字符串输出；则设置成true
	 * 如果需要解析,则设置成false
	 *
	 * @return
	 */
	protected boolean isResponseParse()
	{
		return true;
	}
}
