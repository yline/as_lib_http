package com.lib.http.demo.bean;

public class VNewsSingleBean
{
	/* 新闻ID标识 */
	private String news_id;

	/* 新闻发布时间 */
	private long news_time;

	/* 新闻标题 */
	private String news_title;

	/* 新闻图片(大图片) */
	private String news_img;

	/* 新闻来源 */
	private String news_source;

	/* 新闻链接 */
	private String url;

	public String getNews_id()
	{
		return news_id;
	}

	public void setNews_id(String news_id)
	{
		this.news_id = news_id;
	}

	public String getNews_img()
	{
		return news_img;
	}

	public void setNews_img(String news_img)
	{
		this.news_img = news_img;
	}

	public String getNews_source()
	{
		return news_source;
	}

	public void setNews_source(String news_source)
	{
		this.news_source = news_source;
	}

	public long getNews_time()
	{
		return news_time;
	}

	public void setNews_time(long news_time)
	{
		this.news_time = news_time;
	}

	public String getNews_title()
	{
		return news_title;
	}

	public void setNews_title(String news_title)
	{
		this.news_title = news_title;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	@Override
	public String toString()
	{
		return "VNewsSingleBean{" +
				"news_id='" + news_id + '\'' +
				", news_img='" + news_img + '\'' +
				", news_source='" + news_source + '\'' +
				", news_time=" + news_time +
				", news_title='" + news_title + '\'' +
				", url='" + url + '\'' +
				'}';
	}
}
