package com.lib.http.demo.model;

import java.util.List;

/**
 * 多条新闻请求时,使用
 *
 * @author yline 2017/3/2 --> 14:10
 * @version 1.0.0
 */
public class VNewsMultiplexModel
{
	/* 多条新闻 */
	private List<VNewsSingleModel> list;

	public List<VNewsSingleModel> getList()
	{
		return list;
	}

	public void setList(List<VNewsSingleModel> list)
	{
		this.list = list;
	}
}
