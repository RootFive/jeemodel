package com.jeemodel.unit.manage.core.enums;

import com.jeemodel.bean.enums.code.CodeEnum;
import com.jeemodel.bean.enums.code.sub.IBaseSubCodeTypeServiceLogic;

import lombok.Getter;

/**
 * @author Rootfive
 * <p>SubCode类型枚举</p>
 */
@Getter
public enum ManageSubCodeType  implements IBaseSubCodeTypeServiceLogic{
	
	/** 
	 * 用户账号，命名范围：U0101-U0199
	 * <p> Use with {@link CodeEnum#FAIL_122}}
	 */
	USER_ACCOUNT( CodeEnum.FAIL_122, "用户账号"), 
	
	/** 
	 * 用户密码，命名范围：U0201-U0299
	 * <p> Use with {@link CodeEnum#FAIL_122}}
	 */
	USER_PASSWORD( CodeEnum.FAIL_122, "用户密码"), 
	
	/** 
	 * 用户注册，命名范围：U0301-U0399
	 * <p> Use with {@link CodeEnum#FAIL_122}}
	 */	
	USER_REGISTER( CodeEnum.FAIL_122, "用户注册"), 
	
	
	/** 
	 * 用户登陆，命名范围：U0401-U0499
	 * <p> Use with {@link CodeEnum#FAIL_122}}
	 */
	USER_AUTHORIZATION( CodeEnum.FAIL_122, "用户登陆"),
	

	/** 
	 * 身份校验，命名范围：U0501-U0599
	 * <p> Use with {@link CodeEnum#FAIL_122}}
	 */
	USER_IDENTIFICATION_FAIL( CodeEnum.FAIL_122, "用户身份校验"),
	
	/** 
	 * 请求被拒，命名范围：U0601-U0699
	 * <p> Use with {@link CodeEnum#FAIL_122}}
	 */
	USER_NO_PASS( CodeEnum.FAIL_122, "用户请求被拒"),
	
	
	/** 
	 * 实名制证件号不正确，命名范围：U0701-U0799
	 * <p> Use with {@link CodeEnum#FAIL_122}}
	 */
	USER_REAL_NAME_ID_NUM_ERROR( CodeEnum.FAIL_122, "用户实名制证件号不正确"),
	
	;

	/** 父级别错误码 */
	private CodeEnum code;
	/** 注释;案语;批注 */
	private String note;

	private ManageSubCodeType(CodeEnum code) {
		this.code = code;
	}
	
	private ManageSubCodeType(CodeEnum code, String note) {
		this.code = code;
		this.note = note;
	}


}
