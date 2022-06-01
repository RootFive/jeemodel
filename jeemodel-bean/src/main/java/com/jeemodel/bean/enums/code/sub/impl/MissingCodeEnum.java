package com.jeemodel.bean.enums.code.sub.impl;


import com.jeemodel.bean.enums.code.sub.IBaseSubCodeType;
import com.jeemodel.bean.enums.code.sub.ISubCodeServiceLogic;
import com.jeemodel.bean.enums.code.sub.base.BaseSubCodeTypeServiceLogicEnum;

import lombok.Getter;

/**
 * @author Rootfive
 */
@Getter
public enum MissingCodeEnum implements ISubCodeServiceLogic {

	
	/** 缺少必选参数：{@link SubCodeTypeEnum2#MISSING} */
	MISSING	(BaseSubCodeTypeServiceLogicEnum.MISSING,"要不再看看还缺点啥?"),
	;

	/** 错误码类型 */
	private IBaseSubCodeType type;
	
	/** 注释;案语;批注 */
	private String note;

	
	
	private MissingCodeEnum( IBaseSubCodeType type, String note) {
		this.type = type;
		this.note = note;
	}


	@Override
	public String getSubCode() {
		return this.name();
	}

}
