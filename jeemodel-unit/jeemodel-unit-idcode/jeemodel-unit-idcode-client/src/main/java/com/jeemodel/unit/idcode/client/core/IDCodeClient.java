package com.jeemodel.unit.idcode.client.core;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.jeemodel.bean.rpc.Ping;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.solution.netty.client.invoke.InvokeCallback;
import com.jeemodel.solution.netty.client.invoke.ResponseFuture;
import com.jeemodel.solution.netty.core.BaseNettyClient;
import com.jeemodel.solution.netty.exception.BaseRemoteException;
import com.jeemodel.solution.netty.exception.RemotingConnectException;
import com.jeemodel.solution.netty.exception.RemotingSendRequestException;
import com.jeemodel.solution.netty.exception.RemotingTimeoutException;
import com.jeemodel.solution.netty.exception.RemotingTooMuchRequestException;
import com.jeemodel.solution.netty.utils.NettyUtils;
import com.jeemodel.unit.idcode.client.config.IDCodeClientConfig;
import com.jeemodel.unit.idcode.client.constants.IDCodeClientConstants;
import com.jeemodel.unit.idcode.common.bean.IDCodeDemandDTO;
import com.jeemodel.unit.idcode.common.bean.ProtoDTO;

import io.netty.channel.Channel;
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

	/**
	 * 异步信号量
	 */
	@Resource(name = IDCodeClientConfig.CLIENT_ASYNC_TPS_SEMAPHORE)
	private Semaphore asyncSemaphore;

	/**
	 * 单向信号量
	 */
	@Resource(name = IDCodeClientConfig.CLIENT_ONEWAY_TPS_SEMAPHORE)
	private Semaphore onewaySemaphore;

	
	
	@Override
	public void init() {
		bootstrap
				//设置工作线程组
				.group(workerGroup)
				//通道实现 NIO类型
				.channel(NioSocketChannel.class)
				// SocketChannel 5s内未建立连接就抛出异常
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
				// 通过NoDelay禁用Nagle,使消息立即发出去，不用等待到一定的数据量才发出去
				.option(ChannelOption.TCP_NODELAY, true)
				// 设置保持活动连接状态
				.option(ChannelOption.SO_KEEPALIVE, true)
				/*
				 * handler这里创建一个通道初始化对象(匿名对象)，给workerGroup的EventLoop对应的管道设置处理器，
				 * 这个程序里边使用的是匿名对象，你也可以单独拿出去，用一个类实现 ChannelInitializer
				 */
				.handler(new IDCodeClientHandlersInitializer(IDCodeClient.this));
	}

	/**
	 * 向远程TCP服务器请求连接
	 */
	public void start() {
		synchronized (bootstrap) {
			channelFuture = bootstrap.connect(clientConfig.getServerHost(), clientConfig.getServerSDKPort());
			//ChannelFuture 添加对象监听
			channelFuture.addListener(getConnectionListener());
		}
	}

	/**
	 * ChannelFuture 对象监听
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


	/**
	 * 同步调用
	 */
	public ProtoDTO invokeSync(Ping<IDCodeDemandDTO> ping, long timeoutMillis) throws BaseRemoteException {
		final Channel channel = channel();

		if (channel.isActive()) {
			String echo = ping.getEcho();
			try {
				final ResponseFuture<ProtoDTO> responseFuture = new ResponseFuture<>(echo, timeoutMillis, null, null);

				IDCodeClientConstants.ASYNC_RESPONSE.put(echo, responseFuture);

				channel.writeAndFlush(ping).addListener(new ChannelFutureListener() {
					@Override
					public void operationComplete(ChannelFuture channelFuture) throws Exception {
						if (channelFuture.isSuccess()) {
							// 发送成功后立即跳出
							responseFuture.setIsSendStateOk(true);
							return;
						}
						// 代码执行到此说明发送失败，需要释放资源
						IDCodeClientConstants.ASYNC_RESPONSE.remove(echo);
						responseFuture.putResponse(null);
						responseFuture.setCause(channelFuture.cause());
						log.warn("[Netty客户端] 发送消息失败[{}] ", channel.remoteAddress());
					}
				});
				// 阻塞等待响应
				ProtoDTO resultProto = responseFuture.waitResponse(timeoutMillis);
				if (null == resultProto) {
					if (responseFuture.isSendStateOk()) {
						throw new RemotingTimeoutException(NettyUtils.parseRemoteAddr(channel), timeoutMillis,
								responseFuture.getCause());
					} else {
						throw new RemotingSendRequestException(responseFuture.getCause(),
								NettyUtils.parseRemoteAddr(channel));

					}
				}
				return resultProto;
			} catch (Exception e) {
				log.error("[Netty客户端] 同步请求失败 [{}] ", channel.remoteAddress(), e);
				throw new RemotingSendRequestException(e, NettyUtils.parseRemoteAddr(channel));
			} finally {
				IDCodeClientConstants.ASYNC_RESPONSE.remove(echo);
			}
		} else {
			NettyUtils.closeChannel(channel);
			throw new RemotingConnectException(NettyUtils.parseRemoteAddr(channel));
		}
	}

	/**
	 * 异步调用
	 * @param sdkProtoDTO
	 * @param timeoutMillis
	 * @param invokeCallback
	 * @throws BaseRemoteException
	 * @throws InterruptedException
	 */
	public void invokeAsync(Ping<IDCodeDemandDTO> ping, long timeoutMillis, final InvokeCallback<ProtoDTO> invokeCallback)
			throws BaseRemoteException, InterruptedException {

		final Channel channel = channel();
		if (channel.isOpen() && channel.isActive()) {
			String echo = ping.getEcho();
			boolean acquired = asyncSemaphore.tryAcquire(timeoutMillis, TimeUnit.MILLISECONDS);
			if (acquired) {
				final ResponseFuture<ProtoDTO> responseFuture = new ResponseFuture<>(echo, timeoutMillis,
						invokeCallback, asyncSemaphore);
				IDCodeClientConstants.ASYNC_RESPONSE.put(echo, responseFuture);
				try {
					channel.writeAndFlush(ping).addListener(new ChannelFutureListener() {
						@Override
						public void operationComplete(ChannelFuture channelFuture) throws Exception {
							if (channelFuture.isSuccess()) {
								responseFuture.setIsSendStateOk(true);
								return;
							}
							// 代码执行到些说明发送失败，需要释放资源
							IDCodeClientConstants.ASYNC_RESPONSE.remove(echo);
							responseFuture.setCause(channelFuture.cause());
							responseFuture.putResponse(null);

							try {
								responseFuture.executeInvokeCallback();
							} catch (Exception e) {
								log.error("[Netty客户端] 异步 writeAndFlush addListener 出现异常 [{}] ", channel.remoteAddress(),
										e);
							} finally {
								responseFuture.release();
							}
							log.error("[Netty客户端] 异步发送请求失败 [{}] ", channel.remoteAddress(), channelFuture.cause());
						}
					});
				} catch (Exception e) {
					responseFuture.release();
					log.error("[Netty客户端] 异步发送请求出现异常 [{}] ", channel.remoteAddress(), e);
					throw new RemotingSendRequestException(e, NettyUtils.parseRemoteAddr(channel));
				}
			} else {
				String info = StringUtils.format("尝试获得令牌超过{}ms,等待线程数：{}，可用令牌剩余数：{} ", timeoutMillis,
						asyncSemaphore.getQueueLength(), asyncSemaphore.availablePermits());
				throw new RemotingTooMuchRequestException(info);
			}
		} else {
			NettyUtils.closeChannel(channel);
			throw new RemotingConnectException(NettyUtils.parseRemoteAddr(channel));
		}
	}

	/**
	 * OneWay调用
	 * @param ping
	 * @param timeoutMillis
	 * @throws BaseRemoteException
	 * @throws InterruptedException
	 */
	public void invokeOneWay(Ping<IDCodeDemandDTO> ping, long timeoutMillis) throws BaseRemoteException, InterruptedException {
		final Channel channel = channel();
		if (channel.isActive()) {
			String echo = ping.getEcho();
			boolean acquired = onewaySemaphore.tryAcquire(timeoutMillis, TimeUnit.MILLISECONDS);
			if (acquired) {
				try {
					channel.writeAndFlush(ping).addListener(new ChannelFutureListener() {
						@Override
						public void operationComplete(ChannelFuture channelFuture) throws Exception {
							onewaySemaphore.release();
							if (!channelFuture.isSuccess()) {
								log.warn("[Netty客户端] [OneWay]发送请求失败 [{}] ", channel.remoteAddress());
							}
						}
					});
				} catch (Exception e) {
					log.warn("[Netty客户端] [OneWay]发送请求出现异常 [{}] ", channel.remoteAddress(), e);
					log.warn("send a request to channel <" + NettyUtils.parseRemoteAddr(channel) + "> Exception");
					throw new RemotingSendRequestException(e, NettyUtils.parseRemoteAddr(channel));
				} finally {
					IDCodeClientConstants.ASYNC_RESPONSE.remove(echo);
				}
			} else {
				String info = StringUtils.format("尝试获得令牌超过{}ms,等待线程数：{}，可用令牌剩余数：{} ", timeoutMillis,
						onewaySemaphore.getQueueLength(), onewaySemaphore.availablePermits());
				log.warn(info);
				throw new RemotingTooMuchRequestException(info);
			}
		} else {
			NettyUtils.closeChannel(channel);
			throw new RemotingConnectException(NettyUtils.parseRemoteAddr(channel));
		}
	}

}