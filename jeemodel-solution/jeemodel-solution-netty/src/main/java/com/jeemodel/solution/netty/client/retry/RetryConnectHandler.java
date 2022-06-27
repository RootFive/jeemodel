package com.jeemodel.solution.netty.client.retry;

import java.util.concurrent.TimeUnit;

import com.jeemodel.solution.netty.core.BaseNettyClient;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import lombok.extern.slf4j.Slf4j;

/*
 *  @Sharable 注解用来说明ChannelHandler可以在多个channel直接共享使用
 * 每次失败重连都是一个新的管道Channel 多个管道要共享这个ReconnectHandler
 */
@Slf4j
@ChannelHandler.Sharable
public class RetryConnectHandler extends ChannelInboundHandlerAdapter {

	/** 重连次数 */
	private int retries = 0;

	/** 重连策略 */
	private RetryPolicy retryPolicy;

	/**
	 *  重连的客户端
	 */
	private BaseNettyClient nettyClient;

	public RetryConnectHandler(BaseNettyClient nettyClient, RetryPolicy retryPolicy) {
		this.nettyClient = nettyClient;
		this.retryPolicy = retryPolicy;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.debug("与服务器创建TCP连接-成功：[{}]", ctx.channel().remoteAddress());
		retries = 0;
		ctx.fireChannelActive();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		if (retries == 0) {
			log.debug("与服务器的TCP连接-断开：[{}]", ctx.channel().remoteAddress());
			ctx.close();
		}

		boolean allowRetry = this.retryPolicy.allowRetry(retries);
		log.debug("已经重试次数:{},是否允许继续重新链接：{}", retries, allowRetry);
		if (allowRetry) {
			long sleepTimeMs = this.retryPolicy.getSleepTimeMs(retries);
			log.info("允许重新链接 当前重试次数:{}，等待时间：{}秒 ", ++retries, sleepTimeMs);
			final EventLoop eventLoop = ctx.channel().eventLoop();
			eventLoop.schedule(() -> {
				log.info("重新连接 ......");
				nettyClient.start();
			}, sleepTimeMs, TimeUnit.SECONDS);
		}
		// 现在处于不活动状态，调用ChannelInboundHandler的channelInactive
		ctx.fireChannelInactive();
	}
}