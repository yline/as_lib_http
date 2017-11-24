package com.yline.http.manager;

import com.yline.http.cache.OkioCache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Http缓存 帮助类
 *
 * @author yline 2017/10/19 -- 14:20
 * @version 1.0.0
 */
public class CacheManager {
    private OkioCache mOkioCache;

    private CacheManager() {
        File dirFile = new File(LibManager.getHttpConfig().getCacheDirPath());
        long maxSize = LibManager.getHttpConfig().getCacheMaxSize();
        mOkioCache = new OkioCache(dirFile, maxSize);
    }

    public static String setCache(Response response) throws IOException {
        return getInstance().cacheAndReadResponse(response);
    }

    public static Response getCache(Request request) {
        return getInstance().getCacheStraight(request);
    }

    private static CacheManager getInstance() {
        return CacheManagerHolder.getInstance();
    }

    private Response getCacheStraight(Request request) {
        if (null == mOkioCache || null == request) {
            LibManager.eCache("mOkioCache = " + mOkioCache + ", request = " + request);
            return null;
        }
        return mOkioCache.getResponse(request);
    }

    /**
     * 读取Response，并复制成两份；一份输出，一份保存
     *
     * @param response 网络数据
     * @return 读取的内容
     * @throws IOException 读取数据异常
     */
    private String cacheAndReadResponse(Response response) throws IOException {
        ByteArrayOutputStream baoStream = null;
        InputStream cacheStream = null;
        InputStream returnStream = null;
        try {
            InputStream responseStream = response.body().byteStream();

            baoStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int len;
            while ((len = responseStream.read(buffer)) > -1) {
                baoStream.write(buffer, 0, len);
            }
            baoStream.flush();

            // 缓存一份Stream
            cacheStream = new ByteArrayInputStream(baoStream.toByteArray());
            boolean cacheStraightResult = setCacheStraight(response, cacheStream);
            LibManager.vCache("cacheAndReadResponse result is " + cacheStraightResult);

            // 读取一份Stream
            returnStream = new ByteArrayInputStream(baoStream.toByteArray());
            StringBuilder stringBuilder = new StringBuilder();
            byte[] returnBuffer = new byte[1024];
            int returnLen;
            while ((returnLen = returnStream.read(returnBuffer)) > -1) {
                stringBuilder.append(new String(returnBuffer, 0, returnLen));
            }
            return stringBuilder.toString();
        } finally {
            if (null != baoStream) {
                baoStream.close();
            }

            if (null != cacheStream) {
                cacheStream.close();
            }

            if (null != returnStream) {
                returnStream.close();
            }
        }
    }

    private boolean setCacheStraight(Response response, InputStream inputStream) {
        if (null == mOkioCache || null == response || null == inputStream) {
            LibManager.eCache("mOkioCache = " + mOkioCache + ", response = " + response + ", inputStream = " + inputStream);
            return false;
        }
        return mOkioCache.putResponse(response, inputStream);
    }

    private static class CacheManagerHolder {
        private static CacheManager sInstance;

        private static CacheManager getInstance() {
            if (null == sInstance) {
                sInstance = new CacheManager();
            }
            return sInstance;
        }
    }
}
