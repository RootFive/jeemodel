package com.jeemodel.solution.netty.core;

import java.net.SocketAddress;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PreDestroy;

import com.jeemodel.solution.netty.constants.NettyConstants;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Rootfive
 * 服务器端的启动对象，配置参数时：option 主要负责设置 Boss 线程组，childOption 对应的是 Worker 线程组。
 */
@Slf4j
public abstract class BaseNettyServer implements INettyServer {
	/**
	 * 1. 创建两个线程组 bossGroup 和 workerGroup
	 * 2. bossGroup 只是用于服务端接受客户端连接 , 
	 * 	  workerGroup 用于进行SocketChannel 网络读写，真正的和客户端业务处理
	 * 3. 两个都是无限循环使用
	 * 4. bossGroup 和 workerGroup 含有的子线程(NioEventLoop)的个数，默认实际 cpu核数 * 2
	 * 特别说明：
	 * a.服务端是两个线程组，原因就是服务端实际上就是主从的 reactor模型。
	 * b.正常情况下：Netty的boss线程池是处理Accept事件的，不管线程池多大，只会使用一个线程，
	 * c.既然只使用一个线程为什么要用线程池呢？主要是异常的情况下，线程die了，可以再创建一个新线程，
	 * d.那什么情况下boss线程池可以使用多个线程呢？那就是当ServerBootstrap bind多个端口时。每个端口都有一个线程eventloop accept事件。
	 */
	protected NioEventLoopGroup bossGroup;
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
	 * 创建服务器端的启动对象，用于配置参数， 俗称程序引导器
	 * (option 主要负责设置 Boss 线程组，而 childOption 对应的是 Worker 线程组。)
	 */
	protected ServerBootstrap serverBootstrap;

	/**
	 * 服务启动
	 * 保存<code>Channel</code>, 可在其他非handler的地方发送数据
	 */
	protected Channel channel;

	/**
	 * 初始化线程组和服务器端的启动对象
	 */
	protected BaseNettyServer() {
		log.debug("开始-初始化[Netty-Server]的线程组和服务器（启动对象）");

		//处理连接请求的线程组
		bossGroup = new NioEventLoopGroup(NettyConstants.AVAILABLE_PROCESSORS, new ThreadFactory() {
			private AtomicInteger index = new AtomicInteger(0);

			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "BOSS_" + index.incrementAndGet());
			}
		});

		//处理客户端业务的线程组
		int  workerGroupNThreads = NettyConstants.AVAILABLE_PROCESSORS * 10;
		workerGroup = new NioEventLoopGroup(workerGroupNThreads, new ThreadFactory() {
			private AtomicInteger index = new AtomicInteger(0);

			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "WORKER_" + index.incrementAndGet());
			}
		});
		
		log.debug("初始化[Netty-Server] [bossGroup]线程组，线程数：{} [workerGroup]的线程组，线程数：{} -均已完成",NettyConstants.AVAILABLE_PROCESSORS,workerGroupNThreads);

		//服务器的启动对象
		serverBootstrap = new ServerBootstrap();
		log.debug("初始化[Netty-Server] 服务器的启动对象 [serverBootstrap]-已完成");
		
		log.debug("开始-[Netty-Server]设置服务配置");
		
		/**
		 * 设置调度线程（bossGroup）和工作线程（workerGroup）， 
		 * 服务端是两个线程组，原因就是服务端实际上就是主从的 reactor模型。
		 */
		serverBootstrap.group(bossGroup, workerGroup);
		
		/**
		 * 使用NioServerSocketChannel 作为服务器的通道实现， 
		 * 这里实际上就是传入一个类型，由这个决定了你要使用NIO，还是OIO
		 */
		serverBootstrap.channel(NioServerSocketChannel.class);
		// 设置保持活动连接状态
		serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
		// 通过NoDelay禁用Nagle,使消息立即发出去，不用等待到一定的数据量才发出去
		serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
		// 该 handler对应 bossGroup, 这里给 bossGroup 加个日志处理器
		serverBootstrap.handler(new LoggingHandler(LogLevel.DEBUG));

		
		
	}

	/**
	 *https://codingdict.com/questions/37623
	 */
	@Override
	public void start() {
		try {
			// 启动服务器并绑定绑定端口，开始接收进来的连接
			channelFuture = serverBootstrap.bind().sync();

			SocketAddress localAddress = channelFuture.channel().localAddress();
			log.info("[Netty服务端] 启动成功, [{}]", localAddress);

			/**
			 * ChannelFuture.channel().closeFuture().sync() 执行完 会使当前线程 wait 阻塞后续逻辑执行
			 * 参考博文：https://blog.csdn.net/oschina_40730821/article/details/108224455
			 */
			//			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			log.error("[Netty服务端]启动异常", e);
			shutdown(); // 关闭并释放资源
		}
	}

	/**
	 * 资源释放
	 */
	@PreDestroy
	@Override
	public void shutdown() {

		if (channelFuture != null) {
			channelFuture.channel().close();
		}

		if (bossGroup != null) {
			bossGroup.shutdownGracefully();
		}

		if (workerGroup != null) {
			workerGroup.shutdownGracefully();
		}

		log.info("[Netty服务端] 关闭成功 资源已释放");
	}
}
