package com.jeemodel.bean.exception.type.remote.exception;

public class RemotingSendRequestException extends BaseRemoteException {

	private static final long serialVersionUID = 5391285827332471674L;

	public RemotingSendRequestException(String addr) {
		super(addr);
	}

	public RemotingSendRequestException(Throwable cause, String addr) {
		super("发送请求 <" + addr + "> 失败", cause);
	}
}
