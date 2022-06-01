package com.jeemodel.core.constant;

import java.nio.charset.Charset;

/**
 * 通用常量信息
 * 
 * @author Rootfive
 */
public class Constants {

	/** ISO-8859-1 字符集 */
	public static final String ISO_8859_1 = "ISO-8859-1";
	/** UTF-8 字符集 */
	public static final String UTF_8 = "UTF-8";
	/** GBK 字符集 */
	public static final String GBK = "GBK";

	/** ISO-8859-1 */
	public static final Charset CHARSET_ISO_8859_1 = Charset.forName(ISO_8859_1);
	/** UTF-8 */
	public static final Charset CHARSET_UTF_8 = Charset.forName(UTF_8);
	/** GBK */
	public static final Charset CHARSET_GBK = Charset.forName(GBK);
	
	/**
	 * http请求
	 */
	public static final String HTTP = "http://";

	/**
	 * https请求
	 */
	public static final String HTTPS = "https://";

	/**
	 * 通用成功标识
	 */
	public static final String SUCCESS = "0";

	/**
	 * 通用失败标识
	 */
	public static final String FAIL = "1";

	/**
	 * RMI 远程方法调用
	 */
	public static final String LOOKUP_RMI = "rmi://";

	/**
	 * LDAP 远程方法调用
	 */
	public static final String LOOKUP_LDAP = "ldap://";
	
	/**
	 * 防重提交 redis key
	 */
	public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

}