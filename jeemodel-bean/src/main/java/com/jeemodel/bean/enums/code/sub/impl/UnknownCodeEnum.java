package com.jeemodel.bean.enums.code.sub.impl;


import com.jeemodel.bean.enums.code.CodeEnum;
import com.jeemodel.bean.enums.code.sub.IBaseSubCodeType;
import com.jeemodel.bean.enums.code.sub.ISubCodeSystem;
import com.jeemodel.bean.enums.code.sub.base.BaseSubCodeTypeSystemEnum;

import lombok.Getter;

/**
 * @author Rootfive
 */
@Getter
public enum UnknownCodeEnum implements ISubCodeSystem {

	
	/** 未知的错误：{@link CodeEnum#UNKNOWN} */
	UNKNOWN	(BaseSubCodeTypeSystemEnum.UNKNOWN,"嗯...是时候考虑杀个程序员祭天了~"),
	;

	/** 错误码类型 */
	private IBaseSubCodeType type;
	
	/** 注释;案语;批注 */
	private String note;

	

	private UnknownCodeEnum( IBaseSubCodeType type, String note) {
		this.type = type;
		this.note = note;
	}

	
	
	@Override
	public String getSubCode() {
		return this.name();
	}
	
}
