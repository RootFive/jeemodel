package com.jeemodel.solution.netty.client.invoke;
//package com.jeemodel.solution.netty.core;
//
//import com.jeemodel.bean.rpc.Ping;
//import com.jeemodel.solution.netty.exception.BaseRemoteException;
//
///**
// * @author Rootfive
// */
//public interface INettyClientAPI<Q,P> {
//
//
//	/**
//	 * 同步请求
//	 * @param <P>	请求参数类型
//	 * @param <Q>	响应参数类型
//	 * @param ping	请求参数
//	 * @param timeoutMillis	请求超时时间
//	 * @return	 响应参数 <P>
//	 * @throws BaseRemoteException  远程服务异常
//	 */
//	P invokeSync(Ping<Q,P> ping, long timeoutMillis) throws BaseRemoteException;
//
//	/**
//	 * 异步请求
//	 * @param <P>	请求参数类型
//	 * @param <Q>	响应参数类型
//	 * @param ping	请求参数
//	 * @param timeoutMillis	请求超时时间
//	 * @param invokeCallback	调用响应回执函数
//	 * @throws BaseRemoteException	 远程服务异常
//	 */
//	void invokeAsync(Ping<Q,P> ping, long timeoutMillis, final InvokeCallback<P> invokeCallback) throws BaseRemoteException;
//			
//
//	/**
//	 * OneWay请求
//	 * @param <P>	请求参数类型
//	 * @param <Q>	响应参数类型
//	 * @param ping	请求参数
//	 * @param timeoutMillis	请求超时时间
//	 * @throws BaseRemoteException	 远程服务异常
//	 */
//	void  invokeOneWay(Ping<Q,P> ping, long timeoutMillis) throws BaseRemoteException;
//	
//
//}
