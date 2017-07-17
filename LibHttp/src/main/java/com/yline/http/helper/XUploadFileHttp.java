package com.yline.http.helper;

import com.yline.http.XHttpAdapter;
import com.yline.http.util.LogUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 实现文件上传(小文件),单个图片请求
 *
 * @author yline 2017/4/14 -- 15:09
 * @version 1.0.0
 */
public abstract class XUploadFileHttp<Result>
{
	private XHttpHelper xHttpHelper;

	private boolean isDebug;

	public XUploadFileHttp(XHttpAdapter adapter)
	{
		this.isDebug = adapter.isDebug();
		this.xHttpHelper = new XHttpHelper(adapter, isResponseHandler());
	}

	public void doPost(String httpUrl, final Class<Result> clazz)
	{
		OkHttpClient okHttpClient = getOkHttpClient();

		Request request = getRequest(httpUrl);

		okHttpClient.newCall(request).enqueue(new Callback()
		{
			@Override
			public void onFailure(Call call, IOException e)
			{
				xHttpHelper.handleFailure(e);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException
			{
				handleResponse(xHttpHelper, response, clazz);
			}
		});
	}

	protected RequestBody getRequestBody()
	{
		MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();

		bodyBuilder.setType(MultipartBody.FORM);
		initRequestForm(bodyBuilder);

		MultipartBody multipartBody = bodyBuilder.build();
		if (isDebug)
		{
			LogUtil.v("MultipartBody Size = " + multipartBody.size());
		}
		
		return multipartBody;
	}

	private Request getRequest(String httpUrl)
	{
		if (isDebug)
		{
			LogUtil.v("httpUrl = " + httpUrl);
		}
		Request.Builder requestBuilder = new Request.Builder();

		requestBuilder.url(httpUrl);
		requestBuilder.post(getRequestBody());

		return requestBuilder.build();
	}

	private OkHttpClient getOkHttpClient()
	{
		OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();

		// 设置缓存; 略过
		// 设置超时
		okClientBuilder.connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS);

		return okClientBuilder.build();
	}

	private void handleResponse(XHttpHelper httpHelper, Response response, Class<Result> clazz) throws IOException
	{
		String jsonResult = response.body().string();

		// 入口日志
		if (isDebug)
		{
			LogUtil.v("response " + (null == jsonResult ? "null" : jsonResult.toString()));
		}

		httpHelper.handleSuccess(jsonResult, isResponseCodeHandler(), isResponseParse() ? clazz : null);
	}

	/* %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 重写的数据 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% */

	protected abstract void initRequestForm(MultipartBody.Builder bodyBuilder);

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
