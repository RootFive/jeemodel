package com.jeemodel.unit.coding.config;

import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.core.utils.spring.SpringUtils;

/**
 * 读取代码生成相关配置
 */
public class GenConfigHelper {
	
	/** 作者 */
	public static String getAuthor() {
		return SpringUtils.getBean(GenConfig.class).getAuthor();
	}

	/** 生成包路径 */
	public static String getPackageName() {
		return SpringUtils.getBean(GenConfig.class).getPackageName();
	}

	/** 自动去除表前缀，默认是false */
	public static boolean getAutoRemovePre() {
		return SpringUtils.getBean(GenConfig.class).isAutoRemovePre();
	}
	
	/** 表前缀(类名不会包含表前缀) */
	public static String getTablePrefix() {
		return SpringUtils.getBean(GenConfig.class).getTablePrefix();
	}
	/** 默认上级菜单，默认研发工具 */
	public static String getDefaultParentMenuName() {
		String defaultParentMenuName = SpringUtils.getBean(GenConfig.class).getDefaultParentMenuName();
		if (StringUtils.isNotBlank(defaultParentMenuName)) {
			return StringUtils.trimToEmpty(defaultParentMenuName);
		}
		return "研发工具";
	}

	
}
