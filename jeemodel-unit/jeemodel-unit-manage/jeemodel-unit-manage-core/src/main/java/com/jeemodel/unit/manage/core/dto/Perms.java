package com.jeemodel.unit.manage.core.dto;


import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Rootfive 2021-3-12	 联系方式: QQ群：2236067977  
 * <p>权限标识</p>
 * @since   JeeModel 1.0.0
 */
@Deprecated
@AllArgsConstructor
@Data
public class Perms {
	
	/**
	 * 	服务模块
	 */
	private String  module;
	
	/**
	 * 	业务标题
	 */
	private String  title;
	
	/**
	 *  	功能名
	 */
	private String  funName;
	
	@Override
	public String toString() {
		JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
		builder.append("module", module);
		builder.append("title", title);
		builder.append("funName", funName);
		return builder.toString();
	}

}
