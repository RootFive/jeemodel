package com.jeemodel.solution.file.config;

import com.jeemodel.core.utils.spring.SpringUtils;

public class FileConfigHelper   {
	
	
	public static final String getDefaultBaseDir() {
		FileConfig bean = SpringUtils.getBean(FileConfig.class);
		return bean.getProfile();
	}
	
	/**
	 * 获取上传路径
	 */
	public static String getUploadPath() {
		return getDefaultBaseDir() + "/upload";
	}
	
	/**
	 * 获取下载路径
	 */
	public static String getDownloadPath() {
		return getDefaultBaseDir() + "/download/";
	}

	/**
	 * 获取导入上传路径
	 */
	public static String getImportPath() {
		return getDefaultBaseDir() + "/import";
	}

}