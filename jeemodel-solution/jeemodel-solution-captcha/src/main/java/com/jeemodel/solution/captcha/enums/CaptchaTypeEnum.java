package com.jeemodel.solution.captcha.enums;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

/**
 * @author Rootfive
 * 验证码类型枚举
 */
@Getter
public enum CaptchaTypeEnum {

	/**	 算术验证码	 */
	ARITHMETIC(0, "算术验证码", "ArithmeticCaptcha"),

	/**	 中文验证码	 */
	CHINESE(1, "中文验证码", "ChineseCaptcha"),

	/**	 中文Gif验证码	 */
	CHINESE_GIF(2, "中文Gif验证码", "ChineseGifCaptcha"),

	/**	 动态Gif字符验证码 */
	Gif(3, "动态Gif字符验证码", "GifCaptcha"),

	/**	 png验证码 */
	Spec(4, "png验证码", "SpecCaptcha"),;

	private final int code;
	private final String info;
	private final String beanName;

	CaptchaTypeEnum(Integer code, String info, String beanName) {
		this.code = code;
		this.info = info;
		this.beanName = beanName;
	}

	private static Map<Integer, CaptchaTypeEnum> typeMap = new HashMap<>();

	static {
		for (CaptchaTypeEnum type : CaptchaTypeEnum.values()) {
			typeMap.put(type.code, type);
		}
	}

	public static CaptchaTypeEnum getCaptchaTypeByTypeCode(Integer code) {
		if (typeMap.containsKey(code)) {
			return typeMap.get(code);
		}
		return null;
	}
	
	public static String getCaptchaTypeBeanNameByTypeCode(Integer code) {
		if (typeMap.containsKey(code)) {
			return typeMap.get(code).getBeanName();
		}
		return null;
	}

}
