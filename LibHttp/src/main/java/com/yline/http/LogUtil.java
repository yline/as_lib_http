package com.yline.http;

import java.util.Locale;

/**
 * 日志工具
 * 不给外界使用，所以其他类使用通过 OkHttpUtils包装一次
 *
 * @author yline 2018/8/22 -- 15:29
 */
final class LogUtil {
	/* tag 定位  默认格式 */
	private static final String TAG_DEFAULT_LOCATION = "xxx->%s.%s(L:%d): ";
	
	/* msg 默认格式 */
	private static final String MSG_DEFAULT = "LibHttp -> %s";
	
	/* log trace 抛出的位置,两层,即:使用该工具的当前位置,作为默认 */
	private static final int LOG_LOCATION_NOW = 2;
	
	/* log 开关 */
	private static boolean isUtilLog = false;
	
	public static void setUtilLog(boolean isUtilLog) {
		LogUtil.isUtilLog = isUtilLog;
	}
	
	/**
	 * @param content 内容
	 */
	public static void v(String content) {
		if (isUtilLog) {
			android.util.Log.v(generateTag(LOG_LOCATION_NOW), String.format(MSG_DEFAULT, content));
		}
	}
	
	private static String generateTag(int location) {
		StackTraceElement caller = new Throwable().getStackTrace()[location];
		String clazzName = caller.getClassName();
		clazzName = clazzName.substring(clazzName.lastIndexOf(".") + 1);
		
		return String.format(Locale.CHINA,
				TAG_DEFAULT_LOCATION,
				clazzName,
				caller.getMethodName(),
				caller.getLineNumber());
	}
}