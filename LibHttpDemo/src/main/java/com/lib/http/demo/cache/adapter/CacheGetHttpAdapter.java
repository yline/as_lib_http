package com.lib.http.demo.cache.adapter;

import com.lib.http.demo.cache.client.OkHttpClientManager;
import com.lib.http.demo.cache.impl.CacheManager;
import com.yline.http.adapter.mode.GetHttpAdapter;
import com.yline.http.callback.OnParseCallback;
import com.yline.http.json.FastJson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class CacheGetHttpAdapter extends GetHttpAdapter {
	public CacheGetHttpAdapter(String httpUrl, Map<String, String> param, Object tag) {
		super(httpUrl, param, tag);
	}
	
	@Override
	public OkHttpClient getHttpClient() {
		return OkHttpClientManager.getNetPriorHttpClient();
	}
	
	@Override
	public <T> void handleResponse(Response response, Type type, OnParseCallback<T> callback) throws IOException {
		String bodyString = CacheManager.setHttpCache(response);
		
		T data = FastJson.toClass(bodyString, type);
		callback.onResponseSuccess(data);
	}
}
