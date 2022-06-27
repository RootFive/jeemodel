package com.jeemodel.solution.netty.server.heartbeat;

import com.jeemodel.solution.netty.constants.NettyConstants;
import com.jeemodel.solution.netty.core.BaseNettyInboundHandler;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 对收到来自客户端的心跳包处理
 */
@Slf4j
public class ServerHeartBeatHandler extends BaseNettyInboundHandler<String> {

	/** 是否需要心跳回声 */
	private boolean needHeartBeatEcho;

	/**
	 * @param needEcho 是否需要心跳回声
	 */
	public ServerHeartBeatHandler(boolean needHeartBeatEcho) {
		this.needHeartBeatEcho = needHeartBeatEcho;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String data) throws Exception {
		try {
			log.debug("接收到客户端[{}]的心跳数据：{}", ctx.channel().remoteAddress(), data);
			if (needHeartBeatEcho) {
				ctx.writeAndFlush(NettyConstants.HEART_BEAT_ECHO);
			}
		} catch (Exception e) {
			log.error("接收到客户端，出现异常：", e);
		}
	}
}