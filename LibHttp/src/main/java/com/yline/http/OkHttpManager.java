package com.yline.http;

import android.content.Context;
import android.net.ConnectivityManager;

import java.io.File;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * OkHttp 统一管理类
 *
 * @author yline 2017/10/19 -- 14:25
 * @version 1.0.0
 */
public class OkHttpManager {
    private static OkHttpConfig mOkHttpConfig;
    private static ConnectivityManager mConnectivityManager;

    public static void init(Context context, OkHttpConfig config) {
        OkHttpManager.mOkHttpConfig = config;

        // 修改默认路径
        if (null == config) {
            File cacheDirFile = context.getExternalFilesDir("LibHttp");
            if (null != cacheDirFile) {
                getHttpConfig().setCacheDirPath(cacheDirFile.getAbsolutePath() + File.separator);
            }
        }
        OkHttpManager.mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static OkHttpConfig getHttpConfig() {
        if (null == mOkHttpConfig) {
            mOkHttpConfig = new OkHttpConfig();
        }
        return mOkHttpConfig;
    }

    public static ConnectivityManager getConnectivityManager() {
        return mConnectivityManager;
    }

    /**
     * 默认的Get请求方式
     * XHttp 可重写
     * 1，onRequestBuilder
     * 2，getOkHttpClient {HttpCachePriorClient}、{HttpDefaultClient}、{HttpNetPriorClient}
     * XHttpAdapter 可重写处理类 HttpDefaultResponse
     */
    public static <T> void doGet(String actionUrl, Map<String, Object> actionMap, Class<T> clazz, XHttpAdapter<T> adapter) {
        new XHttp().doGet(actionUrl, actionMap, clazz, adapter);
    }

    /**
     * 默认的 Post Json 请求方式
     * XHttp 可重写
     * 1，onRequestBuilder
     * 2，getOkHttpClient {HttpCachePriorClient}、{HttpDefaultClient}、{HttpNetPriorClient}
     * XHttpAdapter 可重写处理类 HttpDefaultResponse
     */
    public static <T> void doPost(String actionUrl, Object jsonParam, Class<T> clazz, XHttpAdapter<T> adapter) {
        new XHttp().doPost(actionUrl, jsonParam, clazz, adapter);
    }

    /**
     * 默认的 Post 表单 请求方式
     * XHttp 可重写
     * 1，onRequestBuilder
     * 2，getOkHttpClient {HttpCachePriorClient}、{HttpDefaultClient}、{HttpNetPriorClient}
     * XHttpAdapter 可重写处理类 HttpDefaultResponse
     */
    public static <T> void doPost(String actionUrl, MultipartBody.Builder bodyBuilder, Class<T> clazz, XHttpAdapter<T> adapter) {
        new XHttp().doPost(actionUrl, bodyBuilder, clazz, adapter);
    }
}
