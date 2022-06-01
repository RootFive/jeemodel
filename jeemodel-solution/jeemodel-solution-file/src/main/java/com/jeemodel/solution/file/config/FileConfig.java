package com.jeemodel.solution.file.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jeemodel.solution.file")
public class FileConfig   {
	
	/** 上传路径 */
	private  String profile;
	
}