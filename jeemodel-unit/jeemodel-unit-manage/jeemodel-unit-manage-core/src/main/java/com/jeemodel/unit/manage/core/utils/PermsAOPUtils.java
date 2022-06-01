package com.jeemodel.unit.manage.core.utils;


import org.springframework.security.access.prepost.PreAuthorize;

import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.unit.manage.core.dto.Perms;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Rootfive 2021-3-12	 联系方式: QQ群：2236067977  
 * <p>TODO 描述作用</p>
 * <blockquote>
 * 	<pre>
 *     eg：String str = "abc";
 * 	</pre>
 * </blockquote>
 * @since   JeeModel 1.0.0
 */
@Deprecated
@Slf4j
public class PermsAOPUtils {

	/**
	 * 	<pre>获取菜单权限标识对象</pre>
	 * @param preAuthorize
	 * @return
	 */
	public final static Perms getPerms(PreAuthorize preAuthorize) {
		String permsString = getPermsString(preAuthorize);
		log.debug("permsString={}", permsString);
		String[] perms = StringUtils.split(permsString, ":");
		return new Perms(perms[0], perms[1], perms[2]);
	}
	
	
	/**
	 * 	<pre>获取菜单权限标识对象</pre>
	 * @param preAuthorize
	 * @return
	 */
	public final static Perms getPerms(String permsString ) {
		log.debug("permsString={}", permsString);
		String[] perms = StringUtils.split(permsString, ":");
		return new Perms(perms[0], perms[1], perms[2]);
	}
	
	
	/**
	 * 	<pre>获取菜单权限标识</pre>
	 * @param preAuthorize
	 * @return
	 */
	public final static String getPermsString(PreAuthorize preAuthorize) {
		String preAuthorizeValue = preAuthorize.value();
		log.debug("preAuthorizeValue={}", preAuthorizeValue);
		return StringUtils.substringBetween(preAuthorizeValue, "('", "')");
		
	}

}
