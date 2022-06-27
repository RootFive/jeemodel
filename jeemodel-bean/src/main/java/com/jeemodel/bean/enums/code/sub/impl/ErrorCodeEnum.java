package com.jeemodel.bean.enums.code.sub.impl;


import com.jeemodel.bean.enums.code.sub.IBaseSubCodeType;
import com.jeemodel.bean.enums.code.sub.ISubCodeSystem;
import com.jeemodel.bean.enums.code.sub.base.BaseSubCodeTypeSystemEnum;

import lombok.Getter;

/**
 * @author Rootfive  SubCodeEnum细分化
 * <p>SubCode</p>
 */
@Getter
public enum ErrorCodeEnum implements ISubCodeSystem {

	
	/** 错误的请求：{@link SubCodeSysType2#ERROR} */
	ERROR	(BaseSubCodeTypeSystemEnum.ERROR,"▄︻┻┳═一…… ☆（>○<） "),
	
	
	//通用基本类型 上 XXX =========
	
	
	/** 请求错误 请求异常，命名范围：A0101-A0199 {@link SubCodeSysType2#REQUEST_ANOMALOUS}   */
	E0101( BaseSubCodeTypeSystemEnum.REQUEST_ANOMALOUS, "可疑暴力请求"),
	
	/** 并发超出上限 */
	E0102( BaseSubCodeTypeSystemEnum.REQUEST_ANOMALOUS, "并发超出上限"), 
	E0103( BaseSubCodeTypeSystemEnum.REQUEST_ANOMALOUS, "网关访问受限"), 
	E0104( BaseSubCodeTypeSystemEnum.REQUEST_ANOMALOUS, "请求IP受限"),
	E0105( BaseSubCodeTypeSystemEnum.REQUEST_ANOMALOUS, "登陆地域异常"),
	
	
	/** 请求错误 通用错误，命名范围：E0201-E0299 {@link SubCodeSysType2#ERROR}   */
	/** 资源地址不存在 */
	E0201( BaseSubCodeTypeSystemEnum.ERROR, "资源地址不存在"),
	E0202( BaseSubCodeTypeSystemEnum.ERROR, "资源被永久迁移"), 
	E0203( BaseSubCodeTypeSystemEnum.ERROR, "资源访问必须通过代理"),
	E0204( BaseSubCodeTypeSystemEnum.ERROR, "HTTP方法不支持"), 
	E0205( BaseSubCodeTypeSystemEnum.ERROR, "媒体格式不支持"),
	/** 报文格式不匹配 */
	E0206( BaseSubCodeTypeSystemEnum.ERROR, "报文格式不匹配"), 
	/** 请求内容，服务器无法处理 */
	E0207( BaseSubCodeTypeSystemEnum.ERROR, "请求内容，服务器无法处理"), 
	
	
	/** 请求错误 公参错误，命名范围：E0301-E0399 {@link SubCodeSysType2#ERROR}   */
	/** 请求客户端错误 */
	E0301( BaseSubCodeTypeSystemEnum.ERROR, "请求客户端错误"), 
	E0302( BaseSubCodeTypeSystemEnum.ERROR, "请求时间戳异常"), 
	E0303( BaseSubCodeTypeSystemEnum.ERROR, "请求渠道错误"),
	E0304( BaseSubCodeTypeSystemEnum.ERROR, "身份标识错误"),
	E0305( BaseSubCodeTypeSystemEnum.ERROR, "接口签名错误"), 
	E0306( BaseSubCodeTypeSystemEnum.ERROR, "接口名称错误"), 
	E0307( BaseSubCodeTypeSystemEnum.ERROR, "API版本错误"),
	E0308( BaseSubCodeTypeSystemEnum.ERROR, "API版本弃用"), 
	E0309( BaseSubCodeTypeSystemEnum.ERROR, "API版本过低"),
	E0310( BaseSubCodeTypeSystemEnum.ERROR, "API版本过高"),
	E0311( BaseSubCodeTypeSystemEnum.ERROR, "响应格式不支持"),

	;

	/** 错误码类型 */
	private IBaseSubCodeType type;
	
	/** 注释;案语;批注 */
	private String note;

	

	private ErrorCodeEnum( IBaseSubCodeType type, String note) {
		this.type = type;
		this.note = note;
	}


	@Override
	public String getSubCode() {
		return this.name();
	}

}
