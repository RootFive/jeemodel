package com.jeemodel.solution.netty.client.invoke;

/**
 * 调用响应回执
 * @author Rootfive
 *
 */
public interface InvokeCallback<T> {
	
	/**
	 * 操作完成处理
	 * @param responseFuture
	 */
	void operationComplete(ResponseFuture<T> responseFuture);
}
