package com.jeemodel.solution.captcha.enums;

import com.jeemodel.bean.enums.code.CodeEnum;
import com.jeemodel.bean.enums.code.sub.IBaseSubCodeType;

import lombok.Getter;

/**
 * @author Rootfive
 * <p>SubCode类型枚举</p>
 */

@Getter
//public enum CaptchaSubCodeType  implements IBaseSubCodeTypeServiceLogic{
public enum CaptchaSubCodeType  implements IBaseSubCodeType{
	/** 
	 * <p> Use with {@link CodeEnum#SOS_191519}}
	 */
	CAPTCHA_SYS_ERROR( CodeEnum.SOS_191519, "验证码/校验码"), 
	
	/** 
	 * <p> Use with {@link CodeEnum#FAIL_122}}
	 */
	CAPTCHA_SERVICE_LOGIC( CodeEnum.FAIL_122, "验证码/校验码"), 
	;

	/** 父级别错误码 */
	private CodeEnum code;
	/** 注释;案语;批注 */
	private String note;

	private CaptchaSubCodeType(CodeEnum code) {
		this.code = code;
	}
	
	private CaptchaSubCodeType(CodeEnum code, String note) {
		this.code = code;
		this.note = note;
	}


}
