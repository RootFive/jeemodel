package com.jeemodel.unit.manage.exception;

import org.springframework.lang.NonNull;

import com.jeemodel.bean.enums.code.sub.ISubCodeServiceLogic;
import com.jeemodel.core.exception.ModuleBaseI18nException;

/**
 * 用户信息异常类
 * 
 * @author Rootfive
 */
public class UnitManageException extends ModuleBaseI18nException {
	private static final long serialVersionUID = 1L;

	public UnitManageException(@NonNull ISubCodeServiceLogic subCode, String code, Object[] args) {
		super(subCode, "manage", code, args);
	}
}
