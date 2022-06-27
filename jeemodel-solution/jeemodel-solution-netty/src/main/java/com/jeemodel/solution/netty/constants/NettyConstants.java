package com.jeemodel.solution.netty.constants;

import com.jeemodel.core.constant.Constants;

/**
 * Netty 服务常用
 * @author Rootfive
 */
public class NettyConstants extends Constants {

	/** 客户端心跳 */
	public static final String CLIENT_HEART_BEAT = "ping";

	/** 客户端心跳-回声（服务端响应） */
	public static final String HEART_BEAT_ECHO = "pong";

	/**
	 * 宿主机计算资源:CPU核心线程数: Runtime.getRuntime().availableProcessors()方法询问jvm，jvm去问操作系统，操作系统去问硬件。。。。。。。
	 * 
	 * 获取的是cpu核心线程数也就是可用的计算资源。
	 * 注意：不是CPU物理核心数,对于支持超线程的CPU来说，单个物理处理器相当于拥有两个逻辑处理器，能够同时执行两个线程。
	 * 使用：
	 * 	1、cpu密集型计算推荐设置线程池核心线程数为N，也就是和cpu的线程数相同，可以尽可能低避免线程间上下文切换。
	 *  2、io密集型计算推荐设置线程池核心线程数为2N，但是这个数一般根据业务压测出来的，如果不涉及业务就使用推荐。
	 */
	public static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
}