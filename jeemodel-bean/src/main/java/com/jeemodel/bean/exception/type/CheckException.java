package com.jeemodel.bean.exception.type;

import com.jeemodel.bean.enums.code.sub.ISubCodeServiceLogic;
import com.jeemodel.bean.enums.code.sub.impl.FAILCodeEnum;
import com.jeemodel.bean.exception.base.BaseServiceLogicException;

import lombok.Getter;

/**
 * 检查校验异常
 * 
 * @author Rootfive
 *
 */
@Getter
public final class CheckException extends BaseServiceLogicException {

	private static final long serialVersionUID = 1L;

	/**
	 * 空构造方法，避免反序列化问题
	 */
	@Deprecated
	public CheckException() {
	}

	
	/**
	 * 	仅包含message, 没有cause, 也不记录栈异常, 性能最高
	 * @param subCodeServiceLogic  
	 */	
	public CheckException(ISubCodeServiceLogic subCodeServiceLogic) {
		super(subCodeServiceLogic);
	}
	
	/**
     * 	仅包含message, 没有cause, 也不记录栈异常, 性能最高
	 * @param template 字符串模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
     */	
	public CheckException(String template, Object... args) {
		super(FAILCodeEnum.FAIL, template, args);
	}
	
	/**
	 * 自定义描述
	 * @param subCode
	 * @param template 字符串模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 */
	public CheckException(ISubCodeServiceLogic subCodeServiceLogic, String template, Object... args) {
		super(subCodeServiceLogic,  template, args);
	}

}