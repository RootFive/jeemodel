package com.jeemodel.bean.utils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Rootfive 2021-1-29	 联系方式: QQ群：2236067977  
 * <p>API响应异常</p>
 * <blockquote>
 * 	<pre>API相应异常：此异常类在业务程序中，需要终止程序返回API非正常返回时使用，由全局异常拦截 GlobalExceptionHandler</pre>
 * 	<pre>GlobalExceptionHandler拦截之后，调用ApiRespUtils工具类解析业务异常，封装API响应 </pre>
 * @see com.jeemodel.core.global.GlobalExceptionHandler
 * @see com.jeemodel.core.utils.ApiRespUtils
 * </blockquote>
 * @since   JeeModel.0.0
 */
public class StringFormatUtils  {

	public static final String EMPTY_JSON = "{}";
	public static final char C_BACKSLASH = '\\';
	public static final char C_DELIM_START = '{';
	public static final char C_DELIM_END = '}';
	/** UTF-8 */
	public static final Charset CHARSET_UTF_8 = Charset.forName("UTF-8");
	
	/**
	 * 格式化字符串<br>
	 * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
	 * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
	 * 例：<br>
	 * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
	 * 转义{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
	 * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
	 * 
	 * @param template 字符串模板,文本模板，被替换的部分用 {} 表示
	 * @param argArray   参数列表
	 * @return 格式化后的文本
	 */
	public static String format(final String template, final Object... argArray) {
		if (StringUtils.isEmpty(template) || argArray == null ||  argArray.length == 0) {
			return StringUtils.trimToNull(template);
		}
		final int strPatternLength = template.length();

		// 初始化定义好的长度以获得更好的性能
		StringBuilder sbuf = new StringBuilder(strPatternLength + 50);

		int handledPosition = 0;
		int delimIndex;// 占位符所在位置
		for (int argIndex = 0; argIndex < argArray.length; argIndex++) {
			delimIndex = template.indexOf(EMPTY_JSON, handledPosition);
			if (delimIndex == -1) {
				if (handledPosition == 0) {
					return template;
				} else { // 字符串模板剩余部分不再包含占位符，加入剩余部分后返回结果
					sbuf.append(template, handledPosition, strPatternLength);
					return sbuf.toString();
				}
			} else {
				if (delimIndex > 0 && template.charAt(delimIndex - 1) == C_BACKSLASH) {
					if (delimIndex > 1 && template.charAt(delimIndex - 2) == C_BACKSLASH) {
						// 转义符之前还有一个转义符，占位符依旧有效
						sbuf.append(template, handledPosition, delimIndex - 1);
						sbuf.append(str(argArray[argIndex],CHARSET_UTF_8));
						handledPosition = delimIndex + 2;
					} else {
						// 占位符被转义
						argIndex--;
						sbuf.append(template, handledPosition, delimIndex - 1);
						sbuf.append(C_DELIM_START);
						handledPosition = delimIndex + 1;
					}
				} else {
					// 正常占位符
					sbuf.append(template, handledPosition, delimIndex);
					sbuf.append(str(argArray[argIndex],CHARSET_UTF_8));
					handledPosition = delimIndex + 2;
				}
			}
		}
		// 加入最后一个占位符后所有的字符
		sbuf.append(template, handledPosition, template.length());

		return sbuf.toString();
	}
	
	
	/**
	 * 将对象转为字符串<br>
	 * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组 
	 * 2、对象数组会调用Arrays.toString方法
	 *
	 * @param obj     对象
	 * @param charset 字符集
	 * @return 字符串
	 */
	private static String str(Object obj,Charset charset) {
		if (null == obj) {
			return null;
		}
		
		if (obj instanceof String) {
			return (String) obj;
		} else if (obj instanceof byte[]) {
			return str((byte[]) obj, charset);
		} else if (obj instanceof Byte[]) {
			byte[] bytes = ArrayUtils.toPrimitive((Byte[]) obj);
			return str(bytes, charset);
		} else if (obj instanceof ByteBuffer) {
			return str((ByteBuffer) obj, charset);
		}
		return obj.toString();
	}

}
