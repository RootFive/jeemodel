package com.jeemodel.unit.manage.system.show;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jeemodel.core.utils.AspectUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.unit.manage.service.IMenuService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 	演示模式切点管理
 * 
 * @author Rootfive
 */
@Order(100)
@Aspect
@Component
@Slf4j
public class ShowDemoModeAspect {

	@Autowired
	private Environment env;

	@Autowired
	private IMenuService menuService;

	private static final String DEMOMODE_PERMS_PREFIX = "jeemodel.unit.manage.show.";


	@Before("@annotation(controllerPreAuthorize)")
	public void begin(JoinPoint joinPoint,PreAuthorize controllerPreAuthorize) throws Exception {
		// 获得注解
//		PreAuthorize preAuthorize = AspectUtils.getAnnotation(joinPoint, PreAuthorize.class);
		//注解preAuthorize 不存在，直接返回
		if (controllerPreAuthorize == null ) {
			throw new DemoException();
		}
		
		//获取权限字符串
		String permsString = PermsAOPUtils.getPermsString(controllerPreAuthorize);
		Perms perms = PermsAOPUtils.getPerms(permsString);
		log.debug("perms={}", perms.toString());

		//组装权限配置Key
		String demoPermsKey = new StringBuffer(DEMOMODE_PERMS_PREFIX).append(perms.getModule()).append(".")
				.append(perms.getBusiness()).append(".").append(perms.getFunName()).toString();
		String demoPermsKeyValue = env.getProperty(demoPermsKey);

		// 演示功能是否允许操作
		if (StringUtils.equalsIgnoreCase("true", demoPermsKeyValue)) {
			return;
		}
		//尝试获取方法描述
		String methodDesc = getMethodDesc(permsString, joinPoint);
		if (StringUtils.isNotBlank(methodDesc)) {
			throw new DemoException(methodDesc);
		} else {
			throw new DemoException();
		}
	}

	
	/**
	 * @param permsString
	 * @param joinPoint
	 * @return 获取方法描述
	 * @throws Exception
	 */
	private String getMethodDesc(String permsString, JoinPoint joinPoint) throws Exception {
		//获取菜单或者按钮名称
		String methodDesc = menuService.getMenuNameByPerms(permsString);
		
		//菜单或者按钮名称 存在，直接返回
		if (StringUtils.isNotBlank(methodDesc)) {
			return methodDesc;
		} 
		
		//获取API描述
		ApiOperation userOperLog = AspectUtils.getAnnotation(joinPoint, ApiOperation.class);
		if (userOperLog == null) {
			return null;
		}
		return userOperLog.value();

	}

}
