package com.yline.http.adapter.mode;

import com.yline.http.adapter.OnHttpAdapter;
import com.yline.http.adapter.helper.ClientHelper;
import com.yline.http.adapter.helper.FailureHelper;
import com.yline.http.callback.OnParseCallback;
import com.yline.http.json.FastJson;
import com.yline.http.OkHttpUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Post请求方式，提交表单
 *
 * @author yline 2018/8/22 -- 13:07
 */
public class PostMultiHttpAdapter implements OnHttpAdapter {
	private String httpUrl;
	private Map<String, String> paramMap;
	private Object tag;
	
	public PostMultiHttpAdapter(String httpUrl, Map<String, String> param, Object tag) {
		this.httpUrl = httpUrl;
		this.paramMap = param;
		this.tag = tag;
	}
	
	@Override
	public OkHttpClient getHttpClient() {
		return ClientHelper.getDefaultHttpClient();
	}
	
	@Override
	public Request getHttpRequest() {
		return buildPostRequest(httpUrl, paramMap, tag);
	}
	
	@Override
	public <T> void handleResponse(Response response, Type type, OnParseCallback<T> callback) throws IOException {
		if (response.code() == 200) {
			ResponseBody responseBody = response.body();
			if (null != responseBody) {
				try {
					String bodyString = responseBody.string();
					T data = FastJson.toClass(bodyString, type);
					callback.onResponseSuccess(data);
				} catch (IOException ex) {
					callback.onResponseError(ex, OkHttpUtils.HANDLE_ERROR_CODE, null);
				}
			} else {
				callback.onResponseError(new IOException("response body is null"), OkHttpUtils.HANDLE_ERROR_CODE, null);
			}
		} else {
			callback.onResponseError(new IOException("response code error"), String.valueOf(response.code()), null);
		}
	}
	
	@Override
	public String handleFailureException(Exception ex, String code, String msg) {
		return FailureHelper.failureException(ex, code, msg);
	}
	
	/**
	 * 构建 Post请求的Request；
	 * 使用body{json}方式传播
	 *
	 * @param httpUrl 请求链接
	 * @param params  请求参数
	 * @param tag     返回的tag
	 * @return Request对象
	 */
	private static Request buildPostRequest(String httpUrl, Map<String, String> params, Object tag) {
		Request.Builder requestBuilder = new Request.Builder();
		requestBuilder.url(httpUrl);
		
		// 添加Http请求，协议的头部
		requestBuilder.addHeader("Content-Type", "text/json; charset=UTF-8");
		
		// body信息
		MultipartBody.Builder multiBuilder = new MultipartBody.Builder();
		multiBuilder.setType(MultipartBody.FORM);
		if (null != params) {
			for (String key : params.keySet()) {
				multiBuilder.addFormDataPart(key, params.get(key));
			}
		}
		requestBuilder.post(multiBuilder.build());
		
		// tag
		requestBuilder.tag(tag);
		
		// 返回结果
		return requestBuilder.build();
	}
}
