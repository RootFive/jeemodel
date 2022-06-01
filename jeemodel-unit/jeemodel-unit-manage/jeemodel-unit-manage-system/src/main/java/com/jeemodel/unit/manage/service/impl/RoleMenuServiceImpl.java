package com.jeemodel.unit.manage.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeemodel.unit.manage.bean.entity.RoleMenu;
import com.jeemodel.unit.manage.mapper.RoleMenuMapper;
import com.jeemodel.unit.manage.service.IRoleMenuService;

/**
 * 角色和菜单关联Service业务层处理
 * 
 * @author JeeModel
 * @date 2022-01-10
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

	/**
	 * 查询菜单使用数量
	 * 
	 * @param menuId 菜单ID
	 * @return 结果
	 */
	@Override
	public int checkMenuExistRole(Long menuId) {
		return lambdaQuery().eq(RoleMenu::getMenuId, menuId).count();
	}

	/**
	 * 通过角色ID删除角色和菜单关联
	 * 
	 * @param roleId 角色ID
	 * @return 结果
	 */
	@Override
	public boolean deleteRoleMenuByRoleId(Long roleId) {
		LambdaQueryWrapper<RoleMenu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(RoleMenu::getRoleId, roleId);
		return remove(lambdaQueryWrapper);
	}

	/**
	 * 批量删除角色菜单关联信息
	 * 
	 * @param roleId 需要删除的角色ID
	 * @return 结果
	 */
	@Override
	public boolean deleteRoleMenu(Long[] roleIds) {
		LambdaQueryWrapper<RoleMenu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.in(RoleMenu::getRoleId, Arrays.asList(roleIds));
		return remove(lambdaQueryWrapper);
	}

	/**
	 * 批量新增角色菜单信息
	 * 
	 * @param roleMenuList 角色菜单列表
	 * @return 结果
	 */
	@Override
	public boolean batchRoleMenu(List<RoleMenu> roleMenuList) {
		return saveBatch(roleMenuList);
	}
    
}
