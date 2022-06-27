package com.jeemodel.unit.manage.core.auth;


import java.nio.file.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jeemodel.bean.rpc.PongTable;
import com.jeemodel.bean.rpc.PongUtils;
import com.jeemodel.core.web.exception.GlobalExceptionHandler;
import com.jeemodel.unit.manage.core.enums.ManageSubCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class AuthExceptionHandler extends GlobalExceptionHandler {
	/**
	 * 权限校验异常
	 */
	@ExceptionHandler(AccessDeniedException.class)
	public PongTable<?> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		log.error("请求地址'{}',权限校验失败'{}'", requestURI, e.getMessage());
		return PongUtils.diy(ManageSubCode.U0601, "没有权限，请联系管理员授权");
	}
}
