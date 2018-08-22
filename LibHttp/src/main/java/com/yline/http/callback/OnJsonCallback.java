package com.yline.http.callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 返回结构
 *
 * @author yline 2018/8/21 -- 16:28
 */
public abstract class OnJsonCallback<T> {
	/**
	 * 请求失败
	 *
	 * @param code 服务器的code
	 * @param msg  服务器返回的异常信息（可能为null）
	 */
	public abstract void onFailure(int code, String msg);
	
	/**
	 * 请求成功，数据返回
	 *
	 * @param t 泛型数据，可能为null
	 */
	public abstract void onResponse(T t);
	
	/**
	 * 依据回调，获取泛型的type
	 *
	 * @return 泛型的type
	 */
	public Type getSuperclassType() {
		Type superclassType = this.getClass().getGenericSuperclass();
		if (superclassType instanceof Class) {
			throw new RuntimeException("Missing type parameter");
		}
		ParameterizedType parameterizedType = (ParameterizedType) superclassType;
		return parameterizedType.getActualTypeArguments()[0];
	}
}
