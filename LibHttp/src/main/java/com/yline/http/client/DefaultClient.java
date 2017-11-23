package com.yline.http.client;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 按照 OKHttp的 既定规则
 *
 * @author yline 2017/7/22 -- 15:31
 * @version 1.0.0
 */
public class DefaultClient {
    private DefaultClient() {
    }

    public static OkHttpClient getInstance() {
        return DefaultClientHolder.getOkHttpClient();
    }

    private static class DefaultClientHolder {
        private static OkHttpClient okHttpClient;

        private static OkHttpClient getOkHttpClient() {
            if (null == okHttpClient) {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();

                // 设置参数
                builder.connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS);

                okHttpClient = builder.build();
            }
            return okHttpClient;
        }
    }
}
