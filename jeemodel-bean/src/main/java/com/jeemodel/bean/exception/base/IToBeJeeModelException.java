package com.jeemodel.bean.exception.base;

import com.jeemodel.bean.enums.code.sub.IBaseSubCode;

/**
 * 全局异常接口
 * @author Rootfive
 *
 */
public interface IToBeJeeModelException {

	/**
	 * 消息子状态码
	 */
	IBaseSubCode  getBaseSubCode();
	
	
	/**
	 * 获取异常描述信息
	 * 
	 * 初始化异常顶级父类时使用:{@link java.lang.Throwable#Throwable(String, Throwable, boolean, boolean)} 
	 * 
	 * {@link java.lang.Throwable#getMessage()} 
	 * 
	 */
	String getMessage();
	
}
