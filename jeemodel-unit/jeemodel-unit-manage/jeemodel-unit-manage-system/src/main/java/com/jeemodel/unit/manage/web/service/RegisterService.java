package com.jeemodel.unit.manage.web.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeemodel.base.annotation.HelpService;
import com.jeemodel.core.utils.MessageUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.unit.manage.bean.dto.user.UserSave;
import com.jeemodel.unit.manage.core.bean.model.RegisterBody;
import com.jeemodel.unit.manage.core.constant.ManageConstants;
import com.jeemodel.unit.manage.core.constant.UserConstants;
import com.jeemodel.unit.manage.core.utils.SecurityUtils;
import com.jeemodel.unit.manage.helper.LoginCaptchaHelper;
import com.jeemodel.unit.manage.manager.AsyncManager;
import com.jeemodel.unit.manage.manager.factory.AsyncFactory;
import com.jeemodel.unit.manage.service.IConfigService;
import com.jeemodel.unit.manage.service.IUserService;

/**
 * 注册校验方法
 * 
 * @author Rootfive
 */
@HelpService
public class RegisterService {
	
	@Autowired
	private IUserService userService;

	@Autowired
	private IConfigService configService;
	
	@Autowired
	private LoginCaptchaHelper loginCaptchaHelper;

	
	
	/**
	 * 注册
	 */
	public String register(RegisterBody registerBody) {
		String msg = "", username = registerBody.getUsername(), password = registerBody.getPassword();

		boolean captchaOnOff = configService.selectCaptchaOnOff();
		// 验证码开关
		if (captchaOnOff) {
			loginCaptchaHelper.verifyCaptcha(registerBody.getCode(), registerBody.getUuid());
		}

		if (StringUtils.isEmpty(username)) {
			msg = "用户名不能为空";
		} else if (StringUtils.isEmpty(password)) {
			msg = "用户密码不能为空";
		} else if (username.length() < UserConstants.USERNAME_MIN_LENGTH
				|| username.length() > UserConstants.USERNAME_MAX_LENGTH) {
			msg = "账户长度必须在2到20个字符之间";
		} else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
				|| password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
			msg = "密码长度必须在5到20个字符之间";
		} else if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(username))) {
			msg = "保存用户'" + username + "'失败，注册账号已存在";
		} else {
			UserSave userSave = new UserSave();
			userSave.setUserName(username);
			userSave.setNickName(username);
			userSave.setPassword(SecurityUtils.encryptPassword(registerBody.getPassword()));
			boolean regFlag = userService.registerUser(userSave);
			if (!regFlag) {
				msg = "注册失败,请联系系统管理人员";
			} else {
				AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, ManageConstants.REGISTER,
						MessageUtils.message("manage.user.register.success")));
			}
		}
		return msg;
	}

}
