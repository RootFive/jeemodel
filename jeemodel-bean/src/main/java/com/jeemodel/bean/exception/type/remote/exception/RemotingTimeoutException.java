package com.jeemodel.bean.exception.type.remote.exception;

public class RemotingTimeoutException extends BaseRemoteException {

	private static final long serialVersionUID = 4106899185095245979L;

	public RemotingTimeoutException(String message) {
		super(message);
	}

	public RemotingTimeoutException(String addr, long timeoutMillis) {
		this(addr, timeoutMillis, null);
	}

	public RemotingTimeoutException(String addr, long timeoutMillis, Throwable cause) {
		super("等待<" + addr + ">响应超过"+ timeoutMillis + "(ms)，已经超时" , cause);
	}
}
