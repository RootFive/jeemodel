package com.jeemodel.bean.http;

/**
 * API回声ThreadLocal上下文
 * @author Rootfive
 */
public class ApiEchoContext {

	// 把构造函数私有化，外部不能new
	private ApiEchoContext() {}

	private static final ThreadLocal<String> echoContext = new ThreadLocal<>();

	/**
	 * 把API回声echo设置到ThreadLocal上下文
	 * @param API回声 echo
	 */
	public static void setEcho(String echo) {
		echoContext.set(echo);
	}

	/**
	 * 从ThreadLocal上下文中获取API回声echo值，并删除echo
	 * @returnAPI回声 echo
	 */
	public static String getEcho() {
		String echo = null;
		try {
			echo = echoContext.get();
		} finally {
			// 清除当前线程内引用，防止内存泄漏
			echoContext.remove();
		}
		return echo;
	}
}
