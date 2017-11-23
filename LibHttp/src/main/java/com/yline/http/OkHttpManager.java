package com.yline.http;

import android.content.Context;

import com.yline.http.manager.LibManager;
import com.yline.http.manager.OkHttpConfig;
import com.yline.http.manager.XHttp;
import com.yline.http.manager.XHttpAdapter;

import java.util.Map;

import okhttp3.MultipartBody;

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

    public static <T> void doGet(String actionUrl, Map<String, Object> actionMap, Class<T> clazz, XHttpAdapter<T> adapter) {
        new XHttp().doGet(actionUrl, actionMap, clazz, adapter);
    }

    public static <T> void doPost(String actionUrl, Object jsonParam, Class<T> clazz, XHttpAdapter<T> adapter) {
        new XHttp().doPost(actionUrl, jsonParam, clazz, adapter);
    }

    public static <T> void doPost(String actionUrl, MultipartBody.Builder bodyBuilder, Class<T> clazz, XHttpAdapter<T> adapter) {
        new XHttp().doPost(actionUrl, bodyBuilder, clazz, adapter);
    }
}
