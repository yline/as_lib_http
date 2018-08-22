package com.lib.http.demo.normal.adapter;

import android.accounts.NetworkErrorException;
import android.os.Build;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lib.http.demo.normal.model.PostBaseModel;
import com.yline.http.adapter.OnHttpAdapter;
import com.yline.http.adapter.helper.ClientHelper;
import com.yline.http.callback.OnParseCallback;
import com.yline.http.json.FastJson;
import com.yline.http.OkHttpUtils;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.SSLException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 默认的Post请求方式，提交Json
 *
 * @author yline
 * @times 2018/8/22 -- 10:01
 */
public class KjtHttpAdapter implements OnHttpAdapter {
	private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=UTF-8");
	
	private String httpUrl;
	private Map<String, String> paramMap;
	private Object tag;
	
	public KjtHttpAdapter(String httpUrl, Map<String, String> param, Object tag) {
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
					PostBaseModel<JSON> baseModel = FastJson.toClass(bodyString, new TypeReference<PostBaseModel<JSON>>() {
					}.getType());
					
					int code = baseModel.getCode();
					if (code == 200) {
						JSON jsonData = baseModel.getData();
						// 最后一层转换，若数据为空，则不算错误，直接返回null
						if (null != jsonData) {
							T data = jsonData.toJavaObject(type);
							callback.onResponseSuccess(data);
						} else {
							callback.onResponseSuccess(null);
						}
					} else {
						IOException exception = new IOException("service error");
						callback.onResponseError(exception, code, baseModel.getMsg());
					}
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
		return failureException(ex, code, msg);
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
		String jsonRequestStr = buildRequestJson(params);
		RequestBody requestBody = RequestBody.create(JSON_TYPE, jsonRequestStr);
		requestBuilder.post(requestBody);
		
		// tag
		requestBuilder.tag(tag);
		
		// 返回结果
		return requestBuilder.build();
	}
	
	private static String buildRequestJson(Map<String, String> params) {
		HashMap<String, String> requestMap = new HashMap<>();
		
		requestMap.put("appUser", "wk");
		requestMap.put("version", "1.1.0");
		requestMap.put("osType", "android" + Build.VERSION.RELEASE);
		requestMap.put("userIP", "192.168.0.1");
		requestMap.put("mobileSerialNum", "123456789abcdefghijklmnopqrstubwxyz00000"); // 设备号写死，乱写一个
		if (null != params) {
			requestMap.putAll(params);
		}
		return FastJson.toString(requestMap);
	}
	
	/**
	 * 异常信息处理（沿袭之前的方案）
	 *
	 * @param ex   异常
	 * @param code 错误码 -100{IO异常}，-200{Json解析异常}，其它{服务器返回异常}
	 * @param msg  错误信息
	 */
	private static String failureException(final Exception ex, int code, String msg) {
		// 后台返回的异常信息
		if (code != OkHttpUtils.IO_ERROR_CODE && code != OkHttpUtils.HANDLE_ERROR_CODE) {
			return msg;
		}
		
		// 本地设置的异常信息
		if (ex instanceof TimeoutException || ex instanceof SocketTimeoutException) {
			return "连接超时";
		} else if (ex instanceof JSONException || ex instanceof XmlPullParserException || ex instanceof ParseException) {
			return "数据解析异常";
		} else if (ex instanceof NetworkErrorException) {
			return "网络异常";
		} else if (ex instanceof SSLException) {
			return "证书出错";
		} else if (ex instanceof ConnectException || ex instanceof UnknownHostException) {
			return "无网络,请重试!";
		} else {
			return "系统异常";
		}
	}
}
