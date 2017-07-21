package com.yline.http.response;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Http 返回内部处理
 *
 * @author yline 2017/7/21 -- 15:01
 * @version 1.0.0
 */
public interface IResponse
{
	/**
	 * 请求成功
	 *
	 * @param call
	 * @param response 返回的数据
	 * @param clazz    请求时，确定的返回对象.class
	 * @param <T>      返回的数据类型
	 */
	<T> void handleSuccess(Call call, Response response, Class<T> clazz) throws IOException;

	/**
	 * 请求失败
	 *
	 * @param call
	 * @param ex   错误类型
	 * @param <T>  返回的数据类型
	 */
	<T> void handleFailure(Call call, IOException ex);
}
