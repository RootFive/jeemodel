package com.jeemodel.solution.file.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.jeemodel.core.config.ResourcesConfig;
import com.jeemodel.solution.file.constant.FileConstants;


/**
 * 文件服务静态资源路径配置
 * 
 * @author Rootfive
 */
@Configuration
public class FileResourcesConfig extends ResourcesConfig{
	@Autowired
	private FileConfig fileConfig;
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/** 本地文件上传路径 */
		registry.addResourceHandler(FileConstants.RESOURCE_PREFIX + "/**").addResourceLocations("file:" + fileConfig.getProfile() + "/");
		super.addResourceHandlers(registry);
	}
}
