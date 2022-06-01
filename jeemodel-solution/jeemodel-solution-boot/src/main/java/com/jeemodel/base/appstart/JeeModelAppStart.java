package com.jeemodel.base.appstart;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.util.ObjectUtils;

import com.jeemodel.base.config.ProjecetUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>Title: JeeModelAppStart</p>
 * @author Rootfive 2020-11-30	12:01:42 Contact: QQ群：2236067977  site:www.rootfive.cn
 * <p>Description: 启动程序</p>
 * @since   JeeModel.0.0
 */
@Slf4j
//表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
@Configuration
@ComponentScan(basePackages = { "com.jeemodel" })
public class JeeModelAppStart {

	
	protected static ConfigurableApplicationContext start(boolean isMicro, boolean isHaveDBPool, boolean isHaveApiDoc, Class<?> primarySource, String[] args) {
		log.info("【JeeModelApp】启动开始：自动加载：{}，服务架构：{}，数据库连接池：{}，接口文档：{}", 
				System.getProperty("spring.devtools.restart.enabled", "false"),
				isMicro ? "微服务" : "单一应用", 
				isHaveDBPool ? "有" : "无", 
				isHaveApiDoc ? "有" : "无");

		if (isMicro) {
			// 关闭原生 Swagger注解，防止与discovery配置冲突
			System.setProperty("swagger.service.enabled", "false");
		}
		//启动服务
		ConfigurableApplicationContext application = SpringApplication.run(primarySource, args);
		
		//启动-成功后-获取 服务器环境org.springframework.core.env.Environment
		Environment env = application.getEnvironment();
		
		
		//启动-成功后-打印启动成功日志
		log.info(afterAppStartPrettyPrintLog(isMicro, isHaveDBPool, isHaveApiDoc,primarySource,env));
		return application;
	}

	/**
	 * @param isMicro
	 * @param isHaveDBPool
	 * @param isHaveApiDoc
	 * @param primarySource
	 * @param env
	 * @param useTime
	 * @return
	 */
	private static String afterAppStartPrettyPrintLog(boolean isMicro,boolean isHaveDBPool, boolean isHaveApiDoc,Class<?> primarySource, Environment env) {
		
		//获取服务APP-配置名称
		String serviceName = env.getProperty("spring.application.name");
		
		//获取当前服务的端口
		String hostAddress = null;
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.warn("获取本机IP失败");
			hostAddress = "127.0.0.1"; // TODO 读取本地配置
		}
		String serverPort = StringUtils.isNotBlank(env.getProperty("server.port"))?env.getProperty("server.port"):"8080";
		
		String serverServletContextPath = env.getProperty("server.servlet.context-path", "/");
		
		
		//获取当前服务-名称
		String appName= StringUtils.isNotBlank(serviceName)?serviceName:ProjecetUtils.title(primarySource);
		//获取当前服务-版本
		String appVersion= ProjecetUtils.version(primarySource);
		//获取当前服务-使用的JeeModel版本
		String jeeModelVersion= ProjecetUtils.version(JeeModelAppStart.class);
		
//		printBanner(jeeModelVersion, env);
		
		StringBuilder sb = new StringBuilder();
		sb.append("\n▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n\n\t");
		sb.append("               __          __  ___          __     __\n\t");
		sb.append("              / /__  ___  /  |/  /___  ____/ /__  / /\n\t");
		sb.append("         __  / / _ \\/ _ \\/ /|_/ / __ \\/ __  / _ \\/ /\n\t");
		sb.append("        / /_/ /  __/  __/ /  / / /_/ / /_/ /  __/ /\n\t");
		sb.append("        \\____/\\___/\\___/_/  /_/\\____/\\__,_/\\___/_/ \n\t");
		if (StringUtils.isNotBlank(jeeModelVersion)) {
			sb.append("                                                    JeeModel版本："+jeeModelVersion+"\n\t");
		}
		sb.append("\n░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n\n\t");
		
		sb.append("服务名称：[").append(appName).append("]，");
		if (StringUtils.isNotBlank(appVersion)) {
			sb.append("版本：[").append(appVersion).append("]，");
		}
		sb.append("启动已完成，常用地址如下:\t");
		sb.append("\n--------------------------------------------------------------------------------------\n\n\t");
		StringBuilder baseURL = getBaseURL(hostAddress, serverPort, serverServletContextPath);
		
		sb.append("服务URL：  	").append(baseURL).append("\n\t");
		sb.append("监控检查地址：	").append(baseURL).append("/actuator/health").append("\n\n\t");
		if (isHaveDBPool) {
			sb.append("数据源连接池：	").append(baseURL).append("/druid").append("\n\n\t");
		}
		if (isHaveApiDoc) {
			sb.append("接口文档一： 	").append(baseURL).append("/doc.html").append("\n\t");
			sb.append("接口文档二： 	").append(baseURL).append("/swagger-ui/index.html").append("\n\t");
			
		}
		
		sb.append("\n▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n");
		return sb.toString();
	}
	
	
	private static StringBuilder getBaseURL(String hostAddress, String serverPort, String contextPath) {
		String basePath = removeSlash(contextPath);
		return new StringBuilder().append("http://").append(hostAddress).append(":").append(serverPort).append(basePath);
	}

	/**
	 * 去除斜杠
	 * @param contextPath
	 * @return
	 */
	private static String removeSlash(String contextPath) {

		String basePath = "";

		if (!ObjectUtils.isEmpty(contextPath)) {
			int len = contextPath.length();
			String[] value = new String[len];
			boolean isOverRemoveStarts = false;
			for (int i = 0; i < len; i++) {
				char c = contextPath.charAt(i);

				if (c == ' ') {
					value[i] = "";
					continue;
				}

				if (!isOverRemoveStarts && (c == '/' || c == '\\')) {
					value[i] = "";
				} else {
					isOverRemoveStarts = true;
					value[i] = String.valueOf(c);
				}
			}

			boolean isOverRemoveEnds = false;
			for (int i = len - 1; i > 0; i--) {

				if (value[i].equals(" ")) {
					value[i] = "";
					continue;
				}

				if (!isOverRemoveEnds && (value[i].equals("/") || value[i].equals("\\"))) {
					value[i] = "";
				} else {
					isOverRemoveEnds = true;
				}
			}

			StringBuilder stringBuilder = new StringBuilder(len);
			for (String str : value) {
				stringBuilder.append(str);
			}

			basePath = stringBuilder.toString();
		}

		if (!basePath.equals("")) {
			basePath = "/" + basePath;

		}
		return basePath;
	}
	

}
