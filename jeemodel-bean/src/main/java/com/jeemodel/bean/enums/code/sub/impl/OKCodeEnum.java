package com.jeemodel.bean.enums.code.sub.impl;


import com.jeemodel.bean.enums.code.sub.IBaseSubCodeType;
import com.jeemodel.bean.enums.code.sub.ISubCodeSystem;
import com.jeemodel.bean.enums.code.sub.base.BaseSubCodeTypeSystemEnum;

import lombok.Getter;

/**
 * @author Rootfive
 */
@Getter
public enum OKCodeEnum implements ISubCodeSystem {

	/** 全部成功：{@link SubCodeSysType2#OK} */
	ALL	(BaseSubCodeTypeSystemEnum.OK,"一切OK~"),
	
	/** 部分成功：{@link SubCodeSysType2#OK} */
	PART	(BaseSubCodeTypeSystemEnum.OK,"部分成功"),
	;

	/** 错误码类型 */
	private IBaseSubCodeType type;
	
	/** 注释;案语;批注 */
	private String note;
	
	

	private OKCodeEnum( IBaseSubCodeType type, String note) {
		this.type = type;
		this.note = note;
	}


	@Override
	public String getSubCode() {
		return this.name();
	}
}
