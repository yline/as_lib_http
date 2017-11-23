package com.yline.http.client;

import com.yline.http.manager.CacheManager;
import com.yline.http.manager.LibManager;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;

/**
 * 注意：若无网络，则chain.proceed(request)之后就不会执行
 *
 * 按照 网络优先的 规则
 * 有网络 -- 读取网络
 * 无网络 -- 读取缓存 -- 无缓存 -- 返回失败
 *
 * @author yline 2017/7/22 -- 15:51
 * @version 1.0.0
 */
public class NetPriorInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        LibManager.vInt(String.format("NetPriorInterceptor request %s on %s%n%s", request.url(), chain.connection(), request.headers()));

        long time = System.currentTimeMillis();
        boolean isNetWorkable = LibManager.isNetConnected();

        LibManager.vInt("isNetWorkable = " + isNetWorkable);
        if (isNetWorkable) {
            Response response = chain.proceed(request);
            LibManager.vInt(String.format(Locale.CHINA, "NetPriorInterceptor response %s in %dms%n%s", response.request().url(), (System.currentTimeMillis() - time), response.headers()));
            return response;
        } else {
            Response cacheResponse = CacheManager.getCache(request);
            LibManager.vInt("cacheResponse = " + cacheResponse);
            if (null == cacheResponse) {
                return new Response.Builder().request(request)
                        .protocol(Protocol.HTTP_1_1).code(504).message("Unsatisfiable Request (cache is null)").body(Util.EMPTY_RESPONSE)
                        .sentRequestAtMillis(-1L).receivedResponseAtMillis(System.currentTimeMillis())
                        .build();
            } else {
                LibManager.vInt(String.format(Locale.CHINA, "NetPriorInterceptor cacheResponse %s in %dms%n%s", cacheResponse.request().url(), (System.currentTimeMillis() - time), cacheResponse.headers()));
                return cacheResponse;
            }
        }
    }
}
