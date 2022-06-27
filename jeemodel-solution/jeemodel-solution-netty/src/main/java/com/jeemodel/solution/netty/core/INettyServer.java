package com.jeemodel.solution.netty.core;

/**
 * @author Rootfive
 */
public interface INettyServer {

	/**
	 * 初始服务配置
	 * @param port 端口
	 */
	void init();

	/**
	 * 启动 Netty服务
	 */
	void start();

	/**
	 * 关闭资源
	 */
	void shutdown();
	

}
