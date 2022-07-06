package com.jeemodel.unit.idcode.server;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.jeemodel.bean.enums.code.sub.impl.ErrorCodeEnum;
import com.jeemodel.bean.exception.base.BaseJeeModelException;
import com.jeemodel.bean.rpc.Ping;
import com.jeemodel.bean.rpc.PongData;
import com.jeemodel.bean.rpc.PongUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.solution.netty.core.BaseNettyInboundHandler;
import com.jeemodel.unit.idcode.common.bean.IDCodeDemandDTO;
import com.jeemodel.unit.idcode.common.bean.ProtoDTO;
import com.jeemodel.unit.idcode.config.IDCodeServerConfig;
import com.jeemodel.unit.idcode.server.helper.IDcodeCodeHelper;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 通过雪花算法生成唯一ID，写入Channel返回
 * 
 * @Sharable 注解用来说明ChannelHandler是否可以在多个channel直接共享使用。
 */
@Sharable
@Slf4j
@Component
@ConditionalOnProperty(prefix = "jeemodel.unit.idcode", name = "deploy", havingValue = "server")
public class SDKServerHandler extends BaseNettyInboundHandler<Ping<IDCodeDemandDTO>> {

	@Resource
	private IDcodeCodeHelper idcodeCodeHelper;

	/**
	 * 通过信号量来控制流量
	 */
	@Resource(name = IDCodeServerConfig.SDK_TPS_SEMAPHORE)
	private Semaphore semaphore;

	@Resource
	private IDCodeServerConfig serverConfig;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Ping<IDCodeDemandDTO> channelPingMsg) throws Exception {
		log.debug("channelPingMsg={}", channelPingMsg);
		executeHandler(ctx, channelPingMsg);
	}

	@SuppressWarnings("unchecked")
	protected void executeHandler(ChannelHandlerContext ctx, Ping<IDCodeDemandDTO> channelPingMsg) {

		log.debug("channelPingMsg={}", channelPingMsg);
		if (!(channelPingMsg instanceof Ping)) {
			overHandler(ctx, PongUtils.diy(ErrorCodeEnum.E0206), null);
			return;
		}

		Ping<IDCodeDemandDTO> pingMsg = (Ping<IDCodeDemandDTO>) channelPingMsg;

		String echo = pingMsg.getEcho();

		PongData<ProtoDTO> pong = null;
		// 尝试获得令牌，在超时时间内循环尝试获取，直到尝试获取成功或超时返回，不阻塞线程。
		try {
			if (semaphore.tryAcquire(serverConfig.getSdkTimeoutmillis(), TimeUnit.MILLISECONDS)) {
				try {
					IDCodeDemandDTO demand = pingMsg.getData();
					ProtoDTO proto = idcodeCodeHelper.nextUIDCode(demand);
					pong = PongUtils.okData(proto);
				} catch (Exception e) {
					if (e instanceof BaseJeeModelException) {
						pong = PongUtils.analysisException((BaseJeeModelException) e);
					} else {
						log.error("{}_find_a_Exception:", this.getClass().getSimpleName(), e);
						pong = PongUtils.unknown(e.getMessage());
					}
				}

				// 设置回声echo
				if (StringUtils.isBlank(echo)) {
					echo = idcodeCodeHelper.nextEcho();
				}
				pong.setEcho(echo);
				ctx.channel().writeAndFlush(pong).addListener(new ChannelFutureListener() {
					@Override
					public void operationComplete(ChannelFuture channelFuture) throws Exception {
						// 释放一个令牌，唤醒一个获取令牌不成功的阻塞线程。
						semaphore.release();
					}
				});
				return;
			} else {
				String info = StringUtils.format("等待{}ms，未获得令牌,等待线程数：{}，可用令牌剩余数：{} ",
						serverConfig.getSdkTimeoutmillis(), semaphore.getQueueLength(), semaphore.availablePermits());
				log.warn(info);
				// 没有获取到令牌。请求超过负载
				overHandler(ctx, PongUtils.diy(ErrorCodeEnum.E0102), echo);
				return;

			}
		} catch (InterruptedException e) {
			// 5Thread类的interrupted方法是返回当前线程的被打断状态,同时清除打断状态
			log.error("{}_find_a_interruptedexception：", this.getClass().getSimpleName(), e);
			overHandler(ctx, PongUtils.unknown(), echo);
			return;
		}
	}

	private void overHandler(ChannelHandlerContext ctx, PongData<ProtoDTO> pong, String echo) {
		// 设置回声echo
		if (StringUtils.isBlank(echo)) {
			echo = idcodeCodeHelper.nextEcho();
		}
		pong.setEcho(echo);
		ctx.channel().writeAndFlush(pong);
	}

}
