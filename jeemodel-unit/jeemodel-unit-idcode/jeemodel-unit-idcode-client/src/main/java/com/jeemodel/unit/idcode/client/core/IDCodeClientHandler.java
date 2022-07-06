package com.jeemodel.unit.idcode.client.core;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.jeemodel.bean.rpc.PongData;
import com.jeemodel.solution.netty.client.invoke.ResponseFuture;
import com.jeemodel.solution.netty.core.BaseNettyInboundHandler;
import com.jeemodel.solution.netty.utils.NettyUtils;
import com.jeemodel.unit.idcode.client.constants.IDCodeClientConstants;
import com.jeemodel.unit.idcode.common.bean.ProtoDTO;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Rootfive
 * 断线重连
 */
@Slf4j
@ChannelHandler.Sharable
@Component
@ConditionalOnProperty(prefix = "jeemodel.unit.idcode", name = "deploy", havingValue = "client")
public class IDCodeClientHandler extends BaseNettyInboundHandler<PongData<ProtoDTO>> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, PongData<ProtoDTO> pongData) throws Exception {
		String rqid = pongData.getEcho();
		final ResponseFuture<PongData<ProtoDTO>> responseFuture = IDCodeClientConstants.RESPONSE_RESULT.get(rqid);
		if (responseFuture != null) {
			IDCodeClientConstants.RESPONSE_RESULT.remove(rqid);

			responseFuture.setProto(pongData);
			//释放获取的信号量
			responseFuture.release();

			//返回释放数据
			if (responseFuture.getInvokeCallback() != null) {
				// 异步请求，执行回调函数
				responseFuture.executeInvokeCallback();
			} else {
				// 同步请求，返回数据并释放CountDown
				responseFuture.putResponse(pongData);
			}
		} else {
			log.warn("[Netty客户端] 接受响应, 但是不符合预期响应, 通道地址 Channel address[{}],pongData={}",NettyUtils.parseRemoteAddr(ctx.channel()), pongData);
		}

	}
}
