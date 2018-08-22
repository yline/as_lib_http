package com.yline.http.callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 解析具体的数据
 * 不可以是接口类型，如果是接口类型，则获取不到具体的type
 *
 * @author yline 2018/8/22 -- 9:50
 */
public interface OnParseCallback<T> {
	/**
	 * 解析成功，服务器返回正确的信息
	 *
	 * @param t 正确信息对应的数据结构
	 */
	void onResponseSuccess(T t);
	
	/**
	 * 解析成功，服务器返回错误信息
	 *
	 * @param ex   自定义错误内容
	 * @param code 错误编号
	 * @param msg  服务器返回的错误信息
	 */
	void onResponseError(Exception ex, int code, String msg);
}
