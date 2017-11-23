package com.yline.http.controller;

import com.yline.http.manager.XHttpAdapter;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * 提供出来的，请求方法
 *
 * @author yline 2017/10/23 -- 19:35
 * @version 1.0.0
 */
public interface RequestMethodCallback {
    /**
     * Get 方式请求
     *
     * @param actionUrl 基础Url
     * @param actionMap 填充参数
     * @param clazz     返回 数据对象.class
     * @param adapter   返回 回调
     * @param <T>       返回 数据类型
     */
    <T> void doGet(String actionUrl, Map<String, Object> actionMap, Class<T> clazz, XHttpAdapter<T> adapter);

    /**
     * Post 方式请求
     * 携带 Json作为 body 请求
     *
     * @param actionUrl 基础Url
     * @param jsonParam 填充参数；json作为 body请求
     * @param clazz     返回 数据对象.class
     * @param adapter   返回 回调
     * @param <T>       返回数据类型
     */
    <T> void doPost(String actionUrl, Object jsonParam, Class<T> clazz, XHttpAdapter<T> adapter);

    /**
     * Post 方式 请求
     * 携带 表单作为 body 请求
     *
     * @param actionUrl   基础Url
     * @param bodyBuilder 填充参数；表单作为 body请求
     * @param clazz       返回 数据对象.class
     * @param adapter     返回 回调
     * @param <T>         返回数据类型
     */
    <T> void doPost(String actionUrl, MultipartBody.Builder bodyBuilder, Class<T> clazz, XHttpAdapter<T> adapter);
}
