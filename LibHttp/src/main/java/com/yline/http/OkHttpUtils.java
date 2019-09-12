package com.yline.http;

import android.os.Handler;
import android.os.Looper;

import com.yline.http.adapter.OnHttpAdapter;
import com.yline.http.adapter.mode.GetHttpAdapter;
import com.yline.http.adapter.mode.PostJsonHttpAdapter;
import com.yline.http.adapter.mode.PostMultiHttpAdapter;
import com.yline.http.callback.OnJsonCallback;
import com.yline.http.callback.OnParseCallback;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OKHttp封装一层，handler处理一层。具体的实现都在Adapter里面
 *
 * @author yline 2018/8/21 -- 18:13
 */
public class OkHttpUtils {
    public static final String IO_ERROR_CODE = "-100"; // IO 异常

    public static final String HANDLE_ERROR_CODE = "-200"; // 字符串处理出问题（Json解析）

    private static boolean isDebug;

    private static Handler handler;

    private static Handler getHandler() {
        if (null == handler) {
            synchronized (Handler.class) {
                if (null == handler) {
                    handler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return handler;
    }

    public static void setIsDebug(boolean isDebug) {
        OkHttpUtils.isDebug = isDebug;
    }

    /**
     * get方式请求
     */
    public static <T> void get(String httpUrl, Map<String, String> paramMap, Object tag, final OnJsonCallback<T> callback) {
        requestAsync(new GetHttpAdapter(httpUrl, paramMap, tag), callback);
    }

    /**
     * post 表单方式提交
     */
    public static <T> void postMulti(String httpUrl, Map<String, String> paramMap, Object tag, final OnJsonCallback<T> callback) {
        requestAsync(new PostMultiHttpAdapter(httpUrl, paramMap, tag), callback);
    }

    /**
     * post json字符串方式提交
     */
    public static <T> void postJson(String httpUrl, Map<String, String> paramMap, Object tag, final OnJsonCallback<T> callback) {
        requestAsync(new PostJsonHttpAdapter(httpUrl, paramMap, tag), callback);
    }

    /**
     * 自定义请求方式
     */
    public static <T> void request(final OnHttpAdapter httpAdapter, final OnJsonCallback<T> callback) {
        requestAsync(httpAdapter, callback);
    }

    private static <T> void requestAsync(final OnHttpAdapter httpAdapter, final OnJsonCallback<T> callback) {
        print("requestAsync start");

        OkHttpClient okHttpClient = httpAdapter.getHttpClient();
        final Request request = httpAdapter.getHttpRequest();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                print("requestAsync onFailure");
                failure(callback, httpAdapter, e, IO_ERROR_CODE, null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Type dataType = callback.getSuperclassType();
                httpAdapter.handleResponse(response, dataType, new OnParseCallback<T>() {
                    @Override
                    public void onResponseSuccess(T t) {
                        print("requestAsync onResponseSuccess");
                        response(callback, t);
                    }

                    @Override
                    public void onResponseError(Exception ex, String code, String msg) {
                        print("requestAsync onResponseError code = " + code + ", msg = " + msg);
                        failure(callback, httpAdapter, ex, code, msg);
                    }
                });
            }
        });
    }

    /**
     * 打印错误信息
     *
     * @param msg 信息内容
     */
    private static void print(String msg) {
        if (isDebug) {
            android.util.Log.v("xxx-OkHttpUtils", msg);
        }
    }

    /**
     * 处理 Http失败情况
     *
     * @param callback 回调
     * @param ex       异常
     * @param code     错误码 -100{IO异常}，-200{Json解析异常}，其它{服务器返回异常}
     * @param msg      错误信息
     * @param <T>      返回的泛型结构
     */
    private static <T> void failure(final OnJsonCallback<T> callback, final OnHttpAdapter httpAdapter, final Exception ex, final String code, final String msg) {
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                String newMessage = httpAdapter.handleFailureException(ex, code, msg);
                if (null != callback) {
                    callback.onFailure(code, newMessage);
                }
            }
        });
    }

    /**
     * 处理 Http成功的情况
     *
     * @param callback 回调
     * @param <T>      返回的泛型结构
     */
    private static <T> void response(final OnJsonCallback<T> callback, final T t) {
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (null != callback) {
                    callback.onResponse(t);
                }
            }
        });
    }
}
