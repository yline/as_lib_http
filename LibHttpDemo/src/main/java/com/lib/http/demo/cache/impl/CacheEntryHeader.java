package com.lib.http.demo.cache.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Okio 储存的 规范类 的局部数据
 *
 * @author yline 2017/11/23 -- 19:56
 * @version 1.0.0
 */
class CacheEntryHeader {
	private final Map<String, String> namesAndValues = new HashMap<>();
	
	public void add(String line) {
		int index = line.indexOf(":", 1);
		if (-1 != index) {
			add(line.substring(0, index), line.substring(index + 2));
		} else if (line.startsWith(":")) {
			add("", line.substring(1)); // Empty header name
		} else {
			add("", line); // No header name
		}
	}
	
	public void add(String name, String value) {
		namesAndValues.put(name, value);
	}
	
	public String get(String name) {
		return namesAndValues.get(name);
	}
	
	public String remove(String name) {
		return namesAndValues.remove(name);
	}
	
	public Set<String> keySet() {
		return namesAndValues.keySet();
	}
	
	public Map<String, String> getMap() {
		return this.namesAndValues;
	}
	
	public int size() {
		return namesAndValues.size();
	}
}
