package com.jeemodel.unit.idcode.config;

import java.util.concurrent.Semaphore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jeemodel.core.utils.id.SnowFlakeHelper;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "jeemodel.unit.idcode", name = "deploy", havingValue = "server")
public class IDCodeServerConfig {

	/** HTTP信号量 请求TPS */
	public static final String HTTP_TPS_SEMAPHORE = "HTTP_TPS_SEMAPHORE";

	/** SDK信号量 请求TPS */
	public static final String SDK_TPS_SEMAPHORE = "SDK_TPS_SEMAPHORE";

	/** SnowFlake 回声 */
	public static final String ECHO_SNOWFLAKE = "ECHO_SNOWFLAKE";

	/** SnowFlake 统一标识编码 */
	public static final String IDCODE_SNOWFLAKE = "IDCode_SNOWFLAKE";

	/**
	 * 数据中心的标识ID，取值范围：0~31 机器或进程的标识ID，取值范围：0~31 两个标识ID组合在分布式环境中必须唯一
	 */
	@Value("${jeemodel.unit.idcode.server.datacenterId:0}")
	private int datacenterId;

	@Value("${jeemodel.unit.idcode.server.machineId:1}")
	private int machineId;

	// XXX

	/** HTTP协议请求端口 */
	@Value("${jeemodel.unit.idcode.server.http.server.port:22624}")
	private int httpServerPort;

	/** HTTP协议请求URI */
	@Value("${jeemodel.unit.idcode.server.http.server.request.uri:uidcode}")
	private String httpHequestURI;

	/** HTTP流量控制，表示每秒处理的并发数 */
	@Value("${jeemodel.unit.idcode.server.http.server.semaphore.tps:10000}")
	private int httpSemaphoreTps;

	/** HTTP流量控制超时时间,即在指定的时间内尝试地获取1个许可，获取不到就释放，默认是2秒 */
	@Value("${jeemodel.unit.idcode.server.http.server.semaphore.acquire.timeoutmillis:3000}")
	private int httpTimeoutmillis;

	/** HTTP线程队列得到连接个数 */
	@Value("${jeemodel.unit.idcode.server.http.server.backlog.size:1024}")
	private int httpBacklogSize;

	// XXX

	/** SDK协议请求端口 */
	@Value("${jeemodel.unit.idcode.server.sdk.server.port:22724}")
	private int sdkServerPort;

	/** SDK流量控制，表示每秒处理的并发数，默认是5W */
	@Value("${jeemodel.unit.idcode.server.sdk.server.semaphore.tps:50000}")
	private int sdkSemaphoreTps;

	/** SDK流量控制超时时间,即在指定的时间内尝试地获取1个许可，获取不到就释放，默认是3秒 */
	@Value("${jeemodel.unit.idcode.server.sdk.server.semaphore.acquire.timeoutmillis:3000}")
	private int sdkTimeoutmillis;

	/** SDK线程队列得到连接个数 */
	@Value("${jeemodel.unit.idcode.server.sdk.server.backlog.size:1024}")
	private int sdkBacklogSize;

	/** 60秒内没有收到来自客户端的任何数据包 */
	@Value("${jeemodel.unit.idcode.server.sdk.server.idle.state.reader:60}")
	private int readerIdleTimeSeconds;

	/** 30秒内没有向客户端的发送写数据包 */
	@Value("${jeemodel.unit.idcode.server.sdk.server.idle.state.writer:0}")
	private int writerIdleTimeSeconds;

	/** SDK线程队列得到连接个数 */
	@Value("${jeemodel.unit.idcode.server.sdk.server.idle.state.all:0}")
	private int allIdleTimeSeconds;

	/** 服务端是否需要回复客户端的心跳 */
	@Value("${jeemodel.unit.idcode.server.sdk.server.heartbeat.echo:false}")
	private boolean needHeartBeatEcho;

	// XXX
	// Bean
	// XXX

	@Bean(name = ECHO_SNOWFLAKE)
	public SnowFlakeHelper echoSnowFlake() {
		log.info("【回声echo】服务-雪花算法参数使用：[数据节点标识ID:{}],[机器标识ID:{}]", datacenterId, machineId);
		return new SnowFlakeHelper(0, 0);
	}

	@Bean(name = IDCODE_SNOWFLAKE)
	public SnowFlakeHelper uniqueidSnowFlake() {
		log.info("【唯一ID】服务-雪花算法参数使用：[数据节点标识ID:{}],[机器标识ID:{}]", datacenterId, machineId);
		return new SnowFlakeHelper(datacenterId, machineId);
	}

	// XXX
	// HTTP Server
	// XXX

	@Bean(name = HTTP_TPS_SEMAPHORE)
	public Semaphore httpSemaphore() {
		log.info("HTTP流量控制，表示每秒处理的并发数:{}", httpSemaphoreTps);
		return new Semaphore(httpSemaphoreTps);
	}

	// XXX
	// SDK Server
	// XXX
	@Bean(name = SDK_TPS_SEMAPHORE)
	public Semaphore sdksemaphore() {
		log.info("SDK流量控制，表示每秒处理的并发数:{}", sdkSemaphoreTps);
		return new Semaphore(sdkSemaphoreTps);
	}

}
