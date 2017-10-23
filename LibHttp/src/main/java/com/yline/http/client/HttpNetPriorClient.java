package com.yline.http.client;

import com.yline.http.interceptor.NetPriorInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 按照 网络优先的 规则
 * 有网络 -- 读取网络
 * 无网络 -- 读取缓存 -- 无缓存 -- 返回失败
 *
 * @author yline 2017/2/28 -- 17:29
 * @version 1.0.0
 */
public class HttpNetPriorClient extends OkHttpClient {
    private HttpNetPriorClient() {
    }

    public static OkHttpClient getInstance() {
        return HttpNetThanCacheHolder.getHttpClient();
    }

    private static class HttpNetThanCacheHolder {
        private static OkHttpClient getHttpClient() {
            Builder builder = new Builder();

            // 设置超时
            builder.connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS);

            // 添加拦截器；默认走网络，如果没有网，则走缓存
            builder.addInterceptor(new NetPriorInterceptor());

            return builder.build();
        }
    }
}
