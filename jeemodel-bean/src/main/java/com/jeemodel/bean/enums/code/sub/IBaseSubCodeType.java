package com.jeemodel.bean.enums.code.sub;

import com.jeemodel.bean.enums.code.CodeEnum;

/**
 * @author Rootfive
 * <p>SubCode类型枚举</p>
 */
public interface IBaseSubCodeType {

	/**
	 * 父级别错误码
	 * @return
	 */
	CodeEnum getCode();
	
	/**
	 * 
	 * @return 注释;案语;批注
	 */
	String getNote();
	
	
}
