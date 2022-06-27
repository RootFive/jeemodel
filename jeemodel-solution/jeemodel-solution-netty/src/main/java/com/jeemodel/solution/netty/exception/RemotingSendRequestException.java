package com.jeemodel.solution.netty.exception;

public class RemotingSendRequestException extends BaseRemoteException {

	private static final long serialVersionUID = 5391285827332471674L;

	public RemotingSendRequestException(String addr) {
		super(addr);
	}

	public RemotingSendRequestException(Throwable cause, String addr) {
		super("send request to <" + addr + "> failed", cause);
	}
}
