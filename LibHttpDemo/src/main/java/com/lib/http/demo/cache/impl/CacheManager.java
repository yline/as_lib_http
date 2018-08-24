package com.lib.http.demo.cache.impl;

import com.yline.application.SDKManager;
import com.yline.utils.LogUtil;

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
	private static OkioCache okioCache;
	
	private static OkioCache getInstance() {
		if (null == okioCache) {
			synchronized (OkioCache.class) {
				if (null == okioCache) {
					okioCache = initOkioCache();
				}
			}
		}
		return okioCache;
	}
	
	private static OkioCache initOkioCache() {
		File dirFile = SDKManager.getApplication().getExternalFilesDir("LibHttp");
		long maxSize = 128 * 1024 * 1024;
		return new OkioCache(dirFile, maxSize);
	}
	
	public static String setHttpCache(Response response) throws IOException {
		return cacheAndReadResponse(response);
	}
	
	public static Response getHttpCache(Request request) {
		return getInstance().getResponse(request);
	}
	
	/**
	 * 读取Response，并复制成两份；一份输出，一份保存
	 *
	 * @param response 网络数据
	 * @return 读取的内容
	 * @throws IOException 读取数据异常
	 */
	private static String cacheAndReadResponse(Response response) throws IOException {
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
			boolean cacheStraightResult = getInstance().putResponse(response, cacheStream);
			LogUtil.v("cacheAndReadResponse result is " + cacheStraightResult);
			
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
}
