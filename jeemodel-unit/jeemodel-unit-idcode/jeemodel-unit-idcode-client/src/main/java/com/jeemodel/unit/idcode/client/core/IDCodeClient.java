package com.jeemodel.unit.idcode.client.core;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.jeemodel.solution.netty.client.retry.ExponentialBackOffRetry;
import com.jeemodel.solution.netty.client.retry.RetryConnectHandler;
import com.jeemodel.solution.netty.core.BaseNettyClient;
import com.jeemodel.unit.idcode.client.config.IDCodeClientConfig;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Data
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Component
@ConditionalOnProperty(prefix = "jeemodel.unit.idcode", name = "deploy", havingValue = "client")
public class IDCodeClient extends BaseNettyClient {

	@Resource
	private IDCodeClientConfig clientConfig;

	@Override
	public void init() {
		// 设置工作线程组
		bootstrap.group(workerGroup);
		// 通道实现 NIO类型
		bootstrap.channel(NioSocketChannel.class);
		// SocketChannel 5s内未建立连接就抛出异常
		bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
		// 通过NoDelay禁用Nagle,使消息立即发出去，不用等待到一定的数据量才发出去
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		// 设置保持活动连接状态
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		/*
		 * handler这里创建一个通道初始化对象(匿名对象)， 
		 * 给workerGroup的EventLoop对应的管道设置处理器，
		 * 这个程序里边使用的是匿名对象，你也可以单独拿出去，用一个类实现 ChannelInitializer
		 */
		int retryBaseSleepSecond = clientConfig.getRetryBaseSleepSecond();
		int retryMaxSleepSecond = clientConfig.getRetryMaxSleepSecond();
		log.info("与服务器的TCP连接断线重连策略：最小等待时间：{}秒，最大等待时间：{}秒，重试次数上限：{}次", retryBaseSleepSecond, retryMaxSleepSecond,Integer.MAX_VALUE);
		ExponentialBackOffRetry retryPolicy = new ExponentialBackOffRetry(retryBaseSleepSecond, Integer.MAX_VALUE,retryMaxSleepSecond);
		RetryConnectHandler retryConnectHandler = new RetryConnectHandler(IDCodeClient.this, retryPolicy);
		bootstrap.handler(new IDCodeClientHandlersInitializer(retryConnectHandler));
	}

	/**
	 * 向远程TCP服务器请求连接
	 */
	public void start() {
		synchronized (bootstrap) {
			channelFuture = bootstrap.connect(clientConfig.getServerHost(), clientConfig.getServerSDKPort());
			// ChannelFuture 添加对象监听
			channelFuture.addListener(getConnectionListener());
		}
	}

	/**
	 * ChannelFuture 对象监听
	 * 
	 * @return
	 */
	private ChannelFutureListener getConnectionListener() {
		return new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (!future.isSuccess()) {
					log.warn("与服务器的TCP连接-失败");
					future.channel().pipeline().fireChannelInactive();
				} else {
					log.info("与服务器的TCP连接-成功 [{}]", future.channel().remoteAddress());

				}
			}
		};
	}

}