package com.jeemodel.solution.netty.client.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 对收到来自服务端的心跳包处理
 */
@Slf4j
public class ClientHeartBeatEchoHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String data) throws Exception {
        try {
        	log.debug("接收到服务端[{}]的心跳回声：{}",ctx.channel().remoteAddress(),data);
        } catch (Exception e) {
        	log.error("接收到服务端，出现异常：",e);
        }
    }
}