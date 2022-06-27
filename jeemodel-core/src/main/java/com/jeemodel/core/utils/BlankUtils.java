package com.jeemodel.core.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @description 判断字符串、集合、哈希、数组对象是否为空
 */
public class BlankUtils {

	/**
	 * * 判断一个对象是否为空
	 * 
	 * @param object Object
	 * @return true：为空 false：非空
	 */
	public static boolean isNull(final Object object) {
		return object == null;
	}

	/**
	 * * 判断一个对象是否非空
	 * 
	 * @param object Object
	 * @return true：非空 false：空
	 */
	public static boolean isNotNull(final Object object) {
		return !isNull(object);
	}

	/**
	 * 判断字符串是否为空
	 */
	public static boolean isBlank(final CharSequence cs) {
		return StringUtils.isBlank(cs);
	}

	/**
	 * 判断字符串是否为空
	 */
	public static boolean isNotBlank(final CharSequence cs) {
		return !isBlank(cs);
	}

	/**
	 * 判断字符是否为空
	 *
	 * @param c
	 */
	public static boolean isBlank(final Character c) {
		return isNull(c) || c.equals(' ');
	}

	/**
	 * 判断字符是否为空
	 *
	 * @param c
	 */
	public static boolean isNotBlank(final Character c) {
		return !isBlank(c);
	}

	/**
	 * * 判断一个Collection是否为空， 包含List，Set，Queue
	 * 
	 * @param coll 要判断的Collection
	 */
	public static boolean isBlank(final Collection<?> coll) {
		return CollectionUtils.isEmpty(coll);
	}

	/**
	 * * 判断一个Collection是否非空，包含List，Set，Queue
	 * 
	 * @param coll 要判断的Collection
	 */
	public static boolean isNotBlank(final Collection<?> coll) {
		return !isBlank(coll);
	}

	/**
	 * * 判断一个对象数组是否为空
	 * 
	 * @param objects 要判断的对象数组
	 */
	public static boolean isBlank(final Object[] objects) {
		return isNull(objects) || (objects.length == 0);
	}

	/**
	 * * 判断一个对象数组是否不为空
	 * 
	 * @param objects 要判断的对象数组
	 */
	public static boolean isNotBlank(final Object[] objects) {
		return !isBlank(objects);
	}

	/**
	 * 判断一个Map是否为空
	 * 
	 * @param map 要判断的Map
	 */
	public static boolean isBlank(final Map<?, ?> map) {
		return MapUtils.isEmpty(map);
	}

	/**
	 * 判断一个Map是否 不为空
	 * 
	 * @param map 要判断的Map
	 */
	public static boolean isNotBlank(final  Map<?, ?> map) {
		return !isBlank(map);
	}
	
	/**
	* <p>Checks if an Object is empty or null.</p>
	*
	* The following types are supported:
	* <ul>
	* <li>{@link CharSequence}: Considered empty if its length is zero.</li>
	* <li>{@code Array}: Considered empty if its length is zero.</li>
	* <li>{@link Collection}: Considered empty if it has zero elements.</li>
	* <li>{@link Map}: Considered empty if it has zero key-value mappings.</li>
	* </ul>
	*
	* <pre>
	* ObjectUtils.isEmpty(null)             = true
	* ObjectUtils.isEmpty(' ')              = true
	* ObjectUtils.isEmpty("")               = true
	* ObjectUtils.isEmpty(" ")              = true
	* ObjectUtils.isEmpty("ab")             = false
	* ObjectUtils.isEmpty(new int[]{})      = true
	* ObjectUtils.isEmpty(new int[]{1,2,3}) = false
	* ObjectUtils.isEmpty(1234)             = false
	* </pre>
	*
	* @param object  the {@code Object} to test, may be {@code null}
	* @return {@code true} if the object has a supported type and is empty or null,
	* {@code false} otherwise
	*/
	public static boolean isBlank(final Object obj) {
		if (obj == null) {
			return true;
		}

		if (obj instanceof Optional) {
			return !((Optional<?>) obj).isPresent();
		}
		if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
		}
		if (obj instanceof Collection) {
			return ((Collection<?>) obj).isEmpty();
		}
		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).isEmpty();
		}
		
		if (obj instanceof CharSequence) {
//			return ((CharSequence) obj).length() == 0;
			return StringUtils.isBlank((CharSequence) obj);
		}		
		// else
		return false;
	}

	/**
	 * <p>Checks if an Object is not empty and not null.</p>
	 *
	 * The following types are supported:
	 * <ul>
	 * <li>{@link CharSequence}: Considered empty if its length is zero.</li>
	 * <li>{@code Array}: Considered empty if its length is zero.</li>
	 * <li>{@link Collection}: Considered empty if it has zero elements.</li>
	 * <li>{@link Map}: Considered empty if it has zero key-value mappings.</li>
	 * </ul>
	 *
	 * <pre>
	 * ObjectUtils.isNotEmpty(null)             = false
	 * ObjectUtils.isNotEmpty(' ')              = false
	 * ObjectUtils.isNotEmpty("")               = false
	 * ObjectUtils.isNotEmpty(" ")              = false
	 * ObjectUtils.isNotEmpty("ab")             = true
	 * ObjectUtils.isNotEmpty(new int[]{})      = false
	 * ObjectUtils.isNotEmpty(new int[]{1,2,3}) = true
	 * ObjectUtils.isNotEmpty(1234)             = true
	 * </pre>
	 *
	 * @param object  the {@code Object} to test, may be {@code null}
	 * @return {@code true} if the object has an unsupported type or is not empty
	 * and not null, {@code false} otherwise
	 */
	public static boolean isNotBlank(final Object object) {
		return !isBlank(object);
	}

}