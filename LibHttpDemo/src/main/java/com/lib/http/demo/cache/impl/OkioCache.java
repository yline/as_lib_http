package com.lib.http.demo.cache.impl;

import android.text.TextUtils;

import com.yline.utils.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.io.FileSystem;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;

/**
 * 缓存 实现类
 *
 * @author yline 2017/11/23 -- 19:57
 * @version 1.0.0
 */
class OkioCache {
	public static final MediaType DEFAULT_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
	public static final int VERSION = 201105;
	
	public static final int ENTRY_METADATA = 0; // 编号0
	public static final int ENTRY_BODY = 1; // 编号1
	public static final int ENTRY_COUNT = 2; // 总数
	
	private final DiskLruCache diskLruCache;
	
	public OkioCache(File dir, long maxSize) {
		this(dir, maxSize, FileSystem.SYSTEM);
	}
	
	public OkioCache(File dir, long maxSize, FileSystem fileSystem) {
		this.diskLruCache = DiskLruCache.create(fileSystem, dir, VERSION, ENTRY_COUNT, maxSize);
	}
	
	/**
	 * return null, if error happened
	 */
	public Response getResponse(Request request) {
		DiskLruCache.Snapshot snapshot = null;
		CacheEntry cacheEntry = null;
		try {
			String key = key(request);
			if (TextUtils.isEmpty(key)) {
				return null;
			}
			
			snapshot = diskLruCache.get(key);
			if (snapshot == null) {
				return null;
			}
		} catch (IOException e) {
			// Give up because the cache cannot be read.
			e.printStackTrace();
			return null;
		}
		
		try {
			cacheEntry = new CacheEntry(snapshot.getSource(ENTRY_METADATA));
		} catch (IOException e) {
			Util.closeQuietly(snapshot);
			return null;
		}
		
		Response response = cacheEntry.response(request.body(), snapshot);
		
		boolean isMatches = cacheEntry.matches(request, response);
		if (!isMatches) {
			Util.closeQuietly(response.body());
			return null;
		}
		
		return response;
	}
	
	public boolean putResponse(Response response, InputStream inputStream) {
		// 过滤头部，不符合vary的字符“*”
		if (HttpHeaders.hasVaryAll(response)) {
			LogUtil.e("OkioCache hasVaryAll is false");
			return false;
		}
		
		// 过滤 请求方式，仅限get + postJson(json)
		String key = null;
		try {
			key = key(response.request());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		// 校验一次
		if (TextUtils.isEmpty(key)) {
			return false;
		}
		
		DiskLruCache.Editor editor = null;
		try {
			editor = diskLruCache.edit(key);
			CacheEntry textCacheEntry = new CacheEntry(response);
			if (null == editor) {
				return false;
			}
			textCacheEntry.writeTo(editor, inputStream);
			
			return true;
		} catch (IOException e) {
			abortQuietly(editor);
			LogUtil.e("textCache put IOException", e);
			return false;
		}
	}
	
	private void abortQuietly(DiskLruCache.Editor editor) {
		// Give up because the cache cannot be written.
		try {
			if (editor != null) {
				editor.abort();
			}
		} catch (IOException ignored) {
		}
	}
	
	/**
	 * 过滤请求方式 -- 请求规则
	 */
	private String key(Request request) throws IOException {
		String key = null;
		String method = request.method();
		if ("GET".equals(method)) {
			key = ByteString.encodeUtf8(request.url().toString()).md5().hex();
			LogUtil.v("method = GET, key = " + key + ", url = " + request.url().toString() + ", result = null");
			return key;
		} else if ("POST".equals(method) && (DEFAULT_MEDIA_TYPE.equals(request.body().contentType()))) {
			RequestBody requestBody = request.body();
			BufferedSink sink = new Buffer();
			requestBody.writeTo(sink);
			InputStream inputStream = sink.buffer().inputStream();
			String result = streamToString(inputStream);
			
			key = ByteString.encodeUtf8(request.url().toString() + result).md5().hex();
			LogUtil.v("method = POST, key = " + key + ", url = " + request.url().toString() + ", result = " + result);
			return key;
		} else {
			LogUtil.e(String.format("Request{method=%s, url=%s, tag=%s}, contentType=%s", method, request.url().toString(), request.tag(), request.body().contentType()));
		}
		return null;
	}
	
	private String streamToString(InputStream inputStream) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		int len;
		byte[] buffer = new byte[4096];
		while ((len = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, len);
		}
		
		outputStream.close();
		return new String(outputStream.toByteArray());
	}
}
