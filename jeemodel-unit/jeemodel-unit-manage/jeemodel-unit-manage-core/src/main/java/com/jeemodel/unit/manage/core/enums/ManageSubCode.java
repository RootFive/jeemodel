package com.jeemodel.unit.manage.core.enums;


import com.jeemodel.bean.enums.code.sub.IBaseSubCodeType;
import com.jeemodel.bean.enums.code.sub.ISubCodeServiceLogic;

/**
 * @author Rootfive 
 * <p>SubCode</p>
 */
//@Getter
public enum ManageSubCode  implements ISubCodeServiceLogic{
	/* 
	 * 账号，命名范围：U0101-U0199
	 * <p> Use with {@link SubCodeTypeEnum2#USER_ACCOUNT}}
	 */
	/** 用户不存在 */
	U0101( ManageSubCodeType.USER_ACCOUNT, "不存在"), 
	/** 未激活，需要验证激活 */
	U0102( ManageSubCodeType.USER_ACCOUNT, "未激活"),
	/** 锁定，风控锁定，有一定的时间限制 */
	U0103( ManageSubCodeType.USER_ACCOUNT, "已锁定"),
	/** 冻结，系统发起，需要用户配合解冻 */
	U0104( ManageSubCodeType.USER_ACCOUNT, "被冻结"),
	/** 注销，使用者发起,永久注销， */
	U0105( ManageSubCodeType.USER_ACCOUNT, "已注销"),
	/** 黑名单用户-限制使用 */
	U0106( ManageSubCodeType.USER_ACCOUNT, "黑名单"),
	/** 用户-状态异常 */
	U0107( ManageSubCodeType.USER_ACCOUNT, "状态异常"),
	/** 用户-信息异常 */
	U0108( ManageSubCodeType.USER_ACCOUNT, "信息异常"),
	
	/** 
	 * 用户密码，命名范围：U0201-U0299
	 * <p> Use with {@link SubCodeTypeEnum2#USER_PASSWORD}}
	 */
	U0201( ManageSubCodeType.USER_PASSWORD, "错误"), 
	U0202( ManageSubCodeType.USER_PASSWORD, "错误次数超限"),
	U0203( ManageSubCodeType.USER_PASSWORD, "太短"),
	U0204( ManageSubCodeType.USER_PASSWORD, "太长"),
	U0205( ManageSubCodeType.USER_PASSWORD, "太简单"),
	
	
	/** 
	 * 用户注册，命名范围：U0301-U0399
	 * <p> Use with {@link SubCodeTypeEnum2#USER_REGISTER}}
	 */	
	U0301( ManageSubCodeType.USER_REGISTER, "未同意隐私协议"),
	U0302( ManageSubCodeType.USER_REGISTER, "未同意注册协议"), 
	U0303( ManageSubCodeType.USER_REGISTER, "注册国家或地区受限"),

	U0321( ManageSubCodeType.USER_REGISTER, "用户名校验失败"), 
	U0322( ManageSubCodeType.USER_REGISTER, "用户名已存在"), 
	U0323( ManageSubCodeType.USER_REGISTER, "用户名包含敏感词"), 
	U0324( ManageSubCodeType.USER_REGISTER, "用户名包含特殊字符"),
	
	
	/** 
	 * 用户登陆，命名范围：U0401-U0499
	 * <p> Use with {@link SubCodeTypeEnum2#USER_AUTHORIZATION}}
	 */
	U0401( ManageSubCodeType.USER_AUTHORIZATION, "认证失败"),
	U0402( ManageSubCodeType.USER_AUTHORIZATION, "认证过期"),
	U0403( ManageSubCodeType.USER_AUTHORIZATION, "第三方未授权"), 
	U0404( ManageSubCodeType.USER_AUTHORIZATION, "第三方授权已过期"),
	

//	/** 
//	 * 用户验证码/校验码，命名范围：U0501-U0599
//	 * <p> Use with {@link SubCodeTypeEnum2#USER_CAPTCHA}}
//	 */
//	U0501( ManageSubCodeType.USER_CAPTCHA, "输入错误"), 
//	U0502( ManageSubCodeType.USER_CAPTCHA, "已经过期"), 
//	U0503( ManageSubCodeType.USER_CAPTCHA, "输入错误"),
//	U0504( ManageSubCodeType.USER_CAPTCHA, "输入次数超限"),
	
	/** 
	 * 用户身份校验，命名范围：U0501-U0599
	 * <p> Use with {@link SubCodeTypeEnum2#USER_IDENTIFICATION_FAIL}}
	 */
	U0501( ManageSubCodeType.USER_IDENTIFICATION_FAIL, "安全密码错误"), 
	U0502( ManageSubCodeType.USER_IDENTIFICATION_FAIL, "指纹识别失败"), 
	U0503( ManageSubCodeType.USER_IDENTIFICATION_FAIL, "面容识别失败"),
	
	
	
	U0601(ManageSubCodeType.USER_NO_PASS, "账号未授权"),
	U0602(ManageSubCodeType.USER_NO_PASS, "正在授权中"),
	U0603(ManageSubCodeType.USER_NO_PASS, "授权已过期"), 
	U0604(ManageSubCodeType.USER_NO_PASS, "账号被冻结"),
	U0605(ManageSubCodeType.USER_NO_PASS, "服务已欠费"),
	U0606(ManageSubCodeType.USER_NO_PASS, "请求数超限"),
	U0607(ManageSubCodeType.USER_NO_PASS, "黑名单用户"), 
	
	
	
	
	
	/** 
	 * 用户实名制证件号不正确，命名范围：U0701-U0799
	 * <p> Use with {@link SubCodeTypeEnum2#USER_REAL_NAME_ID_NUM_ERROR}}
	 */
	U0701( ManageSubCodeType.USER_REAL_NAME_ID_NUM_ERROR, "大陆居民身份证号"), 
	U0702( ManageSubCodeType.USER_REAL_NAME_ID_NUM_ERROR, "大护护照号"),
	U0703( ManageSubCodeType.USER_REAL_NAME_ID_NUM_ERROR, "港澳居民来往内地通行证号"), 
	U0704( ManageSubCodeType.USER_REAL_NAME_ID_NUM_ERROR, "中华人民共和国来往港澳通行证号"), 
	U0705( ManageSubCodeType.USER_REAL_NAME_ID_NUM_ERROR, "军官证编号"),
	U0706( ManageSubCodeType.USER_REAL_NAME_ID_NUM_ERROR, "武警警官证号"), 
	U0707( ManageSubCodeType.USER_REAL_NAME_ID_NUM_ERROR, "士兵证号"),
	
	;

	/** 错误码类型 */
	private IBaseSubCodeType type;
	
	/** 注释;案语;批注 */
	private String note;

	

	private ManageSubCode( IBaseSubCodeType type) {
		this.type = type;
	}
	
	
	private ManageSubCode( IBaseSubCodeType type, String note) {
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
