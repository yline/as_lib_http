package com.lib.http.demo.normal.adapter;

import android.accounts.NetworkErrorException;
import android.os.Build;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import com.lib.http.demo.normal.model.PostBaseModel;
import com.yline.http.adapter.OnHttpAdapter;
import com.yline.http.adapter.helper.ClientHelper;
import com.yline.http.adapter.helper.FailureHelper;
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
					PostBaseModel<Object> baseModel = FastJson.toClass(bodyString, new TypeReference<PostBaseModel<Object>>() {
					}.getType());
					
					// 由于 具体数据，可能不符合JSON规范，因此进行了适配；具体规则如下（一般传Void和String表明无数据）
					// T == Void时，对应关系："" --> null ; {} --> java.lang.Void@地址 ; [] --> 异常; "123" --> null
					// T == String时，对应关系："" --> "" ; {} --> "{}" ; [] --> "[]"; "123" --> "123"
					// T == Integer时，对应关系："" --> null ; {} --> 异常; [] --> 异常; "123" --> 123
					// 公共集合，解析
					int code = baseModel.getCode();
					if (code == 200) {
						Object obj = baseModel.getData();
						if (obj instanceof JSON) { // 默认解析方式
							JSON jsonModel = (JSON) obj;
							T model = jsonModel.toJavaObject(type);
							callback.onResponseSuccess(model);
						} else {
							try {
								T t = TypeUtils.cast(obj, type, ParserConfig.getGlobalInstance());
								callback.onResponseSuccess(t);
							} catch (Exception ex) {
								callback.onResponseSuccess(null);
							}
						}
					} else {
						IOException exception = new IOException("service error");
						callback.onResponseError(exception, String.valueOf(code), baseModel.getMsg());
					}
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
}
