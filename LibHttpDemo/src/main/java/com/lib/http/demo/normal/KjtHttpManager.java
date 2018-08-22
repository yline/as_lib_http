package com.lib.http.demo.normal;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.lib.http.demo.normal.adapter.KjtHttpAdapter;
import com.lib.http.demo.normal.model.LoginModel;
import com.yline.http.callback.OnJsonCallback;
import com.yline.http.OkHttpUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * 自定义 adapter返回处理方式
 *
 * @author yline
 * @times 2018/8/22 -- 14:02
 */
public class KjtHttpManager {
	public static void loginNormal(String phone, String password, OnJsonCallback<LoginModel> callback) {
		String loginUrl = "http://47.98.162.234/api/" + "a/login";
		
		ArrayMap<String, String> reqMap = new ArrayMap<>();
		reqMap.put("username", phone);
		reqMap.put("password", md5(password + password.length()));
		reqMap.put("loginType", "1");
		
		post(loginUrl, reqMap, callback);
	}
	
	/* ---------------------             基础工具            --------------------- */
	private static <T> void post(String httpUrl, Map<String, String> paramMap, final OnJsonCallback<T> callback) {
		OkHttpUtils.request(new KjtHttpAdapter(httpUrl, paramMap, "Normal"), callback);
	}
	
	/**
	 * 文件名md5
	 */
	private static String md5(String string) {
		if (TextUtils.isEmpty(string)) {
			return "";
		}
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] bytes = md5.digest(string.getBytes());
			StringBuilder result = new StringBuilder();
			for (byte b : bytes) {
				String temp = Integer.toHexString(b & 0xff);
				if (temp.length() == 1) {
					temp = "0" + temp;
				}
				result.append(temp);
			}
			return result.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}
