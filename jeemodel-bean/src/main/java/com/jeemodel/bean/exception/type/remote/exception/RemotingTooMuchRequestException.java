package com.jeemodel.bean.exception.type.remote.exception;

import org.apache.commons.lang3.StringUtils;

public class RemotingTooMuchRequestException extends BaseRemoteException {
	private static final long serialVersionUID = 1L;

	public RemotingTooMuchRequestException(String message) {
		super(StringUtils.isBlank(message) ? "远程链接数超出上限" : message);
	}
}
