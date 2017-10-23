package com.yline.http.client;

import com.yline.http.interceptor.CachePriorInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 缓存优先的策略
 * 有缓存 -- 读取缓存
 * 无缓存 -- 请求网络
 *
 * @author yline 2017/7/22 -- 15:31
 * @version 1.0.0
 */
public class HttpCachePriorClient {
    private HttpCachePriorClient() {
    }

    public static OkHttpClient getInstance() {
        return HttpCacheAndNetHolder.getHttpClient();
    }

    private static class HttpCacheAndNetHolder {
        private static OkHttpClient getHttpClient() {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            // 设置超时
            builder.connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS);

            // 添加拦截器；缓存优先
            builder.addInterceptor(new CachePriorInterceptor());

            return builder.build();
        }
    }
}
