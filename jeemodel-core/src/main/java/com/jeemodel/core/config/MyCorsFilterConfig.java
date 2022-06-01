package com.jeemodel.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 */
@Configuration
public class MyCorsFilterConfig {

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		// 设置访问源地址
//		config.addAllowedOriginPattern("*"); 高版本Springboot 2.5.6
		config.addAllowedOrigin("*");// 低版本Springboot 2.3.12
		// 设置访问源请求头
		config.addAllowedHeader("*");
		// 设置访问源请求方法
		config.addAllowedMethod("*");
		// 有效期 1800秒
		config.setMaxAge(1800L);
		// 添加映射路径，拦截一切请求
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		// 返回新的CorsFilter
		return new CorsFilter(source);
	}
}
