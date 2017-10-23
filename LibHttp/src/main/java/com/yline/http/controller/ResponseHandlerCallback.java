package com.yline.http.controller;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 回调先处理 一遍
 *
 * @author yline 2017/10/23 -- 19:45
 * @version 1.0.0
 */
public interface ResponseHandlerCallback {
    /**
     * 请求成功
     *
     * @param call     回调数据
     * @param response 返回的数据
     * @param clazz    请求时，确定的返回对象.class
     * @param <T>      返回的数据类型
     * @throws IOException 读取数据流异常
     */
    <T> void handleSuccess(Call call, Response response, Class<T> clazz) throws IOException;

    /**
     * 请求失败
     *
     * @param call 回调数据
     * @param ex   错误类型
     * @param <T>  返回的数据类型
     */
    <T> void handleFailure(Call call, IOException ex);
}
