package com.jeemodel.bean.enums.code.sub.impl;

import com.jeemodel.bean.enums.code.CodeEnum;
import com.jeemodel.bean.enums.code.sub.IBaseSubCodeType;
import com.jeemodel.bean.enums.code.sub.ISubCodeServiceLogic;
import com.jeemodel.bean.enums.code.sub.base.BaseSubCodeTypeServiceLogicEnum;

import lombok.Getter;

/**
 * @author Rootfive 
 */
@Getter
public enum FAILCodeEnum implements ISubCodeServiceLogic {

	/** 业务处理失败：{@link CodeEnum#FAIL} */
	FAIL(BaseSubCodeTypeServiceLogicEnum.FAIL,"这次不行，下次再试试呗！"),
	
	/**
	 * 请求重复提交
	 */
	F0101(BaseSubCodeTypeServiceLogicEnum.REQUEST_NO_ACCESS, "请求重复提交"),
	
	/**
	 * API被禁用
	 */
	F0102(BaseSubCodeTypeServiceLogicEnum.REQUEST_NO_ACCESS, "API被禁用"),
	/**
	 * API维护中，请稍后重试
	 */
	F0103(BaseSubCodeTypeServiceLogicEnum.REQUEST_NO_ACCESS, "API维护中，请稍后重试"),
	/**
	 * 系统维护中，请稍后重试
	 */
	F0104(BaseSubCodeTypeServiceLogicEnum.REQUEST_NO_ACCESS, "系统维护中，请稍后重试"),

	;

	/** 错误码类型 */
	private IBaseSubCodeType type;

	/** 注释;案语;批注 */
	private String note;


	private FAILCodeEnum(IBaseSubCodeType type, String note) {
		this.type = type;
		this.note = note;
	}

	
	@Override
	public String getSubCode() {
		return this.name();
	}
}
