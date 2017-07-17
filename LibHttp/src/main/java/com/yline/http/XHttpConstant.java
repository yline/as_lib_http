package com.yline.http;

public class XHttpConstant
{
	// 默认成功的Code
	public static final int REQUEST_SUCCESS_CODE = 0;

	// 拦截器是否输出日志
	private static boolean isInterceptorDebug = true;

	// 缓存是否输出日志
	private static boolean isCacheDebug = true;

	// 输入输出是否日志
	private static boolean isDefaultDebug = true;

	public static boolean isInterceptorDebug()
	{
		return isInterceptorDebug;
	}

	public static void setIsInterceptorDebug(boolean isInterceptorDebug)
	{
		XHttpConstant.isInterceptorDebug = isInterceptorDebug;
	}

	public static boolean isCacheDebug()
	{
		return isCacheDebug;
	}

	public static void setIsCacheDebug(boolean isCacheDebug)
	{
		XHttpConstant.isCacheDebug = isCacheDebug;
	}

	public static boolean isDefaultDebug()
	{
		return isDefaultDebug;
	}

	public static void setIsDefaultDebug(boolean isDefaultDebug)
	{
		XHttpConstant.isDefaultDebug = isDefaultDebug;
	}
}
