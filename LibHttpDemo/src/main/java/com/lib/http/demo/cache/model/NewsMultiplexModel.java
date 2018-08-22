package com.lib.http.demo.cache.model;

import java.util.List;

/**
 * 多条新闻请求时,使用
 *
 * @author yline 2017/3/2 --> 14:10
 * @version 1.0.0
 */
public class NewsMultiplexModel
{
	/* 多条新闻 */
	private List<NewsSingleModel> list;

	public List<NewsSingleModel> getList()
	{
		return list;
	}

	public void setList(List<NewsSingleModel> list)
	{
		this.list = list;
	}
}
