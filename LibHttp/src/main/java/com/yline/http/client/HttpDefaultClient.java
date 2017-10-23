package com.yline.http.client;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 按照 OKHttp的 既定规则
 *
 * @author yline 2017/7/22 -- 15:31
 * @version 1.0.0
 */
public class HttpDefaultClient {
    private HttpDefaultClient() {
    }

    public static OkHttpClient getInstance() {
        return HttpDefaultHolder.getHttpClient();
    }

    private static class HttpDefaultHolder {
        private static OkHttpClient getHttpClient() {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            // 设置超时
            builder.connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS);

            return builder.build();
        }
    }
}
