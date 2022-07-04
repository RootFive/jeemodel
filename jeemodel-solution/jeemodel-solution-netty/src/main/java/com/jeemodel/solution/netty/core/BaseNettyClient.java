package com.jeemodel.solution.netty.core;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.jeemodel.solution.netty.constants.NettyConstants;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseNettyClient implements INettyServer {

	/**
	 * 创建无线循环线程组  workerGroup:负责处理业务
	 */
	protected NioEventLoopGroup workerGroup;

	/**
	 * 【作用是用来保存Channel异步操作的结果】。
	 * 对于一个ChannelFuture可能已经完成，也可能未完成。
	 * 当一个I/O操作开始的时候，一个新的future对象就会被创建。
	 * 在开始的时候，新的future是未完成的状态－－它既非成功、失败，也非被取消，因为I/O操作还没有结束。
	 * 如果I/O操作以成功、失败或者被取消中的任何一种状态结束了，那么这个future将会被标记为已完成，并包含更多详细的信息（例如：失败的原因）。
	 * 请注意，即使是失败和被取消的状态，也是属于已完成的状态。
	 */
	protected ChannelFuture channelFuture;

	/**
	 * 服务器端的启动对象，用于配置参数(option 主要负责设置 Boss 线程组，而 childOption 对应的是 Worker 线程组。)
	 */
	protected Bootstrap bootstrap;
	

	protected BaseNettyClient() {
		log.debug("开始-初始化[Netty-Client]的线程组和服务器（启动对象）");

		// 处理业务的线程组
		int  workerGroupNThreads = NettyConstants.AVAILABLE_PROCESSORS * 10;
		workerGroup = new NioEventLoopGroup(workerGroupNThreads, new ThreadFactory() {
			private AtomicInteger index = new AtomicInteger(0);

			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "WORKER_" + index.incrementAndGet());
			}
		});
		log.debug("初始化[Netty-Client] 处理业务的线程组 [workerGroup]-已完成，线程数：{}",workerGroupNThreads);

		// 服务器的启动对象
		bootstrap = new Bootstrap();
		log.debug("初始化[Netty-Client] 服务器的启动对象 [serverBootstrap]-已完成");
	}

	@Override
	public void shutdown() {
		if (channelFuture != null) {
			channelFuture.channel().close();
		}
		if (workerGroup != null) {
			workerGroup.shutdownGracefully();
		}

		log.info("[Netty-Client]资源已释放");
	}
	
	
	/**
	 * 使用<code>Channel</code>, 可用于在其他非handler的地方发送数据
	 * @return
	 */
	public Channel channel() {
		return  channelFuture.channel();
	}
}