package com.lib.http.demo.http;

import com.yline.http.XHttpAdapter;

public abstract class YlineAdapter<Result> extends XHttpAdapter<Result>
{
	/**
	 * @param code   不为0，若为Integer.MIN_VALUE，则没有经过code的解析
	 * @param result 对应的，数据
	 */
	public void onSuccess(int code, Result result)
	{
		if (code == REQUEST_SUCCESS_CODE)
		{
			onSuccess(result);
		}
	}
}
