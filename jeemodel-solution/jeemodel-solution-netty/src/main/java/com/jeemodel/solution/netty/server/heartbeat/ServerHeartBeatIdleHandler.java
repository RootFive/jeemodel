package com.jeemodel.solution.netty.server.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务器端处理心跳的handler
 * 在规定时间内没有受到客户端的任何数据包(不局限于心跳), 将主动断开该连接
 */
@Slf4j
public class ServerHeartBeatIdleHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleState state = ((IdleStateEvent) evt).state();
			// 读超时
			if (state == IdleState.READER_IDLE) {
				log.debug("在规定时间内没有受到客户端的心跳 主动断开链接");
				//选择是否回复
				ctx.disconnect();
			}
		} else {
			/*
			 * ChannelInboundHandlerAdapter#userEventTriggered()提供的方法作为入口fireUserEventTriggered
			 *  这个是触发一个事件，以IdleStateHandler为例，
			 *  其一般作为心跳检测事件，放入线程池执行，判断空闲就会触发该方法，传导到各个handler。
			 */
			super.userEventTriggered(ctx, evt);
		}
	}
}