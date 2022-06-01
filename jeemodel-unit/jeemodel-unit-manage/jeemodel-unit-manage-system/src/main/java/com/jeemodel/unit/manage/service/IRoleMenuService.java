package com.jeemodel.unit.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeemodel.unit.manage.bean.entity.RoleMenu;

/**
 * 角色和菜单关联Service接口
 * 
 * @author JeeModel
 * @date 2022-01-10
 */
public interface IRoleMenuService extends IService<RoleMenu> {
	/**
	 * 查询菜单使用数量
	 * 
	 * @param menuId 菜单ID
	 * @return 结果
	 */
	int checkMenuExistRole(Long menuId);

	/**
	 * 通过角色ID删除角色和菜单关联
	 * 
	 * @param roleId 角色ID
	 * @return 结果
	 */
	boolean deleteRoleMenuByRoleId(Long roleId);

	/**
	 * 批量删除角色菜单关联信息
	 * 
	 * @param roleIds 需要删除的角色ID
	 * @return 结果
	 */
	boolean deleteRoleMenu(Long[] roleIds);

	/**
	 * 批量新增角色菜单信息
	 * 
	 * @param roleMenuList 角色菜单列表
	 * @return 结果
	 */
	boolean batchRoleMenu(List<RoleMenu> roleMenuList);
}
