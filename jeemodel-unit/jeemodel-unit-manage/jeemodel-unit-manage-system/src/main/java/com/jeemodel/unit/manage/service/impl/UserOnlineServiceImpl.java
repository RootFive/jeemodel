package com.jeemodel.unit.manage.service.impl;

import org.springframework.stereotype.Service;

import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.unit.manage.bean.entity.UserOnline;
import com.jeemodel.unit.manage.core.bean.model.LoginUser;
import com.jeemodel.unit.manage.service.IUserOnlineService;

/**
 * 在线用户 服务层处理
 * 
 * @author Rootfive
 */
@Service
public class UserOnlineServiceImpl implements IUserOnlineService {
	/**
	 * 通过登录地址查询信息
	 * 
	 * @param ipaddr 登录地址
	 * @param user   用户信息
	 * @return 在线用户信息
	 */
	@Override
	public UserOnline selectOnlineByIpaddr(String ipaddr, LoginUser user) {
		if (StringUtils.equals(ipaddr, user.getIpaddr())) {
			return loginUserToUserOnline(user);
		}
		return null;
	}

	/**
	 * 通过用户名称查询信息
	 * 
	 * @param userName 用户名称
	 * @param user     用户信息
	 * @return 在线用户信息
	 */
	@Override
	public UserOnline selectOnlineByUserName(String userName, LoginUser user) {
		if (StringUtils.equals(userName, user.getUsername())) {
			return loginUserToUserOnline(user);
		}
		return null;
	}

	/**
	 * 通过登录地址/用户名称查询信息
	 * 
	 * @param ipaddr   登录地址
	 * @param userName 用户名称
	 * @param user     用户信息
	 * @return 在线用户信息
	 */
	@Override
	public UserOnline selectOnlineByInfo(String ipaddr, String userName, LoginUser user) {
		if (StringUtils.equals(ipaddr, user.getIpaddr()) && StringUtils.equals(userName, user.getUsername())) {
			return loginUserToUserOnline(user);
		}
		return null;
	}

	/**
	 * 设置在线用户信息
	 * 
	 * @param user 用户信息
	 * @return 在线用户
	 */
	@Override
	public UserOnline loginUserToUserOnline(LoginUser user) {
		if (StringUtils.isNull(user) || StringUtils.isNull(user.getUser())) {
			return null;
		}
		UserOnline UserOnline = new UserOnline();
		UserOnline.setTokenId(user.getToken());
		UserOnline.setUserName(user.getUsername());
		UserOnline.setIpaddr(user.getIpaddr());
		UserOnline.setLoginLocation(user.getLoginLocation());
		UserOnline.setBrowser(user.getBrowser());
		UserOnline.setOs(user.getOs());
		UserOnline.setLoginTime(user.getLoginTime());
		if (StringUtils.isNotNull(user.getUser().getDept())) {
			UserOnline.setDeptName(user.getUser().getDept().getDeptName());
		}
		return UserOnline;
	}
}
