package com.lib.http.demo.http;

import com.yline.http.XHttpAdapter;
import com.yline.http.request.IRequestParam;
import com.yline.http.response.XHttpResponse;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

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
	public <T> void handleSuccess(Call call, Response response, Class<T> clazz) throws IOException
	{
		super.handleSuccess(call, response, clazz);
	}

	@Override
	protected String setCache(Response response) throws IOException
	{
		return super.setCache(response);
	}

	@Override
	public <T> void handleFailure(Call call, IOException ex)
	{
		super.handleFailure(call, ex);
	}
}
