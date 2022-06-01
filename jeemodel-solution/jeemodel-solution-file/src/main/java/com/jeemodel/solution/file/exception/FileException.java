package com.jeemodel.solution.file.exception;

import org.springframework.lang.NonNull;

import com.jeemodel.bean.enums.code.sub.ISubCodeServiceLogic;
import com.jeemodel.core.exception.ModuleBaseI18nException;

/**
 * 文件信息异常类
 * 
 * @author Rootfive
 */
public class FileException extends ModuleBaseI18nException {
	private static final long serialVersionUID = 1L;
	
	public FileException(@NonNull ISubCodeServiceLogic subCode,String msgCode, Object...args) {
		super(subCode,"file", msgCode, args);
	}

}
