package com.lib.http.demo.http;

import android.support.annotation.NonNull;

import com.yline.http.XHttpAdapter;
import com.yline.http.request.XHttp;
import com.yline.http.response.IResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;

/**
 * 重写, Http 请求 规定
 *
 * @author yline 2017/7/22 -- 9:58
 * @version 1.0.0
 */
public class YlineHttp extends XHttp
{
	@Override
	public <T> void doGet(String actionUrl, Map<String, Object> actionMap, Class<T> clazz, XHttpAdapter<T> adapter)
	{
		super.doGet(actionUrl, actionMap, clazz, adapter);
	}

	@Override
	public <T> void doPost(String actionUrl, Object jsonParam, Class<T> clazz, XHttpAdapter<T> adapter)
	{
		super.doPost(actionUrl, jsonParam, clazz, adapter);
	}

	@Override
	public <T> void doPost(String actionUrl, @NonNull MultipartBody.Builder bodyBuilder, Class<T> clazz, XHttpAdapter<T> adapter)
	{
		super.doPost(actionUrl, bodyBuilder, clazz, adapter);
	}

	@Override
	protected <T> IResponse getHttpResponse(XHttpAdapter<T> adapter)
	{
		return new YlineResponse(adapter, this);
	}

	@Override
	protected OkHttpClient getHttpClient()
	{
		return super.getHttpClient();
	}

	@Override
	public boolean isResponseCodeHandle()
	{
		return super.isResponseCodeHandle();
	}

	@Override
	public boolean isResponseHandler()
	{
		return super.isResponseHandler();
	}

	@Override
	public boolean isResponseJsonType()
	{
		return super.isResponseJsonType();
	}

	@Override
	public boolean isDebug()
	{
		return super.isDebug();
	}
}
