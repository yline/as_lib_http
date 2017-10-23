package com.yline.http;

import android.net.NetworkInfo;
import android.os.Environment;

import com.yline.http.util.LogUtil;

import java.io.File;

/**
 * OkHttpManager 开关
 *
 * @author yline 2017/10/19 -- 14:31
 * @version 1.0.0
 */
public class OkHttpConfig {
    private boolean isInterceptorDebug; // 拦截器是否 debug
    private boolean isCacheDebug; // 缓存是否 debug
    private boolean isRequestDebug; // 网络请求，debug
    private long cacheMaxSize; // 缓存最大大小
    private String cacheDirPath; // 缓存路径

    public OkHttpConfig() {
        isInterceptorDebug = true;
        isCacheDebug = true;
        isRequestDebug = true;
        cacheMaxSize = 128 * 1024 * 1024;
        cacheDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;

        LogUtil.setUtilLog(true);
        LogUtil.setUtilLogLocation(true);
    }

    /**
     * 拦截器日志使用
     */
    public static void v(String content) {
        if (OkHttpManager.getHttpConfig().isInterceptorDebug()) {
            LogUtil.v(content, LogUtil.LOG_LOCATION_PARENT);
        }
    }

    /**
     * 缓存日志 使用
     */
    public static void i(String content) {
        if (OkHttpManager.getHttpConfig().isCacheDebug()) {
            LogUtil.i(content, LogUtil.LOG_LOCATION_PARENT);
        }
    }

    /**
     * 网络请求，入口出口使用
     */
    public static void d(String content) {
        if (OkHttpManager.getHttpConfig().isRequestDebug()) {
            LogUtil.v(content, LogUtil.LOG_LOCATION_PARENT);
        }
    }

    /**
     * 判断网络是否连接
     */
    public static boolean isNetConnected() {
        if (null != OkHttpManager.getConnectivityManager()) {
            NetworkInfo networkInfo = OkHttpManager.getConnectivityManager().getActiveNetworkInfo();
            if (null != networkInfo) {
                return (networkInfo.getType() != -1);
            } else {
                return false;
            }
        } else {
            LogUtil.e("isNetConnected mConnectivityManager is null");
            return false;
        }
    }

    public boolean isRequestDebug() {
        return isRequestDebug;
    }

    public void setRequestDebug(boolean requestDebug) {
        isRequestDebug = requestDebug;
    }

    public boolean isCacheDebug() {
        return isCacheDebug;
    }

    public void setCacheDebug(boolean cacheDebug) {
        isCacheDebug = cacheDebug;
    }

    public boolean isInterceptorDebug() {
        return isInterceptorDebug;
    }

    public void setInterceptorDebug(boolean interceptorDebug) {
        isInterceptorDebug = interceptorDebug;
    }

    public long getCacheMaxSize() {
        return cacheMaxSize;
    }

    public void setCacheMaxSize(long cacheMaxSize) {
        this.cacheMaxSize = cacheMaxSize;
    }

    public String getCacheDirPath() {
        return cacheDirPath;
    }

    public void setCacheDirPath(String cacheDirPath) {
        this.cacheDirPath = cacheDirPath;
    }
}
