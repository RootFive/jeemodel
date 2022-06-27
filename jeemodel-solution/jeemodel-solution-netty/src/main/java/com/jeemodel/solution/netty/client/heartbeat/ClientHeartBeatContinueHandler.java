package com.jeemodel.solution.netty.client.heartbeat;

import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

import com.jeemodel.solution.netty.constants.NettyConstants;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;
import lombok.extern.slf4j.Slf4j;

/**
 * 持续发送心跳检测消息（不管服务器忙不忙，都是会定时间隔发送）
 * <p>客户端连接到服务器端后，会循环执行一个任务：随机等待几秒，然后ping一下Server端，即发送一个心跳包。</p>
 */
@Slf4j
public class ClientHeartBeatContinueHandler extends ChannelInboundHandlerAdapter {

	private int heartbeatSeaconds;

	public ClientHeartBeatContinueHandler(int heartbeatSeaconds) {
		super();
		this.heartbeatSeaconds = heartbeatSeaconds;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		ping(ctx.channel());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void ping(Channel channel) {
		SocketAddress remoteAddress = channel.remoteAddress();
		log.debug("TCP通道：[{}] 将在{}秒后发送下次心跳", remoteAddress, heartbeatSeaconds);
		// 定时向服务端发送心跳
		ScheduledFuture<?> future = channel.eventLoop().schedule(new Runnable() {
			@Override
			public void run() {
				if (channel.isActive()) {
					log.debug("TCP通道：[{}]-活跃,可以发送心跳", remoteAddress);
					channel.writeAndFlush(NettyConstants.CLIENT_HEART_BEAT);
				} else {
					log.debug("TCP通道：[{}]-断开,不能发送心跳", remoteAddress);
					channel.closeFuture();
					throw new RuntimeException();
				}
			}
		}, heartbeatSeaconds, TimeUnit.SECONDS);

		future.addListener(new GenericFutureListener() {
			@Override
			public void operationComplete(Future future) throws Exception {
				if (future.isSuccess()) {
					ping(channel);
				}
			}
		});
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 当Channel已经断开的情况下, 仍然发送数据, 会抛异常, 该方法会被调用.
		cause.printStackTrace();
		ctx.close();
	}
}