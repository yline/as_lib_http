package com.yline.http.client;

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
public class CachePriorClient {
    private CachePriorClient() {
    }

    public static OkHttpClient getInstance() {
        return CachePriorClientHolder.getOkHttpClient();
    }

    private static class CachePriorClientHolder {
        private static OkHttpClient okHttpClient;

        private static OkHttpClient getOkHttpClient() {
            if (null == okHttpClient) {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();

                // 设置参数
                builder.connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS);
                builder.addInterceptor(new CachePriorInterceptor());

                okHttpClient = builder.build();
            }
            return okHttpClient;
        }
    }
}
