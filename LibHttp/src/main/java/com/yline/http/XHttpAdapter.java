package com.yline.http;

import com.google.gson.JsonParseException;
import com.yline.http.util.LogUtil;

import org.json.JSONException;

/**
 * 配置每次的请求参数
 */
public abstract class XHttpAdapter<Result>
{
	// 默认成功的Code
	public static final int REQUEST_SUCCESS_CODE = 0;

	/**
	 * 经过了Json解析，回调数据
	 *
	 * @param result
	 */
	public abstract void onSuccess(Result result);

	/**
	 *
	 * @param code 不为0，若为Integer.MIN_VALUE，则没有经过code的解析
	 * @param data 对应的，数据
	 * @throws JSONException
	 * @throws JsonParseException
	 */
	public void onSuccess(int code, String data) throws JSONException, JsonParseException
	{

	}

	public void onFailure(Exception ex, boolean isDebug)
	{
		if (isDebug)
		{
			LogUtil.e("onFailure net exception happened", ex);
		}
	}
}
