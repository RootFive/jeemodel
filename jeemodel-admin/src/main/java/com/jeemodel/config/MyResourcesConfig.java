package com.jeemodel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import com.jeemodel.core.config.ResourcesConfig;
import com.jeemodel.core.interceptor.BaseRepeatSubmitStopInterceptor;

/**
 * 通用配置
 */
@Configuration
public class MyResourcesConfig extends ResourcesConfig {
	
	@Autowired
	private BaseRepeatSubmitStopInterceptor baseRepeatSubmitStopInterceptor;

	/**
	 * 自定义拦截规则
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(baseRepeatSubmitStopInterceptor).addPathPatterns("/**");
	}
}