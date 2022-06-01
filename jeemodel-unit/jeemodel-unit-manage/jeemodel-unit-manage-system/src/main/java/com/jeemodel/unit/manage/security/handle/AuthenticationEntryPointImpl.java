package com.jeemodel.unit.manage.security.handle;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.jeemodel.bean.http.PongUtils;
import com.jeemodel.core.utils.ServletUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.unit.manage.core.enums.ManageSubCode;

/**
 * 认证失败处理类 返回未授权
 * 
 * @author Rootfive
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
	private static final long serialVersionUID = -8970718410437077606L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
			throws IOException {
		String msg = StringUtils.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
		ServletUtils.renderString(response, JSON.toJSONString(PongUtils.diy(ManageSubCode.U0401, msg)));
	}
}
