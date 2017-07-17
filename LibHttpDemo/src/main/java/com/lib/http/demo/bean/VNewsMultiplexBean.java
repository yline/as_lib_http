package com.lib.http.demo.bean;

import java.util.List;

/**
 * 多条新闻请求时,使用
 *
 * @author yline 2017/3/2 --> 14:10
 * @version 1.0.0
 */
public class VNewsMultiplexBean
{
	/* 多条新闻 */
	private List<VNewsSingleBean> list;

	public List<VNewsSingleBean> getList()
	{
		return list;
	}
	
	public void setList(List<VNewsSingleBean> list)
	{
		this.list = list;
	}
}
