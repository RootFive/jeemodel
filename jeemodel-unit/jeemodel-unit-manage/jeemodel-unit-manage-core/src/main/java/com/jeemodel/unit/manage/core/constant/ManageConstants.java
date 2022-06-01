package com.jeemodel.unit.manage.core.constant;

import io.jsonwebtoken.Claims;

/**
 * 管理系统常量信息
 * 
 */
public class ManageConstants {
	/**
	 * 登录成功
	 */
	public static final String LOGIN_SUCCESS = "Success";

	/**
	 * 注销
	 */
	public static final String LOGOUT = "Logout";

	/**
	 * 注册
	 */
	public static final String REGISTER = "Register";

	/**
	 * 登录失败
	 */
	public static final String LOGIN_FAIL = "Error";


	/**
	 * 登录用户 redis key
	 */
	public static final String LOGIN_TOKEN_KEY = "login_tokens:";


	/**
	 * 用户ID
	 */
	public static final String JWT_USERID = "userid";

	/**
	 * 用户名称
	 */
	public static final String JWT_USERNAME = Claims.SUBJECT;

	/**
	 * 用户头像
	 */
	public static final String JWT_AVATAR = "avatar";
	

	/**
	 * 用户权限
	 */
	public static final String JWT_AUTHORITIES = "authorities";

	/**
	 * 参数管理 cache key
	 */
	public static final String manage_config_KEY = "manage_config:";

	/**
	 * 字典管理 cache key
	 */
	public static final String manage_dict_KEY = "manage_dict:";

	/**
	 * 令牌前缀
	 */
	public static final String TOKEN_PREFIX = "Bearer ";

	/**
	 * 令牌前缀
	 */
	public static final String LOGIN_USER_KEY = "login_user_key";

	/**
	 * 创建时间
	 */
	public static final String JWT_CREATED = "created";
	
}
