package com.jeemodel.core.exception;

import org.springframework.lang.NonNull;

import com.jeemodel.bean.enums.code.sub.ISubCodeServiceLogic;
import com.jeemodel.bean.enums.code.sub.impl.FAILCodeEnum;
import com.jeemodel.bean.exception.base.BaseServiceLogicException;
import com.jeemodel.core.utils.MessageUtils;
import com.jeemodel.core.utils.StringUtils;

import lombok.Getter;

/**
 * 基础模块异常
 * 
 * @author Rootfive
 */
@Getter
public class ModuleBaseI18nException extends BaseServiceLogicException {

	private static final long serialVersionUID = 1L;

	/**
	 * 所属模块Code
	 */
	private String moduleCode;

	/**
	 * 功能业务消息提示Code
	 */
	private String i18nTipCode;

	/**
	 * 错误码对应的参数
	 */
	private Object[] args;
	
	
	/**
	 * 空构造方法，避免反序列化问题
	 */
	@Deprecated
	public ModuleBaseI18nException() {
		super(FAILCodeEnum.FAIL);
	}

	public ModuleBaseI18nException(@NonNull ISubCodeServiceLogic subCode,@NonNull  String moduleCode, @NonNull String i18nTipCode, Object... args) {
		super(subCode);
		this.moduleCode = moduleCode;
		this.i18nTipCode = i18nTipCode;
		this.args = args;
	}

	@Override
	public String getMessage() {
		//获取国际化消息的Key
		String i18nMsgKey = new StringBuilder(moduleCode).append(".").append(i18nTipCode).toString();
		
		//获取国际化消息的消息内容
		String message  = MessageUtils.message(i18nMsgKey, args);;
		
		//消息为空，返回父类中message消息
		if (StringUtils.isBlank(message)) {
			message = super.getMessage();
		}
		
		return message;
	}
}
