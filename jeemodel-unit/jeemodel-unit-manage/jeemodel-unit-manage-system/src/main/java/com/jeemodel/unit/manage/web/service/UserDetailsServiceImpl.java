package com.jeemodel.unit.manage.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jeemodel.base.annotation.HelpService;
import com.jeemodel.bean.exception.type.CheckException;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.unit.manage.core.bean.model.LoginUser;
import com.jeemodel.unit.manage.core.bean.model.UserDataScope;
import com.jeemodel.unit.manage.core.enums.UserStatus;
import com.jeemodel.unit.manage.service.IUserService;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户验证处理
 *
 * @author Rootfive
 */
@Slf4j
@HelpService
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IUserService userService;

	@Autowired
	private SysPermissionService permissionService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDataScope user = userService.selectUserByUserName(username);
		if (StringUtils.isNull(user)) {
			log.info("登录用户：{} 不存在.", username);
			throw new CheckException("登录用户：" + username + " 不存在");
		} else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
			log.info("登录用户：{} 已被删除.", username);
			throw new CheckException("对不起，您的账号：" + username + " 已被删除");
		} else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
			log.info("登录用户：{} 已被停用.", username);
			throw new CheckException("对不起，您的账号：" + username + " 已停用");
		}

		return createLoginUser(user);
	}

	public UserDetails createLoginUser(UserDataScope user) {
		return new LoginUser(user.getId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
	}
}
