package com.lib.http.demo.model;

public class WNewsMultiplexModel
{
	/**
	 * 请求新闻开始号
	 */
	private int num1;

	/**
	 * 请求数据长度
	 */
	private int length;

	public WNewsMultiplexModel(int num1, int length)
	{
		this.num1 = num1;
		this.length = length;
	}

	public int getNum1()
	{
		return num1;
	}

	public void setNum1(int num1)
	{
		this.num1 = num1;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}
}
