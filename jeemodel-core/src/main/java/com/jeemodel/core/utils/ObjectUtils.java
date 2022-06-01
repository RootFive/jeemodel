package com.jeemodel.core.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.jeemodel.bean.helper.JeeModelToStringJSONStyle;



/**
 * Object工具类
 * @author Rootfive
 *
 */
public class ObjectUtils {

	
    // Empty checks
    //-----------------------------------------------------------------------
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
     * @since 1.0.0
     */
	@SuppressWarnings("rawtypes")
	public static boolean isBlank( Object obj) {
		if (obj == null) {
			return true;
		}

		if (obj instanceof Optional) {
			return !((Optional) obj).isPresent();
		}
		if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
		}
		if (obj instanceof Collection) {
			return ((Collection) obj).isEmpty();
		}
		if (obj instanceof Map) {
			return ((Map) obj).isEmpty();
		}
		
		if (obj instanceof CharSequence) {
			return StringUtils.isBlank((CharSequence) obj);
		} else {
			return StringUtils.isBlank(new StringBuilder().append(obj));
		}
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
     * @since 1.0.0
     */
    public static boolean isNotBlank(final Object object) {
        return !isBlank(object);
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
