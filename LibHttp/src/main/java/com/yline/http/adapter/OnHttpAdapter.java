package com.yline.http.adapter;

import com.yline.http.callback.OnParseCallback;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 所有支持配置的信息，都由这个类统一提供配置
 *
 * @author yline 2018/8/21 -- 16:33
 */
public interface OnHttpAdapter {
	/**
	 * 构建请求的 Client
	 * 主线程执行
	 *
	 * @return 请求的Client
	 */
	OkHttpClient getHttpClient();
	
	/**
	 * 构建请求的内容
	 * 主线程执行
	 *
	 * @return 请求的Request
	 */
	Request getHttpRequest();
	
	/**
	 * 处理最终的返回数据
	 * 子线程执行
	 *
	 * @param response 返回内容
	 * @param type     泛型的type
	 * @param <T>      对应的数据结构
	 * @throws IOException 异常信息
	 */
	<T> void handleResponse(Response response, Type type, OnParseCallback<T> callback) throws IOException;
	
	/**
	 * 异常信息处理
	 * 主线程执行
	 *
	 * @param ex   异常
	 * @param code 错误码 -100{IO异常}，-200{Json解析异常}，其它{服务器返回异常}
	 * @param msg  错误信息{可能为null}
	 * @return 最终呈现的字符串
	 */
	String handleFailureException(final Exception ex, final int code, final String msg);
}
