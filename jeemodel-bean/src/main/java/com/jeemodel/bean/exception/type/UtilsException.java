package com.jeemodel.bean.exception.type;

import com.jeemodel.bean.enums.code.sub.ISubCodeSystem;
import com.jeemodel.bean.enums.code.sub.impl.SosCodeEnum;
import com.jeemodel.bean.exception.base.BaseSystemException;

/**
 * 工具类异常
 * 
 * @author Rootfive
 */
public final class UtilsException extends BaseSystemException {

	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 空构造方法，避免反序列化问题
	 */
	@Deprecated
	public UtilsException() {
	}
	
	//===============
	// 上面是基础构造 XXX
	// 下面是拓展构造 XXX
	//===============
	
	/**
	 * 仅包含message, 没有cause, 也不记录栈异常, 性能最高
	 * @param template 异常的描述信息模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 */
	public UtilsException(String template, Object... args) {
		super(SosCodeEnum.SOS_PROG_E,true,template,args);
	}
	
	/**
	 * 构造方法：记录栈异常，生成栈追踪信息
	 * 包含message和cause, 会记录栈异常
	 * @param cause   导致此异常发生的父异常，即追踪信息里的caused by
	 * @param template 异常的描述信息模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 */
	public UtilsException(ISubCodeSystem subCodeSystem,Throwable cause, String template, Object... args) {
		super(SosCodeEnum.SOS_PROG_E,cause,template,args);
	}
	
}
