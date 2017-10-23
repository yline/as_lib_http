package com.yline.http;

import com.google.gson.Gson;
import com.yline.http.cache.OkioCache;
import com.yline.http.client.HttpDefaultClient;
import com.yline.http.controller.RequestMethodCallback;
import com.yline.http.controller.ResponseHandlerCallback;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class XHttp implements RequestMethodCallback {
    @Override
    public <T> void doGet(String actionUrl, Map<String, Object> actionMap, final Class<T> clazz, XHttpAdapter<T> adapter) {
        OkHttpClient okHttpClient = getOkHttpClient();  // 配置Client

        // 配置请求参数
        Request.Builder requestBuilder = attachGetHead(actionUrl, actionMap);
        onRequestBuilder(requestBuilder);
        Request request = requestBuilder.build();

        // 发送请求
        final ResponseHandlerCallback iResponse = adapter.getRequestHandler();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                iResponse.handleFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                iResponse.handleSuccess(call, response, clazz);
            }
        });
    }

    @Override
    public <T> void doPost(String actionUrl, MultipartBody.Builder bodyBuilder, final Class<T> clazz, XHttpAdapter<T> adapter) {
        if (null == bodyBuilder) {
            doPost(actionUrl, "", clazz, adapter);
        } else {
            OkHttpClient okHttpClient = getOkHttpClient(); // 配置Client

            // 配置请求参数
            Request.Builder requestBuilder = attachMultiBody(actionUrl, bodyBuilder);
            onRequestBuilder(requestBuilder);
            Request request = requestBuilder.build();

            // 发送请求
            final ResponseHandlerCallback iResponse = adapter.getRequestHandler();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    iResponse.handleFailure(call, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    iResponse.handleSuccess(call, response, clazz);
                }
            });
        }
    }

    @Override
    public <T> void doPost(String actionUrl, Object jsonParam, final Class<T> clazz, XHttpAdapter<T> adapter) {
        OkHttpClient okHttpClient = getOkHttpClient(); // 配置Client

        // 配置请求参数, 并提供修改的 方法
        Request.Builder requestBuilder = attachJsonBody(actionUrl, jsonParam);
        onRequestBuilder(requestBuilder);
        Request request = requestBuilder.build();

        // 发送请求
        final ResponseHandlerCallback iResponse = adapter.getRequestHandler();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                iResponse.handleFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                iResponse.handleSuccess(call, response, clazz);
            }
        });
    }

    /**
     * 生成 Http的Url
     */
    private Request.Builder attachGetHead(String actionUrl, Map<String, Object> actionMap) {
        Request.Builder builder = new Request.Builder();

        // get 拼接
        String getHttpUrl = String.format("%s?%s", actionUrl, genGetParamUrl(actionMap));
        OkHttpConfig.d("get request url = " + getHttpUrl);

        builder.url(getHttpUrl);
        return builder;
    }

    /**
     * 将 Map 生成为 Get请求的 Url部分
     */
    private String genGetParamUrl(Map<String, Object> actionMap) {
        if (null == actionMap) {
            return "";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            boolean isFirst = true;
            for (String key : actionMap.keySet()) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    stringBuilder.append("&");
                }
                stringBuilder.append(key);
                stringBuilder.append("=");
                stringBuilder.append(actionMap.get(key));
            }
            return stringBuilder.toString();
        }
    }

    private Request.Builder attachMultiBody(String actionUrl, MultipartBody.Builder bodyBuilder) {
        Request.Builder builder = new Request.Builder();
        builder.url(actionUrl);

        // body
        bodyBuilder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = bodyBuilder.build();
        builder.post(multipartBody);

        OkHttpConfig.d("post request url = " + actionUrl + ", multipartBody size = " + multipartBody.size());
        return builder;
    }

    private Request.Builder attachJsonBody(String actionUrl, Object jsonParam) {
        OkHttpConfig.d("post request url = " + actionUrl);

        Request.Builder builder = new Request.Builder();
        builder.url(actionUrl);

        // 添加Json作为 结构体
        String jsonBody;
        if (null == jsonParam) {
            jsonBody = "";
        } else {
            jsonBody = new Gson().toJson(jsonParam);
        }
        OkHttpConfig.d("post request body = " + jsonBody);
        RequestBody requestBody = RequestBody.create(OkioCache.DEFAULT_MEDIA_TYPE, jsonBody);
        builder.post(requestBody);

        return builder;
    }

	/* &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& 重写的方法 &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& */

    /**
     * 添加 Request信息
     * 丢给子类 实现更多功能; 也可以覆盖父类的功能
     */
    protected void onRequestBuilder(Request.Builder builder) {
        // TODO
    }

    protected OkHttpClient getOkHttpClient() {
        return HttpDefaultClient.getInstance();
    }
}
