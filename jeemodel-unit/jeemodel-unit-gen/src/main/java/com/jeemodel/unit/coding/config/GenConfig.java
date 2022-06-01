package com.jeemodel.unit.coding.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * 读取代码生成相关配置
 */

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jeemodel.unit.gen.coding")
public class GenConfig {
	/** 作者 */
	public  String author;

	/** 生成包路径 */
	public  String packageName;

	/** 自动去除表前缀，默认是false */
	public  boolean autoRemovePre;

	/** 表前缀(类名不会包含表前缀) */
	public  String tablePrefix;
	
	/** 默认上级菜单，研发工具 */
	public  String defaultParentMenuName;
}
