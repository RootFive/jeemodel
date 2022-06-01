package com.jeemodel.unit.manage.aspectj;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 数据过滤上下文
 * 
 * @author Rootfive
 *
 */
@Slf4j
public class DataScopeContext {

	private static final ThreadLocal<Map<String, Object>> DATA_SCOPE_CONTEXT = new ThreadLocal<>();

	public static void set(Map<String, Object> dataScopeParams) {
		log.info("dataScopeParams={}",dataScopeParams);
		DATA_SCOPE_CONTEXT.set(dataScopeParams);
	}

	public static Map<String, Object> get() {
		try {
			Map<String, Object> dataScopeParams = DATA_SCOPE_CONTEXT.get();
			return dataScopeParams;
		}finally {
			// 清除当前线程内引用，防止内存泄漏
			DATA_SCOPE_CONTEXT.remove();
		}
	}
}
