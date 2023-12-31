package com.jeemodel.unit.manage.security.handle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.alibaba.fastjson2.JSON;
import com.jeemodel.bean.rpc.PongUtils;
import com.jeemodel.core.utils.BlankUtils;
import com.jeemodel.core.utils.ServletUtils;
import com.jeemodel.unit.manage.core.bean.model.LoginUser;
import com.jeemodel.unit.manage.core.constant.ManageConstants;
import com.jeemodel.unit.manage.core.web.service.TokenService;
import com.jeemodel.unit.manage.manager.AsyncManager;
import com.jeemodel.unit.manage.manager.factory.AsyncFactory;

/**
 * 自定义退出处理类 返回成功
 * 
 * @author Rootfive
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

	@Autowired
	private TokenService tokenService;

	/**
	 * 退出处理
	 * 
	 * @return
	 */
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		LoginUser loginUser = tokenService.getLoginUser(request);
		if (BlankUtils.isNotNull(loginUser)) {
			String userName = loginUser.getUsername();
			// 删除用户缓存记录
			tokenService.delLoginUser(loginUser.getToken());
			// 记录用户退出日志
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, ManageConstants.LOGOUT, "退出成功"));
		}
//		ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(HttpStatus.SUCCESS, "退出成功")));
		ServletUtils.renderString(response, JSON.toJSONString(PongUtils.okData("退出成功")));
	}
}
