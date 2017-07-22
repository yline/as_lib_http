package com.yline.http.request;

/**
 * 请求参数 配置
 *
 * @author yline 2017/7/21 -- 15:07
 * @version 1.0.0
 */
public interface IRequestParam
{
	/**
	 * 进入参数，进行一层处理
	 *
	 * @return true 处理，false 不处理
	 */
	boolean isResponseCodeHandle();

	/**
	 * 是否 经过，Handler处理
	 *
	 * @return true 处理，false 不处理
	 */
	boolean isResponseHandler();

	/**
	 * 返回的数据，是否满足Json的要求
	 *
	 * @return true json，false 其它
	 */
	boolean isResponseJsonType();

	/**
	 * 返回的数据，是否进行缓存
	 *
	 * @return true cache, false 不缓存
	 */
	boolean isResponseCache();

	/**
	 * 是否 经过，debug处理
	 *
	 * @return
	 */
	boolean isDebug();
}
