package com.lib.http.demo.normal.model;

import java.io.Serializable;

/**
 * 网络请求，返回的基础数据
 *
 * @author yline
 * @times 2018/8/22 -- 10:15
 */
public class PostBaseModel<T> implements Serializable {
	private static final long serialVersionUID = 8943183459595414871L;
	
	private int code;
	private String msg;
	private T data;
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
}
