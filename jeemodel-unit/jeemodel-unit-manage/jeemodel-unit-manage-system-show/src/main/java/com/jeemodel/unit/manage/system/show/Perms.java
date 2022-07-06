package com.jeemodel.unit.manage.system.show;


import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>权限标识</p>
 */
@NoArgsConstructor
@Data
public class Perms {
	
	/**
	 * 	服务模块
	 */
	private String  module;
	
	/**
	 * 	业务标题
	 */
	private String  business;
	
	/**
	 *  	功能名
	 */
	private String  funName;
	
	
	public Perms(String module, String business, String funName) {
		super();
		this.module = module;
		this.business = business;
		this.funName = funName;
	}
	
	
	
	@Override
	public String toString() {
		JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
		builder.append("module", module);
		builder.append("business", business);
		builder.append("funName", funName);
		return builder.toString();
	}







}
