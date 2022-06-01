package com.jeemodel.core.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.fastjson2.JSON;
import com.jeemodel.bean.enums.code.sub.impl.FAILCodeEnum;
import com.jeemodel.bean.http.PongTable;
import com.jeemodel.bean.http.PongUtils;
import com.jeemodel.core.annotation.SubmitLimit;
import com.jeemodel.core.annotation.SubmitLimiter;
import com.jeemodel.core.utils.ServletUtils;
import com.jeemodel.core.utils.spring.SpringUtils;

/**
 * 提交拦截器
 *
 * @author Rootfive
 */
@SubmitLimiter
public abstract class BaseRepeatSubmitStopInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
			
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			SubmitLimit submitLimit = method.getAnnotation(SubmitLimit.class);
			if (submitLimit != null) {
				return submitStop(request, response, submitLimit);
			}
		}
		
		return true;
	}


	/**
	 * 提交阻止
	 * @param request
	 * @param response
	 * @param submitLimit
	 * @return
	 */
	private boolean submitStop(HttpServletRequest request, HttpServletResponse response, SubmitLimit submitLimit) {
		Class<? extends BaseRepeatSubmitStopInterceptor> submitStopClass = submitLimit.submitStopClass();
		BaseRepeatSubmitStopInterceptor submitStopInterceptor = SpringUtils.getBean(submitStopClass);
		if (submitStopInterceptor.isNeedSubmit(request, submitLimit)) {
			PongTable<?> ajaxResult = PongUtils.diy(FAILCodeEnum.F0101, submitLimit.message());
			ServletUtils.renderString(response, JSON.toJSONString(ajaxResult));
			return false;
		}
		return true;
	}

	
	/**
	 * 验证是否重复提交由子类实现具体的防重复提交的规则
	 * @param request
	 * @param annotation
	 * @return
	 */
	public abstract boolean isNeedSubmit(HttpServletRequest request, SubmitLimit annotation);
}
