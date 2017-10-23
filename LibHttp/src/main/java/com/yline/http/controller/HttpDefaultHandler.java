package com.yline.http.controller;

import android.os.Handler;

/**
 * 提供,统一处理Http的handler
 *
 * @author yline 2017/2/23 -- 10:26
 * @version 1.0.0
 */
public class HttpDefaultHandler extends Handler {
    private HttpDefaultHandler() {
    }

    public static HttpDefaultHandler build() {
        return HttpHandlerHold.getInstance();
    }

    private static class HttpHandlerHold {
        private static HttpDefaultHandler sInstance;

        private static HttpDefaultHandler getInstance() {
            if (null == sInstance) {
                sInstance = new HttpDefaultHandler();
            }
            return sInstance;
        }
    }
}
