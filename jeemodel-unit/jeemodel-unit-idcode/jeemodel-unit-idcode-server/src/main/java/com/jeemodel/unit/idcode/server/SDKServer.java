package com.jeemodel.unit.idcode.server;

import java.net.InetSocketAddress;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.jeemodel.solution.netty.core.BaseNettyServer;
import com.jeemodel.unit.idcode.config.IDCodeServerConfig;

import io.netty.channel.ChannelOption;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "jeemodel.unit.idcode", name = "deploy", havingValue = "server")
public class SDKServer extends BaseNettyServer {

	@Resource
	private IDCodeServerConfig serverConfig;

	@Resource
	private SDKServerHandlerInitializer sdkServerHandlerInitializer;

	@Override
	public void init() {

		/**
		 * 设置线程队列得到连接个数，服务端处理客户端连接请求是顺序处理的， 同一时间只能处理一个客户端连接。
		 * 多个客户端同时来的时候,服务端将不能处理的客户端连接请求放在队列中等待处理
		 */
		serverBootstrap.option(ChannelOption.SO_BACKLOG, serverConfig.getSdkBacklogSize());
		// 端口绑定
		serverBootstrap.localAddress(new InetSocketAddress(serverConfig.getSdkServerPort()));
		/**
		 * childHandler这里创建一个通道初始化对象(匿名对象)，给workerGroup的EventLoop对应的管道设置处理器，
		 * 这个程序里边使用的是匿名对象，你也可以单独拿出去，用一个类实现 ChannelInitializer
		 */
		serverBootstrap.childHandler(sdkServerHandlerInitializer);
		log.debug("配置-[Netty-{}]服务-已完成", SDKServer.class.getSimpleName());
	}

}