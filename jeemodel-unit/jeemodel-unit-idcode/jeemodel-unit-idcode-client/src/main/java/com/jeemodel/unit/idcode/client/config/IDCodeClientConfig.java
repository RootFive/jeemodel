package com.jeemodel.unit.idcode.client.config;

import java.util.concurrent.Semaphore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Configuration
@ConditionalOnProperty(prefix = "jeemodel.unit.idcode", name = "deploy", havingValue = "client")
public class IDCodeClientConfig {

	/** 客户端 异步 信号量 请求TPS */
	public static final String CLIENT_ASYNC_TPS_SEMAPHORE = "CLIENT_ASYNC_TPS_SEMAPHORE";

	/** 客户端 单向 信号量 请求TPS */
	public static final String CLIENT_ONEWAY_TPS_SEMAPHORE = "CLIENT_ONEWAY_TPS_SEMAPHORE";

	/**
	 * 客户端链接默认HOST,默认localhost
	 */
	@Value("${jeemodel.unit.idcode.client.connect.server.host:localhost}")
	private String serverHost;

	@Value("${jeemodel.unit.idcode.client.connect.server.http.port:22624}")
	private int serverHTTPPort;
	
	
	@Value("${jeemodel.unit.idcode.client.connect.server.sdk.port:22704}")
	private int serverSDKPort;

	/** 异步请求流量控制，表示每秒处理的并发数 */
	@Value("${jeemodel.unit.idcode.client.connect.server.sdk.async.semaphore.tps:10000}")
	private int asyncSemaphoreTps;

	/** 单向请求流量控制，表示每秒处理的并发数，默认是5W */
	@Value("${jeemodel.unit.idcode.client.connect.server.sdk.oneway.semaphore.tps:10000}")
	private int onewaySemaphoreTps;

	/** 断线重连，初始等待时间,秒，默认是1秒 */
	@Value("${jeemodel.unit.idcode.client.connect.server.sdk.retry.baseSleepSecond:1}")
	private int retryBaseSleepSecond;
	
	/** 断线重连，最长重试等待休眠时间,秒，默认是60秒 */
	@Value("${jeemodel.unit.idcode.client.connect.server.sdk.retry.maxSleepSecond:30}")
	private int retryMaxSleepSecond;
	
	/** 客户端心跳，读超时,秒，默认是3秒 */
	@Value("${jeemodel.unit.idcode.client.connect.server.sdk.retry.idleStateReader:0}")
	private int idleStateReader;
	
	/** 客户端心跳，写超时,秒，默认是0秒 */
	@Value("${jeemodel.unit.idcode.client.connect.server.sdk.retry.idleStateWriter:3}")
	private int idleStateWriter;
	
	/** 客户端心跳，读超时,秒，默认是0秒 */
	@Value("${jeemodel.unit.idcode.client.connect.server.sdk.retry.idleStateAll:0}")
	private int idleStateAll;

	
	// XXX
	// 异步信号量
	// XXX
	@Bean(name = CLIENT_ASYNC_TPS_SEMAPHORE)
	public Semaphore httpSemaphore() {
		log.info("【异步】信号量请求流量控制，表示每秒处理的并发数:{}", asyncSemaphoreTps);
		return new Semaphore(asyncSemaphoreTps);
	}

	// XXX
	// 单向信号量
	// XXX
	@Bean(name = CLIENT_ONEWAY_TPS_SEMAPHORE)
	public Semaphore onewaySemaphore() {
		log.info("【单向】信号量请求流量控制，表示每秒处理的并发数:{}", onewaySemaphoreTps);
		return new Semaphore(onewaySemaphoreTps);
	}
}