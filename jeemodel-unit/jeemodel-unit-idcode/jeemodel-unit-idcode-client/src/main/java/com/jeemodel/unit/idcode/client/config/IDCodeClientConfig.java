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
	
	
	@Value("${jeemodel.unit.idcode.client.connect.server.sdk.port:22624}")
	private int serverSDKPort;

	/** 异步请求流量控制，表示每秒处理的并发数 */
	@Value("${jeemodel.unit.idcode.client.connect.server.sdk.async.semaphore.tps:10000}")
	private int asyncSemaphoreTps;

	/** 单向请求流量控制，表示每秒处理的并发数，默认是5W */
	@Value("${jeemodel.unit.idcode.client.connect.server.sdk.oneway.semaphore.tps:10000}")
	private int onewaySemaphoreTps;

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