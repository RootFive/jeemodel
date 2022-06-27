package com.jeemodel.unit.idcode.client.core;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.jeemodel.solution.netty.client.heartbeat.ClientHeartBeatEchoHandler;
import com.jeemodel.solution.netty.client.heartbeat.ClientHeartBeatIdleHandler;
import com.jeemodel.solution.netty.client.retry.ExponentialBackOffRetry;
import com.jeemodel.solution.netty.client.retry.RetryConnectHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 初始化客户端 Handlers程序
 * UID.client
 */
@Component
@ConditionalOnProperty(prefix = "jeemodel.unit.idcode", name = "deploy", havingValue = "client")
public class IDCodeClientHandlersInitializer extends ChannelInitializer<SocketChannel> {

	/**
	 * ReconnectHandler 只在初始化客户端的时候new一个新的对象，
	 * 每个客户端都对应一个自己的 每个客户端的多个channel共享一个ReconnectHandler
	 */
	private RetryConnectHandler retryConnectHandler;

	public IDCodeClientHandlersInitializer(IDCodeClient iDcodeClient) {
		Assert.notNull(iDcodeClient, "TcpClient can not be null.");
		ExponentialBackOffRetry retryPolicy = new ExponentialBackOffRetry(1, Integer.MAX_VALUE, 60);
		this.retryConnectHandler = new RetryConnectHandler(iDcodeClient, retryPolicy);
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		
		pipeline.addLast(ObjectEncoder.class.getSimpleName(), new ObjectEncoder()); // 自定义入站解码器
		pipeline.addLast(ObjectDecoder.class.getSimpleName(),new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader()))); // 自定义出站编码器

		//客户端掉线重连
		pipeline.addLast(RetryConnectHandler.class.getSimpleName(), this.retryConnectHandler);

		//客户端心跳
		pipeline.addLast(IdleStateHandler.class.getSimpleName(), new IdleStateHandler(3, 3, 0));

		
				

		
		pipeline.addLast(ClientHeartBeatIdleHandler.class.getSimpleName(), new ClientHeartBeatIdleHandler());
		pipeline.addLast(ClientHeartBeatEchoHandler.class.getSimpleName(), new ClientHeartBeatEchoHandler());
		
		
		//业务Handler
		pipeline.addLast(IDCodeClientHandler.class.getSimpleName(), new IDCodeClientHandler());
	}
}