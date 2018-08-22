package com.lib.http.demo.normal.model;

import java.io.Serializable;

public class LoginModel implements Serializable {
	private static final long serialVersionUID = 5299424679819316702L;
	
	/**
	 * customerId : A10000012
	 * token : dad6427b6c193694b08b58c5afd88b49f215f6098e34bd70
	 */
	private String customerId;
	private String token;
	
	public String getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
}
