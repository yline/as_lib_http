package com.yline.http.manager;

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
