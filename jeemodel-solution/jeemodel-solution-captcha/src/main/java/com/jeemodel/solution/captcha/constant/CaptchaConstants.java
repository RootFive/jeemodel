package com.jeemodel.solution.captcha.constant;

/**
 * 验证码常量信息
 * 
 */
public class CaptchaConstants {

	/**
	 * 验证码 redis key
	 */
	public static final String CAPTCHA_CODE_KEY_PREFIX = "captcha_codes_";
	
	/**
	 * 验证码有效期（分钟）
	 */
	public static final Integer CAPTCHA_EXPIRATION = 2;
	
	
	/**
	 * 验证码应用场景redis key(前缀)
	 */
	public static final String CAPTCHA_SCENE_PREFIX = "captcha_scene_";
}