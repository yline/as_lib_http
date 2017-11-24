package com.yline.http.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yline.http.util.LogUtil;

import java.io.File;

/**
 * 本地日志管理类
 * 并加入初始化方法
 *
 * @author yline 2017/11/23 -- 20:29
 * @version 1.0.0
 */
public class LibManager {
    private static OkHttpConfig mOkHttpConfig;
    private static ConnectivityManager mConnectivityManager;

    public static void init(Context context, OkHttpConfig config) {
        LibManager.mOkHttpConfig = config;

        // 修改默认路径
        if (null == config) {
            File cacheDirFile = context.getExternalFilesDir("LibHttp");
            if (null != cacheDirFile) {
                getHttpConfig().setCacheDirPath(cacheDirFile.getAbsolutePath() + File.separator);
            }
        }
        LibManager.mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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
     * 拦截器日志使用
     */
    public static void vInt(String content) {
        if (LibManager.getHttpConfig().isInterceptorDebug()) {
            LogUtil.v(content, LogUtil.LOG_LOCATION_PARENT);
        }
    }

    /**
     * 缓存日志 使用
     */
    public static void vCache(String content) {
        if (LibManager.getHttpConfig().isCacheDebug()) {
            LogUtil.i(content, LogUtil.LOG_LOCATION_PARENT);
        }
    }

    public static void eCache(String content) {
        if (LibManager.getHttpConfig().isCacheDebug()) {
            LogUtil.e(content, LogUtil.LOG_LOCATION_PARENT);
        }
    }

    /**
     * 网络请求，入口出口使用
     */
    public static void vRequest(String content) {
        if (LibManager.getHttpConfig().isRequestDebug()) {
            LogUtil.v(content, LogUtil.LOG_LOCATION_PARENT);
        }
    }

    public static void eRequest(String content, Throwable ex) {
        if (LibManager.getHttpConfig().isRequestDebug()) {
            LogUtil.e(content, LogUtil.LOG_LOCATION_PARENT, ex);
        }
    }

    /**
     * 判断网络是否连接
     */
    public static boolean isNetConnected() {
        if (null != LibManager.getConnectivityManager()) {
            NetworkInfo networkInfo = LibManager.getConnectivityManager().getActiveNetworkInfo();
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
}
