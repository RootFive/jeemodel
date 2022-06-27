package com.jeemodel.unit.idcode.server;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.jeemodel.solution.netty.server.heartbeat.ServerHeartBeatHandler;
import com.jeemodel.solution.netty.server.heartbeat.ServerHeartBeatIdleHandler;
import com.jeemodel.unit.idcode.config.IDCodeServerConfig;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>用于初始化服务器端涉及到的所有<code>Handler</code></p>
 * 通常将编码（Encode）称为序列化（serialization），它将对象序列化为字节数组，用于网络传输、数据持久化或者其它用途。
 * 反之将解码（Decode）称为反序列化（deserialization）把从网络、磁盘等读取的字节数组还原成原始对象（通常是原始对象的拷贝），以方便后续的业务逻辑操作。
 * 
 * Netty学习：ChannelHandler执行顺序详解: https://www.bbsmax.com/A/o75N66qM5W/
 * Handler执行顺序已经介绍完毕了，总结为：
 * 1、对于channelInboundHandler,总是会从传递事件的开始，向链表末尾方向遍历执行可用的inboundHandler。
 * 2、对于channelOutboundHandler，总是会从write事件执行的开始，向链表头部方向遍历执行可用的outboundHandler。
 * 举例说明如下代码：
 * 		ch.pipeline().addLast(new OutboundHandler1());
 * 		ch.pipeline().addLast(new OutboundHandler2());
 * 		ch.pipeline().addLast(new InboundHandler1());
 * 		ch.pipeline().addLast(new InboundHandler2());
 * 链表中的顺序为head->out1->out2->in1->in2->tail
 * 那么Inbound的执行顺序为read->in1->in2
 * 在Inbound执行write后，outbound执行顺序为out1<-out2<-write
 * 
 * ========================================================
 * 所以实际使用中，如果添加的顺序不好，很可能会意外跳过某些inbount或者outbound。建议实际使用上：
 * 先通过addFirst插入所有outBound
 * 再通过addLast插入所有inBound
 * 
 * 这样inBound与outBound的插入顺序与执行顺序完全一致，且不会出现跳过的情况。
 * 
 * 很多源码中的习惯都是只使用addLast或者addFirst插入，然后顺序在心中，具体方法见仁见智，保证顺序不错就行
 * 所以一些统一编码解码的handler，例如ssl，httpcodec，最好是按照顺序放在链表头！这样才会保证进出都会执行到并且业务逻辑可以正常插入
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "jeemodel.unit.idcode", name = "deploy", havingValue = "server")
public class SDKServerHandlerInitializer extends ChannelInitializer<SocketChannel> {

	@Resource
	private IDCodeServerConfig serverConfig;
	
	@Resource
	private SDKServerHandler sdkServerHandler;

	protected void initChannel(SocketChannel ch) throws Exception {
		log.info("初始化服务器端涉及到的所有Handler");
		ChannelPipeline pipeline = ch.pipeline();
		
		//入站解码器
		pipeline.addLast(new ObjectEncoder());
		//出站编码器
		pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader())));

		
		/**
		 * 设置5s读超时，如：new IdleStateHandler(5, 0, 0)
		 * 该handler代表如果在5秒内没有收到来自客户端的任何数据包（包括但不限于心跳包），将会主动断开与该客户端的连接。
		 */
		int readerIdleTimeSeconds = serverConfig.getReaderIdleTimeSeconds();
		int writerIdleTimeSeconds = serverConfig.getWriterIdleTimeSeconds();
		int allIdleTimeSeconds = serverConfig.getAllIdleTimeSeconds();
		
		pipeline.addLast(IdleStateHandler.class.getSimpleName(), new IdleStateHandler(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds));
		pipeline.addLast(ServerHeartBeatIdleHandler.class.getSimpleName(), new ServerHeartBeatIdleHandler());


		//客户端心跳处理器
		boolean needHeartBeatEcho = serverConfig.isNeedHeartBeatEcho();
		pipeline.addLast(ServerHeartBeatHandler.class.getSimpleName(), new ServerHeartBeatHandler(needHeartBeatEcho));

		//业务Handler
		pipeline.addLast(SDKServerHandler.class.getSimpleName(), sdkServerHandler);
	}

}