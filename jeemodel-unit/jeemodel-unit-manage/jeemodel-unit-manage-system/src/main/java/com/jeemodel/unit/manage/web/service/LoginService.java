package com.jeemodel.unit.manage.web.service;

import java.time.LocalDateTime;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.jeemodel.base.annotation.HelpService;
import com.jeemodel.bean.enums.code.sub.IBaseSubCode;
import com.jeemodel.bean.exception.type.CheckException;
import com.jeemodel.core.utils.ExceptionUtils;
import com.jeemodel.core.utils.MessageUtils;
import com.jeemodel.core.utils.ServletUtils;
import com.jeemodel.core.utils.ip.IpUtils;
import com.jeemodel.solution.captcha.enums.CaptchaSubCode;
import com.jeemodel.unit.manage.core.bean.entity.User;
import com.jeemodel.unit.manage.core.bean.model.LoginUser;
import com.jeemodel.unit.manage.core.constant.ManageConstants;
import com.jeemodel.unit.manage.core.enums.ManageSubCode;
import com.jeemodel.unit.manage.core.web.service.TokenService;
import com.jeemodel.unit.manage.helper.LoginCaptchaHelper;
import com.jeemodel.unit.manage.manager.AsyncManager;
import com.jeemodel.unit.manage.manager.factory.AsyncFactory;
import com.jeemodel.unit.manage.service.IConfigService;
import com.jeemodel.unit.manage.service.IUserService;

/**
 * 登录校验方法
 * 
 * @author Rootfive
 */
@HelpService
public class LoginService {

	@Autowired
	private TokenService tokenService;

	@Resource
	private AuthenticationManager authenticationManager;

	@Autowired
	private IUserService userService;

	@Autowired
	private IConfigService configService;
	
	@Autowired
	private LoginCaptchaHelper loginCaptchaHelper;


	/**
	 * 登录验证
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @param code     验证码
	 * @param uuid     唯一标识
	 * @return 结果
	 */
	public String login(String username, String password, String code, String uuid) {
		boolean captchaOnOff = configService.selectCaptchaOnOff();
		// 验证码开关
		if (captchaOnOff) {
			validateCaptcha(username, code, uuid);
		}
		// 用户验证
		Authentication authentication = null;
		try {
			// 该方法会去调用 @see com.jeemodel.unit.manage.web.service.UserDetailsServiceImpl#loadUserByUsername
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (Exception e) {
			if (e instanceof BadCredentialsException) {
				String message = MessageUtils.message("manage.user.password.not.match");
				AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, ManageConstants.LOGIN_FAIL,message));
				//用户不存在/密码错误
				throw  ExceptionUtils.customMsg(ManageSubCode.U0201,message);
			} else {
				AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, ManageConstants.LOGIN_FAIL, e.getMessage()));
				throw new CheckException(e.getMessage());
			}
		}
		AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, ManageConstants.LOGIN_SUCCESS,MessageUtils.message("manage.user.login.success")));
		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
		recordLoginInfo(loginUser.getUserId());
		// 生成token
		return tokenService.createToken(loginUser);
	}

	/**
	 * 校验验证码
	 * 
	 * @param username 用户名
	 * @param code     验证码
	 * @param uuid     唯一标识
	 * @return 结果
	 */
	public void validateCaptcha(String username, String code, String uuid) {
		
		
		try {
			loginCaptchaHelper.verifyCaptcha(code, uuid);
		} catch (CheckException e) {
			IBaseSubCode baseSubCode = e.getBaseSubCode();
			String customMsg = null;
			if (baseSubCode == CaptchaSubCode.CU102) {
				//验证码过期
				customMsg = MessageUtils.message("manage.user.jcaptcha.expire");
			}else if (baseSubCode == CaptchaSubCode.CU101 || baseSubCode == CaptchaSubCode.CU103) {
				//验证码错误
				customMsg = MessageUtils.message("manage.user.jcaptcha.error");
			}
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, ManageConstants.LOGIN_FAIL,customMsg));
			e.setCustomMsg(customMsg);
			throw e;
		}
	}

	/**
	 * 记录登录信息
	 *
	 * @param userId 用户ID
	 */
	public void recordLoginInfo(Long userId) {
		User User = new User();
		User.setId(userId);
		User.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
		User.setLoginDate(LocalDateTime.now());
		userService.updateUserProfile(User);
	}
}
