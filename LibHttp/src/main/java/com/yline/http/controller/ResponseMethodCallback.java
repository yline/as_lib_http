package com.yline.http.controller;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 统一的 让外界处理的 回调
 *
 * @author yline 2017/10/23 -- 19:58
 * @version 1.0.0
 */
public interface ResponseMethodCallback<Result> {
    /**
     * 回调 数据
     *
     * @param call     请求参数
     * @param response 返回的结构体
     * @param result   回调的数据
     */
    void onSuccess(Call call, Response response, Result result);

    /**
     * 错误处理
     *
     * @param call 请求参数
     * @param ex   具体的错误
     */
    void onFailure(Call call, Exception ex);
}
