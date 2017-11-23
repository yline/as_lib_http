package com.yline.http.controller;

import com.google.gson.Gson;
import com.yline.http.manager.CacheManager;
import com.yline.http.manager.LibManager;
import com.yline.http.manager.XHttpAdapter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Http 统一处理类
 *
 * @author yline 2017/10/23 -- 20:07
 * @version 1.0.0
 */
public class HttpDefaultResponse implements ResponseHandlerCallback {
    protected ResponseHandlerConfigCallback mResponseConfig;
    protected ResponseMethodCallback mResponseCallback;
    private HttpDefaultHandler mHttpHandler;

    public HttpDefaultResponse(XHttpAdapter adapter) {
        this.mResponseCallback = adapter;
        this.mResponseConfig = adapter;

        if (mResponseConfig.isResponseHandler()) {
            mHttpHandler = HttpDefaultHandler.build();
        }
    }

    @Override
    public <T> void handleSuccess(Call call, Response response, Class<T> clazz) throws IOException {
        // 实现缓存策略
        String responseData;
        if (mResponseConfig.isResponseCache()) {
            responseData = CacheManager.setCache(response);
        } else {
            responseData = response.body().string();
        }

        // 提供 http 出口日志
        LibManager.vRequest("response = " + responseData);

        // 回调处理
        handleSuccess(call, response, clazz, responseData, new ResponseMethodCallback<T>() {
            @Override
            public void onSuccess(Call call, Response response, T t) {
                sendSuccess(call, response, t);
            }

            @Override
            public void onFailure(Call call, Exception ex) {
                sendFailure(call, ex);
            }
        });
    }

    @Override
    public <T> void handleFailure(Call call, final IOException ex) {
        sendFailure(call, ex);
    }

    /**
     * 提供 请求成功单独处理的方法； 例如大部分的情况需要外面加上"一层基础数据"
     * 该方法在子线程内，因此避免了主线程执行
     *
     * @param call             OKHttp自带 请求参数
     * @param response         OKHttp自带 返回数据
     * @param clazz            返回数据的结构体
     * @param responseData     返回数据字符串
     * @param responseCallback Handler处理回调
     * @param <T>              返回数据的结构体
     * @throws IOException IO数据异常
     */
    protected <T> void handleSuccess(Call call, Response response, Class<T> clazz, String responseData, ResponseMethodCallback<T> responseCallback) throws IOException {
        try {
            if (null == clazz) {
                responseCallback.onSuccess(call, response, null);
            } else if (clazz == String.class) {
                responseCallback.onSuccess(call, response, clazz.cast(responseData));
            } else {
                // json 解析 -> 返回数据
                T result = new Gson().fromJson(responseData, clazz);
                responseCallback.onSuccess(call, response, result);
            }
        } catch (Exception e) {
            responseCallback.onFailure(call, e);
        }
    }

    private <T> void sendSuccess(final Call call, final Response response, final T result) {
        if (mResponseConfig.isResponseHandler()) {
            mHttpHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResponseCallback.onSuccess(call, response, result);
                }
            });
        } else {
            mResponseCallback.onSuccess(call, response, result);
        }
    }

    private <T> void sendFailure(final Call call, final Exception ex) {
        if (mResponseConfig.isResponseHandler()) {
            mHttpHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResponseCallback.onFailure(call, ex);
                }
            });
        } else {
            mResponseCallback.onFailure(call, ex);
        }
    }
}
