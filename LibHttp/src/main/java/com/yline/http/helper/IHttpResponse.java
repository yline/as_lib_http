package com.yline.http.helper;

/**
 * Http请求回调
 *
 * @author yline 2017/2/22 -- 17:07
 * @version 1.0.0
 */
public interface IHttpResponse<T>
{
	/**
	 * 获取到网络数据，直接回调
	 *
	 * @param jsonData 最开始的Json数据
	 * @param isHandle 是否 进行默认处理
	 * @param clazz    如果处理，那么对应的json类是什么；若不处理，则为null
	 */
	void onSuccess(String jsonData, boolean isHandle, Class<T> clazz) throws Exception;

	/**
	 * 网络错误
	 *
	 * @param ex 错误具体
	 */
	void onFailure(Exception ex);
}
