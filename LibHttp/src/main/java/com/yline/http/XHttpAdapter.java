package com.yline.http;

import com.google.gson.Gson;
import com.yline.http.helper.IHttpResponse;
import com.yline.http.util.LogUtil;

import org.json.JSONObject;

/**
 * 配置每次的请求参数
 *
 * @param <Result>
 */
public abstract class XHttpAdapter<Result> implements IHttpResponse<Result>
{
	public static final int REQUEST_SUCCESS_CODE = 0;

	/**
	 * 默认规则下，数据的回调
	 *
	 * @param result
	 */
	public abstract void onSuccess(Result result);

	/**
	 * @param code    code码，非零情况下
	 * @param content code码对应的字符串
	 */
	public void onSuccess(int code, String content)
	{

	}

	@Override
	public void onSuccess(String jsonData, boolean isHandle, Class<Result> clazz) throws Exception
	{
		if (isHandle) // 进行code处理一次
		{
			JSONObject jsonObject = new JSONObject(jsonData);

			int code = jsonObject.getInt("code");
			String jsonContent = jsonObject.getString("data");
			if (null == clazz || code != REQUEST_SUCCESS_CODE || (clazz == String.class))
			{
				onSuccess(code, jsonContent);
			}
			else
			{
				Result result = new Gson().fromJson(jsonContent, clazz);
				onSuccess(result);
			}
		}
		else
		{
			onSuccess(Integer.MIN_VALUE, jsonData);
		}
	}

	@Override
	public void onFailure(Exception ex)
	{
		if (isDebug())
		{
			LogUtil.e("onFailure net exception happened", ex);
		}
	}

	public boolean isDebug()
	{
		return XHttpConstant.isDefaultDebug();
	}
}
