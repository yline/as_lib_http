package com.yline.http;

import com.yline.http.util.LogUtil;

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

	public void onFailure(Exception ex, boolean isDebug)
	{
		if (isDebug)
		{
			LogUtil.e("onFailure net exception happened", ex);
		}
	}
}
