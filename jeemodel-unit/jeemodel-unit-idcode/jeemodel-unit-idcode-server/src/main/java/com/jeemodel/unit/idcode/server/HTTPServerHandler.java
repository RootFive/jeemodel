package com.jeemodel.unit.idcode.server;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.jeemodel.bean.enums.code.sub.impl.ErrorCodeEnum;
import com.jeemodel.bean.rpc.PongData;
import com.jeemodel.bean.rpc.PongUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.core.utils.http.HttpUtils;
import com.jeemodel.solution.netty.core.BaseNettyInboundHandler;
import com.jeemodel.unit.idcode.common.bean.IDCodeDemandDTO;
import com.jeemodel.unit.idcode.common.bean.ProtoDTO;
import com.jeemodel.unit.idcode.config.IDCodeServerConfig;
import com.jeemodel.unit.idcode.server.helper.IDcodeCodeHelper;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义的处理器，目前支持三种请求： getTime: 获取服务器当前时间； clientInfo: 获取请求客户端的User-Agent信息 其它：
 * 返回404状态，并且提示404信息
 * 
 * @Sharable 注解用来说明ChannelHandler是否可以在多个channel直接共享使用。
 */
@Slf4j
@ChannelHandler.Sharable
@Component
@ConditionalOnProperty(prefix = "jeemodel.unit.idcode", name = "deploy", havingValue = "server")
public class HTTPServerHandler extends BaseNettyInboundHandler<FullHttpRequest> {

	@Resource
	private IDcodeCodeHelper idcodeCodeHelper;

	@Resource
	private IDCodeServerConfig serverConfig;

	/**
	 * 通过信号量来控制流量
	 */
	@Resource(name = IDCodeServerConfig.HTTP_TPS_SEMAPHORE)
	private Semaphore semaphore;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {
		log.debug("[Netty客户端] 请求的 fullHttpRequest: {}", JSON.toJSONString(fullHttpRequest));
		executeHandler(ctx, fullHttpRequest);
	}

	@SuppressWarnings("unchecked")
	private void executeHandler(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) {

		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		// 请求地址+请求参数
		String uriWithQueryString = fullHttpRequest.uri();

		if (StringUtils.startsWith(uriWithQueryString, "/")) {
			// 请求地址 如若是
			uriWithQueryString = uriWithQueryString.substring(1);
		}

		// 请求地址为空，直接返回异常
		if (StringUtils.isBlank(uriWithQueryString)) {
			overHandler(ctx, response, PongUtils.diy(ErrorCodeEnum.E0201), null);
			return;
		}

		// 分离URI和QueryString
		String[] httpReq = StringUtils.split(uriWithQueryString, '?');

		// 真实URI
		String realUri = httpReq[0];

		// 查询参数
		JSONObject queryJSON = null;
		if (httpReq.length > 1) {
			// URL问号"?"后面的参数
			queryJSON = HttpUtils.getQueryParamToJSON(httpReq[1], false);
		}

		// 请求方法
		HttpMethod method = fullHttpRequest.method();
		if (method != HttpMethod.GET) {
			// XXX
			// XXX
			// 非Get参数。还需要获取请求体中的参数
			String requestBodyString = fullHttpRequest.content().toString(CharsetUtil.UTF_8);
			log.debug("requestBodyString={}", requestBodyString);
			if (StringUtils.isNotBlank(requestBodyString)) {
				try {
					JSONObject requestBodyJSON = JSON.parseObject(requestBodyString);
					if (queryJSON == null) {
						queryJSON = requestBodyJSON;
					} else {
						if (requestBodyJSON != null) {
							queryJSON.putAll(requestBodyJSON);
						}
					}
				} catch (Exception e) {
					log.debug("{}反序列化requestBody，find_a_Exception:", this.getClass().getSimpleName(), e);
				}
			}
		}

		// 请求参数为空，直接结束
		if (queryJSON == null || queryJSON.isEmpty()) {
			overHandler(ctx, response, PongUtils.diy(ErrorCodeEnum.E0207), null);
			return;
		}

		String echo = queryJSON.getString("echo");
		// 真实URI和服务URI不一致，直接结束
		if (!StringUtils.equalsIgnoreCase(serverConfig.getHttpHequestURI(), realUri)) {
			// XXX
			// 目前只提供唯一标识服务
			// XXX
			overHandler(ctx, response, PongUtils.diy(ErrorCodeEnum.E0201), echo);
			return;
		}

		IDCodeDemandDTO demandDTO = queryJSON.to(IDCodeDemandDTO.class);

		try {
			// 尝试获得令牌，在超时时间内循环尝试获取1个许可，直到尝试获取成功或超时返回，不阻塞线程。
			if (semaphore.tryAcquire(serverConfig.getHttpTimeoutmillis(), TimeUnit.MILLISECONDS)) {
				PongData<ProtoDTO> pong = null;
				try {
					ProtoDTO proto = idcodeCodeHelper.nextUIDCode(demandDTO);
					pong = PongUtils.okData(proto);
				} catch (Exception e) {
					log.error("{}_find_a_Exception:", this.getClass().getSimpleName(), e);
					pong = PongUtils.unknown();
					return;
				}
				overHandler(ctx, response, pong, echo);
				semaphore.release();
				return;
			} else {
				String info = StringUtils.format("尝试获得令牌超过{}ms,等待线程数：{}，可用令牌剩余数：{} ",
						serverConfig.getSdkTimeoutmillis(), this.semaphore.getQueueLength(),
						this.semaphore.availablePermits());
				log.warn(info);
				// 没有获取到令牌。请求超过负载
				overHandler(ctx, response, PongUtils.diy(ErrorCodeEnum.E0102), echo);
				return;
			}
		} catch (InterruptedException e) {
			// 5Thread类的interrupted方法是返回当前线程的被打断状态,同时清除打断状态
			log.error("{}_find_a_interruptedexception：", this.getClass().getSimpleName(), e);
			overHandler(ctx, response, PongUtils.unknown(), echo);
			return;
		}
	}

	private void overHandler(ChannelHandlerContext ctx, FullHttpResponse response, PongData<ProtoDTO> pong,
			String echo) {
		// 设置回声echo
		if (StringUtils.isBlank(echo)) {
			echo = idcodeCodeHelper.nextEcho();
		}
		pong.setEcho(echo);

		// 序列化之后 writeAndFlush
		byte[] respBytes = JSON.toJSONString(pong).getBytes();
		response.content().writeBytes(respBytes);
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
}
