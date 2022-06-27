package com.jeemodel.solution.netty.exception;

public class RemotingTooMuchRequestException extends BaseRemoteException {
	private static final long serialVersionUID = 4326919581254519654L;

	public RemotingTooMuchRequestException(String message) {
		super(message);
	}
}
