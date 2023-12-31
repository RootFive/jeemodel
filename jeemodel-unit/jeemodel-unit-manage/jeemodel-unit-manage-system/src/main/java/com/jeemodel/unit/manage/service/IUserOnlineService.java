package com.jeemodel.unit.manage.service;

import com.jeemodel.unit.manage.bean.entity.UserOnline;
import com.jeemodel.unit.manage.core.bean.model.LoginUser;

/**
 * 在线用户 服务层
 * 
 * @author Rootfive
 */
public interface IUserOnlineService {
	/**
	 * 通过登录地址查询信息
	 * 
	 * @param ipaddr 登录地址
	 * @param user   用户信息
	 * @return 在线用户信息
	 */
	public UserOnline selectOnlineByIpaddr(String ipaddr, LoginUser user);

	/**
	 * 通过用户名称查询信息
	 * 
	 * @param userName 用户名称
	 * @param user     用户信息
	 * @return 在线用户信息
	 */
	public UserOnline selectOnlineByUserName(String userName, LoginUser user);

	/**
	 * 通过登录地址/用户名称查询信息
	 * 
	 * @param ipaddr   登录地址
	 * @param userName 用户名称
	 * @param user     用户信息
	 * @return 在线用户信息
	 */
	public UserOnline selectOnlineByInfo(String ipaddr, String userName, LoginUser user);

	/**
	 * 设置在线用户信息
	 * 
	 * @param user 用户信息
	 * @return 在线用户
	 */
	public UserOnline loginUserToUserOnline(LoginUser user);
}
