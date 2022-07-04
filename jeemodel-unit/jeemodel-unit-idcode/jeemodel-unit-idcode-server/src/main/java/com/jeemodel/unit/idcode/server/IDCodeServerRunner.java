package com.jeemodel.unit.idcode.server;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 两个服务器进程最好用同一个SnowFlake实例，
 * 部署在分布式环境时，SnowFlake的datacenterId和machineId作为联合键必须全局唯一, 否则多个节点的服务可能产生相同的ID
 */
@Component
@ConditionalOnProperty(prefix = "jeemodel.unit.idcode", name = "deploy", havingValue = "server")
public class IDCodeServerRunner implements CommandLineRunner {

	@Resource
	private HTTPServer hTTPServer;

	@Resource
	private SDKServer sDKServer;

	@Override
	public void run(String... args) throws Exception {
		// 启动HTTP服务器
		hTTPServer.init();
		hTTPServer.start();

		// 启动SDK服务器
		sDKServer.init();
		sDKServer.start();
	}

}
