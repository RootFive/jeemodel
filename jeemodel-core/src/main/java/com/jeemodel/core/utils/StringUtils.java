package com.jeemodel.core.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.util.AntPathMatcher;

import com.jeemodel.bean.helper.JeeModelToStringJSONStyle;
import com.jeemodel.core.constant.Constants;

/**
 * 字符串工具类
 * 
 * @author Rootfive
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	/** 空字符串 */
	private static final String NULLSTR = "";

	/** 下划线 */
	private static final char SEPARATOR = '_';

	public static final String EMPTY_JSON = "{}";
	public static final char C_BACKSLASH = '\\';
	public static final char C_DELIM_START = '{';
	public static final char C_DELIM_END = '}';

	/**
	 * 获取参数不为空值
	 * 
	 * @param value defaultValue 要判断的value
	 * @return value 返回值
	 */
	public static <T> T nvl(T value, T defaultValue) {
		return value != null ? value : defaultValue;
	}

	/**
	 * 去空格
	 */
	public static String trim(String str) {
		return (str == null ? "" : str.trim());
	}

	/**
	 * 截取字符串
	 * 
	 * @param str   字符串
	 * @param start 开始
	 * @return 结果
	 */
	public static String substring(final String str, int start) {
		if (str == null) {
			return NULLSTR;
		}

		if (start < 0) {
			start = str.length() + start;
		}

		if (start < 0) {
			start = 0;
		}
		if (start > str.length()) {
			return NULLSTR;
		}

		return str.substring(start);
	}

	/**
	 * 截取字符串
	 * 
	 * @param str   字符串
	 * @param start 开始
	 * @param end   结束
	 * @return 结果
	 */
	public static String substring(final String str, int start, int end) {
		if (str == null) {
			return NULLSTR;
		}

		if (end < 0) {
			end = str.length() + end;
		}
		if (start < 0) {
			start = str.length() + start;
		}

		if (end > str.length()) {
			end = str.length();
		}

		if (start > end) {
			return NULLSTR;
		}

		if (start < 0) {
			start = 0;
		}
		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

	/**
	 * 格式化字符串<br>
	 * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
	 * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
	 * 例：<br>
	 * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
	 * 转义{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
	 * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
	 * 
	 * @param strPattern 字符串模板,文本模板，被替换的部分用 {} 表示
	 * @param argArray   参数列表
	 * @return 格式化后的文本
	 */
	public static String format(final String template, final Object... argArray) {
		if (StringUtils.isEmpty(template) || BlankUtils.isBlank(argArray)) {
			return template;
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
						sbuf.append(DataTypeConvertUtils.utf8Str(argArray[argIndex]));
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
					sbuf.append(DataTypeConvertUtils.utf8Str(argArray[argIndex]));
					handledPosition = delimIndex + 2;
				}
			}
		}
		// 加入最后一个占位符后所有的字符
		sbuf.append(template, handledPosition, template.length());

		return sbuf.toString();
	}

	/**
	 * 是否为http(s)://开头
	 * 
	 * @param link 链接
	 * @return 结果
	 */
	public static boolean ishttp(String link) {
		return StringUtils.startsWithAny(link, Constants.HTTP, Constants.HTTPS);
	}

	/**
	 * 字符串转set
	 * 
	 * @param str 字符串
	 * @param sep 分隔符
	 * @return set集合
	 */
	public static final Set<String> str2Set(String str, String sep) {
		return new HashSet<String>(str2List(str, sep, true, false));
	}

	/**
	 * 字符串转list
	 * 
	 * @param str         字符串
	 * @param sep         分隔符
	 * @param filterBlank 过滤纯空白
	 * @param trim        去掉首尾空白
	 * @return list集合
	 */
	public static final List<String> str2List(String str, String sep, boolean filterBlank, boolean trim) {
		List<String> list = new ArrayList<String>();
		if (StringUtils.isEmpty(str)) {
			return list;
		}

		// 过滤空白字符串
		if (filterBlank && StringUtils.isBlank(str)) {
			return list;
		}
		String[] split = str.split(sep);
		for (String string : split) {
			if (filterBlank && StringUtils.isBlank(string)) {
				continue;
			}
			if (trim) {
				string = string.trim();
			}
			list.add(string);
		}

		return list;
	}

	/**
	 * 查找指定字符串是否包含指定字符串列表中的任意一个字符串同时串忽略大小写
	 *
	 * @param cs                  指定字符串
	 * @param searchCharSequences 需要检查的字符串数组
	 * @return 是否包含任意一个字符串
	 */
	public static boolean containsAnyIgnoreCase(CharSequence cs, CharSequence... searchCharSequences) {
		if (isEmpty(cs) || BlankUtils.isBlank(searchCharSequences)) {
			return false;
		}
		for (CharSequence testStr : searchCharSequences) {
			if (containsIgnoreCase(cs, testStr)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 驼峰转下划线命名
	 */
	public static String toUnderScoreCase(String str) {
		if (str == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		// 前置字符是否大写
		boolean preCharIsUpperCase = true;
		// 当前字符是否大写
		boolean curreCharIsUpperCase = true;
		// 下一字符是否大写
		boolean nexteCharIsUpperCase = true;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (i > 0) {
				preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
			} else {
				preCharIsUpperCase = false;
			}

			curreCharIsUpperCase = Character.isUpperCase(c);

			if (i < (str.length() - 1)) {
				nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
			}

			if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
				sb.append(SEPARATOR);
			} else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
				sb.append(SEPARATOR);
			}
			sb.append(Character.toLowerCase(c));
		}

		return sb.toString();
	}

	/**
	 * 是否包含字符串
	 * 
	 * @param str  验证字符串
	 * @param strs 字符串组
	 * @return 包含返回true
	 */
	public static boolean inStringIgnoreCase(String str, String... strs) {
		if (str != null && strs != null) {
			for (String s : strs) {
				if (str.equalsIgnoreCase(trim(s))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。
	 * 例如：HELLO_WORLD->HelloWorld
	 * 
	 * @param name 转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String convertToCamelCase(String name) {
		StringBuilder result = new StringBuilder();
		// 快速检查
		if (name == null || name.isEmpty()) {
			// 没必要转换
			return "";
		} else if (!name.contains("_")) {
			// 不含下划线，仅将首字母大写
			return name.substring(0, 1).toUpperCase() + name.substring(1);
		}
		// 用下划线将原始字符串分割
		String[] camels = name.split("_");
		for (String camel : camels) {
			// 跳过原始字符串中开头、结尾的下换线或双重下划线
			if (camel.isEmpty()) {
				continue;
			}
			// 首字母大写
			result.append(camel.substring(0, 1).toUpperCase());
			result.append(camel.substring(1).toLowerCase());
		}
		return result.toString();
	}

	/**
	 * 驼峰式命名法 例如：user_name->userName
	 */
	public static String toCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = s.toLowerCase();
		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 查找指定字符串是否匹配指定字符串列表中的任意一个字符串
	 * 
	 * @param str  指定字符串
	 * @param strs 需要检查的字符串数组
	 * @return 是否匹配
	 */
	public static boolean matches(String str, List<String> strs) {
		if (isEmpty(str) || BlankUtils.isBlank(strs)) {
			return false;
		}
		for (String pattern : strs) {
			if (isMatch(pattern, str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断url是否与规则配置: ? 表示单个字符; * 表示一层路径内的任意字符串，不可跨层级; ** 表示任意层路径;
	 * 
	 * @param pattern 匹配规则
	 * @param url     需要匹配的url
	 * @return
	 */
	public static boolean isMatch(String pattern, String url) {
		AntPathMatcher matcher = new AntPathMatcher();
		return matcher.match(pattern, url);
	}

	@SuppressWarnings("unchecked")
	public static <T> T cast(Object obj) {
		return (T) obj;
	}

	/**
	 *  	以JSON重写实体类的toString()方法
	 * @param object
	 * @return
	 */
	public static String toStringStyleJson(final Object object) {
		return ReflectionToStringBuilder.toString(object, JeeModelToStringJSONStyle.JSON_STYLE);
	}
}