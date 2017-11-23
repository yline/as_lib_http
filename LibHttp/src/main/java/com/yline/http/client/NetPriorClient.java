package com.yline.http.client;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 按照 网络优先的 规则
 * 无网络 -- 读取缓存 -- 无缓存 -- 返回失败
 *
 * @author yline 2017/2/28 -- 17:29
 * @version 1.0.0
 */
public class NetPriorClient {
    private NetPriorClient() {
    }

    public static OkHttpClient getInstance() {
        return NetPriorClientHolder.getOkHttpClient();
    }

    private static class NetPriorClientHolder {
        private static OkHttpClient okHttpClient;

        private static OkHttpClient getOkHttpClient() {
            if (null == okHttpClient) {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();

                // 设置参数
                builder.connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS);
                builder.addInterceptor(new NetPriorInterceptor());

                okHttpClient = builder.build();
            }
            return okHttpClient;
        }
    }
}
