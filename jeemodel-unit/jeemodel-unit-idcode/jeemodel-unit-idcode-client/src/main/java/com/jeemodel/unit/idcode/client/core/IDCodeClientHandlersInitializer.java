package com.jeemodel.unit.idcode.client.core;

import org.springframework.util.Assert;

import com.jeemodel.solution.netty.client.heartbeat.ClientHeartBeatEchoHandler;
import com.jeemodel.solution.netty.client.heartbeat.ClientHeartBeatIdleHandler;
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
//@Component
//@ConditionalOnProperty(prefix = "jeemodel.unit.idcode", name = "deploy", havingValue = "client")
public class IDCodeClientHandlersInitializer extends ChannelInitializer<SocketChannel> {

	/**
	 * ReconnectHandler 只在初始化客户端的时候new一个新的对象，
	 * 每个客户端都对应一个自己的 每个客户端的多个channel共享一个ReconnectHandler
	 */
	private RetryConnectHandler retryConnectHandler;

	public IDCodeClientHandlersInitializer(RetryConnectHandler retryConnectHandler) {
		Assert.notNull(retryConnectHandler, "RetryConnectHandler can not be null.");
		this.retryConnectHandler = retryConnectHandler;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		
		pipeline.addLast(ObjectEncoder.class.getSimpleName(), new ObjectEncoder()); // 自定义入站解码器
		pipeline.addLast(ObjectDecoder.class.getSimpleName(),new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader()))); // 自定义出站编码器

		//客户端掉线重连
		pipeline.addLast(RetryConnectHandler.class.getSimpleName(), this.retryConnectHandler);

		//客户端心跳
		/*
		 * new IdleStateHandler(3, 3, 0) 三个参数的含义：
		 * readerIdleTimeSeconds：读超时。即当在指定的时间间隔内没有从 Channel 读取到数据时，会触发一个 READER_IDLE 的 IdleStateEvent 事件
		 * writerIdleTimeSeconds: 写超时。即当在指定的时间间隔内没有数据写入到 Channel 时，会触发一个 WRITER_IDLE 的 IdleStateEvent 事件
		 * allIdleTimeSeconds: 读/写超时。即当在指定的时间间隔内没有读或写操作时，会触发一个 ALL_IDLE 的 IdleStateEvent 事件
		 * ===================================================================
		 * 三个参数默认的时间单位是秒。若需要指定其他时间单位，可以使用另一个构造方法： 
		 * public IdleStateHandler(boolean observeOutput,long readerIdleTime, long writerIdleTime, long allIdleTime,TimeUnit unit)
		 * ===================================================================
		 * 由于我们的需求是判断 Client 时候还要向 Server 发送请求，从而决定是否关闭该连接
		 * 所以，我们只需要判断 Server 是否在时间间隔内从 Channel 读取到数据
		 * 所以，readerIdleTimeSeconds 我们取 3s，而 writerIdleTimeSeconds 为 0
		 */
		IdleStateHandler idleStateHandler = new IdleStateHandler(3, 3, 0);
		pipeline.addLast(IdleStateHandler.class.getSimpleName(), idleStateHandler);

		
				

		
		pipeline.addLast(ClientHeartBeatIdleHandler.class.getSimpleName(), new ClientHeartBeatIdleHandler());
		pipeline.addLast(ClientHeartBeatEchoHandler.class.getSimpleName(), new ClientHeartBeatEchoHandler());
		
		
		//业务Handler
		pipeline.addLast(IDCodeClientHandler.class.getSimpleName(), new IDCodeClientHandler());
	}
}