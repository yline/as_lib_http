package com.yline.http.client;

import com.yline.http.manager.CacheManager;
import com.yline.http.manager.LibManager;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 注意：若无网络，则chain.proceed(request)之后就不会执行
 *
 * 缓存优先的策略
 * 有缓存 -- 读取缓存
 * 无缓存 -- 请求网络
 *
 * @author yline 2017/7/22 -- 15:41
 * @version 1.0.0
 */
public class CachePriorInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        LibManager.vInt(String.format("CachePriorInterceptor request %s on %s%n%s", request.url(), chain.connection(), request.headers()));

        long time = System.currentTimeMillis();
        Response cacheResponse = CacheManager.getCache(request);
        if (null == cacheResponse) {
            LibManager.vInt("CachePriorInterceptor cacheResponse is null");
            Response response = chain.proceed(request);
            LibManager.vInt(String.format(Locale.CHINA, "CachePriorInterceptor response %s in %dms%n%s", response.request().url(), (System.currentTimeMillis() - time), response.headers()));
            return response;
        } else {
            LibManager.vInt(String.format(Locale.CHINA, "CachePriorInterceptor cacheResponse %s in %dms%n%s", cacheResponse.request().url(), (System.currentTimeMillis() - time), cacheResponse.headers()));
            return cacheResponse;
        }
    }
}
