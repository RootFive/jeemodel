package com.jeemodel.unit.manage.system.show;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jeemodel.bean.rpc.PongTable;
import com.jeemodel.bean.rpc.PongUtils;
import com.jeemodel.unit.manage.core.auth.AuthExceptionHandler;

/**
 * @author Rootfive 2021-3-1	 联系方式: QQ群：2236067977  
 * <p>Spring Security权限认证全局异常处理器</p>
 * <blockquote>
 * 	<pre>
 *     AccessDeniedException 1、用户未登录情况下访问受保护资源 2、用户在登录情况下访问受保护资源
 *     BadCredentialsException 	坏的凭据
 *     UsernameNotFoundException 用户找不到
 *     AccountStatusException 	用户状态异常它包含如下子类
 *     		1、AccountExpiredException 	账户过期
 *     		2、LockedException 		账户锁定
 *     		3、DisabledException 	账户不可用
 *     		4、CredentialsExpiredException 证书过期
 * 	</pre>
 * </blockquote>
 */
@RestControllerAdvice
public class DemoExceptionHandler  extends AuthExceptionHandler{
	
	/**
	 * 演示模式异常
	 */
	@ExceptionHandler(DemoException.class)
	public PongTable<?> demoModeException(DemoException e) {
		return PongUtils.fail(e.getMessage());
	}



}
