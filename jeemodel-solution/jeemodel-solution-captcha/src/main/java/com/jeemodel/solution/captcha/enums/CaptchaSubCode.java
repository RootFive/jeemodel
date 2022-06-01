package com.jeemodel.solution.captcha.enums;


import com.jeemodel.bean.enums.code.CodeEnum;
import com.jeemodel.bean.enums.code.sub.IBaseSubCodeType;
import com.jeemodel.bean.enums.code.sub.ISubCodeServiceLogic;
import com.jeemodel.bean.enums.code.sub.ISubCodeSystem;

/**
 * @author Rootfive 
 * <p>SubCode</p>
 * 中文名称:业务逻辑
 * 英文名称:service logic;SL
 * @author Rootfive
 * 业务逻辑主要涉及三种响应Code
 * 1、缺少参数 {@link CodeEnum#MISSING_119}
 * 2、参数非法 {@link CodeEnum#WARN_120}
 * 3、处理失败 {@link CodeEnum#FAIL_122}
 */
//@Getter
public enum CaptchaSubCode  implements ISubCodeSystem,ISubCodeServiceLogic{
	
	CS101( CaptchaSubCodeType.CAPTCHA_SYS_ERROR, "生成异常"),
	
	/** 
	 * 用户验证码/校验码
	 * <p> Use with {@link CaptchaSubCodeType#CAPTCHA_SERVICE_LOGIC}}
	 */
	CU101( CaptchaSubCodeType.CAPTCHA_SERVICE_LOGIC, "不能为空"),
	CU102( CaptchaSubCodeType.CAPTCHA_SERVICE_LOGIC, "已经过期"),
	CU103( CaptchaSubCodeType.CAPTCHA_SERVICE_LOGIC, "输入错误"),
	CU104( CaptchaSubCodeType.CAPTCHA_SERVICE_LOGIC, "输入次数超限"),
	
	;

	/** 错误码类型 */
	private IBaseSubCodeType type;
	
	/** 注释;案语;批注 */
	private String note;

	

	private CaptchaSubCode( IBaseSubCodeType type) {
		this.type = type;
	}
	
	
	private CaptchaSubCode( IBaseSubCodeType type, String note) {
		this.type = type;
		this.note = note;
	}

	
	
	public IBaseSubCodeType getType() {
		return type;
	}


	public String getNote() {
		return note;
	}


	@Override
	public String getSubCode() {
		return name();
	}

}
