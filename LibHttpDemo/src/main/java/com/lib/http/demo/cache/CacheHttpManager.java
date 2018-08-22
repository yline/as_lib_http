package com.lib.http.demo.cache;

import android.support.v4.util.ArrayMap;

import com.lib.http.demo.cache.adapter.CacheGetHttpAdapter;
import com.lib.http.demo.cache.adapter.CachePostHttpAdapter;
import com.lib.http.demo.cache.model.NewsMultiplexModel;
import com.lib.http.demo.cache.model.NewsSingleModel;
import com.yline.http.callback.OnJsonCallback;
import com.yline.http.OkHttpUtils;

import java.util.Map;

/**
 * 测试 缓存
 *
 * @author yline
 * @times 2018/8/22 -- 14:02
 */
public class CacheHttpManager {
	private static final String IP = "192.168.2.184";
	
	public static void doGetDefault(OnJsonCallback<NewsSingleModel> callback) {
		String httpUrl = "http://" + IP + "/android/git_api/libhttp/puppet_list.txt";
		
		get(httpUrl, null, callback);
	}
	
	public static void doPostDefault(int startNum, int length, OnJsonCallback<NewsMultiplexModel> callback) {
		String httpUrl = "http://" + IP + "/android/git_api/libhttp/puppet_list.txt";
		
		ArrayMap<String, String> reqMap = new ArrayMap<>();
		reqMap.put("num1", String.valueOf(startNum));
		reqMap.put("length", String.valueOf(length));
		
		post(httpUrl, reqMap, callback);
	}
	
	public static void doGetNetPrior(OnJsonCallback<NewsSingleModel> callback) {
		String httpUrl = "http://" + IP + "/android/git_api/libhttp/puppet_list.txt";
		
		getNetPrior(httpUrl, null, callback);
	}
	
	public static void doPostNetPrior(int startNum, int length, OnJsonCallback<NewsMultiplexModel> callback) {
		String httpUrl = "http://" + IP + "/android/git_api/libhttp/puppet_list.txt";
		
		ArrayMap<String, String> reqMap = new ArrayMap<>();
		reqMap.put("num1", String.valueOf(startNum));
		reqMap.put("length", String.valueOf(length));
		
		postNetPrior(httpUrl, reqMap, callback);
	}
	
	/* ---------------------             基础工具            --------------------- */
	private static <T> void get(String httpUrl, Map<String, String> param, OnJsonCallback<T> callback) {
		OkHttpUtils.get(httpUrl, param, "Cache-get", callback);
	}
	
	private static <T> void getNetPrior(String httpUrl, Map<String, String> param, OnJsonCallback<T> callback) {
		OkHttpUtils.request(new CacheGetHttpAdapter(httpUrl, param, "Cache-getNetPrior"), callback);
	}
	
	private static <T> void post(String httpUrl, Map<String, String> param, OnJsonCallback<T> callback) {
		OkHttpUtils.postJson(httpUrl, param, "Cache-post", callback);
	}
	
	private static <T> void postNetPrior(String httpUrl, Map<String, String> param, OnJsonCallback<T> callback) {
		OkHttpUtils.request(new CachePostHttpAdapter(httpUrl, param, "Cache-postNetPrior"), callback);
	}
}
