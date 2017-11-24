package com.yline.http;

import android.content.Context;

import com.yline.http.client.CachePriorClient;
import com.yline.http.client.DefaultClient;
import com.yline.http.client.NetPriorClient;
import com.yline.http.manager.LibManager;
import com.yline.http.manager.OkHttpConfig;
import com.yline.http.manager.XHttp;
import com.yline.http.manager.XHttpAdapter;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;

/**
 * OkHttp 统一管理类
 *
 * @author yline 2017/10/19 -- 14:25
 * @version 1.0.0
 */
public class OkHttpManager {
    public static void init(Context context, OkHttpConfig config) {
        LibManager.init(context, config);
    }

    /**
     * Get请求，提交url
     */
    public static <T> void doGet(String actionUrl, Map<String, Object> actionMap, Class<T> clazz, XHttpAdapter<T> adapter) {
        new XHttp() {
            @Override
            protected OkHttpClient getOkHttpClient() {
                return DefaultClient.getInstance();
            }
        }.doGet(actionUrl, actionMap, clazz, adapter);
    }

    public static <T> void doGetNetPrior(String actionUrl, Map<String, Object> actionMap, Class<T> clazz, XHttpAdapter<T> adapter) {
        new XHttp() {
            @Override
            protected OkHttpClient getOkHttpClient() {
                return NetPriorClient.getInstance();
            }
        }.doGet(actionUrl, actionMap, clazz, adapter);
    }

    public static <T> void doGetCachePrior(String actionUrl, Map<String, Object> actionMap, Class<T> clazz, XHttpAdapter<T> adapter) {
        new XHttp() {
            @Override
            protected OkHttpClient getOkHttpClient() {
                return CachePriorClient.getInstance();
            }
        }.doGet(actionUrl, actionMap, clazz, adapter);
    }

    /**
     * Post请求，提交Gson
     */
    public static <T> void doPost(String actionUrl, Object jsonParam, Class<T> clazz, XHttpAdapter<T> adapter) {
        new XHttp() {
            @Override
            protected OkHttpClient getOkHttpClient() {
                return DefaultClient.getInstance();
            }
        }.doPost(actionUrl, jsonParam, clazz, adapter);
    }

    public static <T> void doPostNetPrior(String actionUrl, Object jsonParam, Class<T> clazz, XHttpAdapter<T> adapter) {
        new XHttp() {
            @Override
            protected OkHttpClient getOkHttpClient() {
                return NetPriorClient.getInstance();
            }
        }.doPost(actionUrl, jsonParam, clazz, adapter);
    }

    public static <T> void doPostCachePrior(String actionUrl, Object jsonParam, Class<T> clazz, XHttpAdapter<T> adapter) {
        new XHttp() {
            @Override
            protected OkHttpClient getOkHttpClient() {
                return CachePriorClient.getInstance();
            }
        }.doPost(actionUrl, jsonParam, clazz, adapter);
    }

    /**
     * Post请求，提交表单
     */
    public static <T> void doPost(String actionUrl, MultipartBody.Builder bodyBuilder, Class<T> clazz, XHttpAdapter<T> adapter) {
        new XHttp() {
            @Override
            protected OkHttpClient getOkHttpClient() {
                return DefaultClient.getInstance();
            }
        }.doPost(actionUrl, bodyBuilder, clazz, adapter);
    }

    public static <T> void doPostNetPrior(String actionUrl, MultipartBody.Builder bodyBuilder, Class<T> clazz, XHttpAdapter<T> adapter) {
        new XHttp() {
            @Override
            protected OkHttpClient getOkHttpClient() {
                return NetPriorClient.getInstance();
            }
        }.doPost(actionUrl, bodyBuilder, clazz, adapter);
    }

    public static <T> void doPostCachePrior(String actionUrl, MultipartBody.Builder bodyBuilder, Class<T> clazz, XHttpAdapter<T> adapter) {
        new XHttp() {
            @Override
            protected OkHttpClient getOkHttpClient() {
                return CachePriorClient.getInstance();
            }
        }.doPost(actionUrl, bodyBuilder, clazz, adapter);
    }
}
