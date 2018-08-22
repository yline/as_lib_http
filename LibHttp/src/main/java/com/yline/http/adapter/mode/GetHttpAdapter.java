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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GetHttpAdapter implements OnHttpAdapter {
	private String httpUrl;
	private Map<String, String> paramMap;
	private Object tag;
	
	public GetHttpAdapter(String httpUrl, Map<String, String> param, Object tag) {
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
		return buildGetRequest(httpUrl, paramMap, tag);
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
			callback.onResponseError(new IOException("response code error"), response.code(), null);
		}
	}
	
	@Override
	public String handleFailureException(Exception ex, int code, String msg) {
		return FailureHelper.failureException(ex, code, msg);
	}
	
	private static Request buildGetRequest(String httpUrl, Map<String, String> paramMap, Object tag) {
		Request.Builder requestBuilder = new Request.Builder();
		
		// 添加请求链接
		String finalHttpUrl = String.format("%s?%s", httpUrl, genParamUrl(paramMap));
		requestBuilder.url(finalHttpUrl);
		
		requestBuilder.tag(tag);
		
		return requestBuilder.build();
	}
	
	/**
	 * 将请求参数，拼接成字符串
	 *
	 * @param param 请求参数
	 * @return 拼凑的字符串
	 */
	private static String genParamUrl(Map<String, String> param) {
		if (null == param) {
			return "";
		} else {
			StringBuilder stringBuilder = new StringBuilder();
			boolean isFirst = true;
			for (String key : param.keySet()) {
				if (isFirst) {
					isFirst = false;
				} else {
					stringBuilder.append("&");
				}
				stringBuilder.append(key);
				stringBuilder.append("=");
				stringBuilder.append(param.get(key));
			}
			return stringBuilder.toString();
		}
	}
}
