package com.jeemodel.solution.file.exception;

import com.jeemodel.bean.enums.code.sub.impl.WarnCodeEnum;

/**
 * 文件名称超长限制异常类
 * 
 * @author Rootfive
 */
public class FileNameLengthLimitExceededException extends FileException {
	private static final long serialVersionUID = 1L;

	public FileNameLengthLimitExceededException(int defaultFileNameLength) {
		super(WarnCodeEnum.W0306,"upload.filename.exceed.length", new Object[] { defaultFileNameLength });
	}
}
