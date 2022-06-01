package com.jeemodel.solution.file.exception;

import com.jeemodel.bean.enums.code.sub.impl.WarnCodeEnum;

/**
 * 文件名大小限制异常类
 * 
 * @author Rootfive
 */
public class FileSizeLimitExceededException extends FileException {
	private static final long serialVersionUID = 1L;

	public FileSizeLimitExceededException(long defaultMaxSize) {
		super(WarnCodeEnum.W0307,"upload.exceed.maxSize", defaultMaxSize);
	}
}
