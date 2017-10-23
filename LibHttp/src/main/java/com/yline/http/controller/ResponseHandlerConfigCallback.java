package com.yline.http.controller;

/**
 * 回调先处理 一遍 配置信息
 *
 * @author yline 2017/10/23 -- 19:49
 * @version 1.0.0
 */
public interface ResponseHandlerConfigCallback {
    /**
     * 获取 回调处理的具体实现类
     *
     * @return 实现类
     */
    ResponseHandlerCallback getRequestHandler();

    /**
     * 是否 经过，Handler处理
     *
     * @return true 处理，false 不处理
     */
    boolean isResponseHandler();

    /**
     * 返回的数据，是否进行缓存
     *
     * @return true cache, false 不缓存
     */
    boolean isResponseCache();
}
