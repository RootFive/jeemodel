package com.jeemodel.bean.enums.code.sub.impl;


import com.jeemodel.bean.enums.code.sub.IBaseSubCodeType;
import com.jeemodel.bean.enums.code.sub.ISubCodeSystem;
import com.jeemodel.bean.enums.code.sub.base.BaseSubCodeTypeSystemEnum;

import lombok.Getter;

/**
 * @author Rootfive
 */
@Getter
public enum SosCodeEnum implements ISubCodeSystem {

	/** 程序出错 */
	SOS_PROG_E( BaseSubCodeTypeSystemEnum.SOS, "出Bug了... 可能是程序媛写的代码~"),
	
	/** 子服务不可用 子系统服务:Subsystem Service */
	SOS_SUB_SERVICE( BaseSubCodeTypeSystemEnum.SOS, "子服务不可用"),
	;

	/** 错误码类型 */
	private IBaseSubCodeType type;
	
	/** 注释;案语;批注 */
	private String note;

	

	private SosCodeEnum( IBaseSubCodeType type, String note) {
		this.type = type;
		this.note = note;
	}

	
	@Override
	public String getSubCode() {
		return this.name();
	}

}
