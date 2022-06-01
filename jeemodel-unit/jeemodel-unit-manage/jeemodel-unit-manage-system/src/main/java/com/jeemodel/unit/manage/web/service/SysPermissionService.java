package com.jeemodel.unit.manage.web.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeemodel.base.annotation.HelpService;
import com.jeemodel.unit.manage.core.bean.entity.User;
import com.jeemodel.unit.manage.service.IMenuService;
import com.jeemodel.unit.manage.service.IRoleService;

/**
 * 用户权限处理
 * 
 * @author Rootfive
 */
@HelpService
public class SysPermissionService {

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IMenuService menuService;

	/**
	 * 获取角色数据权限
	 * 
	 * @param user 用户信息
	 * @return 角色权限信息
	 */
	public Set<String> getRolePermission(User user) {
		Set<String> roles = new HashSet<String>();
		// 管理员拥有所有权限
		if (user.isAdmin()) {
			roles.add("admin");
		} else {
			roles.addAll(roleService.selectRolePermissionByUserId(user.getId()));
		}
		return roles;
	}

	/**
	 * 获取菜单数据权限
	 * 
	 * @param user 用户信息
	 * @return 菜单权限信息
	 */
	public Set<String> getMenuPermission(User user) {
		Set<String> perms = new HashSet<String>();
		// 管理员拥有所有权限
		if (user.isAdmin()) {
			perms.add("*:*:*");
		} else {
			perms.addAll(menuService.selectMenuPermsByUserId(user.getId()));
		}
		return perms;
	}
}
