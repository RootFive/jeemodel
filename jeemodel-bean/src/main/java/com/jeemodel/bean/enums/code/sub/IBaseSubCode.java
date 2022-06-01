package com.jeemodel.bean.enums.code.sub;

import org.apache.commons.lang3.StringUtils;

/**
 * 子错误码拓展接口
 * @author Rootfive
 *
 */
public interface IBaseSubCode {

	/**
	 * @return 错误码类型
	 */
	IBaseSubCodeType getType();

	/**
	 * @return 注释;案语;批注
	 */
	String getNote();
	
	/**
	 * @return 消息子状态码
	 */
	String getSubCode();
	
	
	/**
	 * @return 【消息子状态】的注释;案语;批注
	 */
	public default String getExplain() {
		StringBuilder sb = new StringBuilder();
		
		IBaseSubCodeType baseSubCodeType = this.getType();
		
		String typeNote = baseSubCodeType.getNote();
		if (StringUtils.isNotBlank(typeNote)) {
			sb.append(typeNote).append(":");
		}
		
		String thisNote = this.getNote();
		
		if (StringUtils.isNotBlank(thisNote)) {
			sb.append(thisNote);
		}
		return sb.toString();
	}
}
