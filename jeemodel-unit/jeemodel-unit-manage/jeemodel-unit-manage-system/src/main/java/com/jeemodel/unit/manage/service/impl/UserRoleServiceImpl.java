package com.jeemodel.unit.manage.service.impl;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeemodel.unit.manage.bean.entity.UserRole;
import com.jeemodel.unit.manage.mapper.UserRoleMapper;
import com.jeemodel.unit.manage.service.IUserRoleService;

/**
 * 用户和角色关联Service业务层处理
 * 
 * @author JeeModel
 * @date 2022-01-10
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

	/**
	 * 通过用户ID删除用户和角色关联
	 * 
	 * @param userId 用户ID
	 * @return 结果
	 */
	@Override
	public boolean deleteUserRoleByUserId(Long... userIds) {
		LambdaQueryWrapper<UserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.in(UserRole::getUserId, Arrays.asList(userIds));
		return remove(lambdaQueryWrapper);
	}
	/**
	 * 通过角色ID查询角色使用数量
	 * 
	 * @param roleId 角色ID
	 * @return 结果
	 */
	@Override
	public int countUserRoleByRoleId(Long roleId) {
		return lambdaQuery().eq(UserRole::getRoleId, roleId).count();
	}
	
	
	
	/**
	 * 删除用户和角色关联信息
	 * 
	 * @param userRole 用户和角色关联信息
	 * @return 结果
	 */
	@Override
	public boolean deleteUserRoleInfo(UserRole userRole) {
		LambdaQueryWrapper<UserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(UserRole::getUserId, userRole.getUserId());
		lambdaQueryWrapper.eq(UserRole::getRoleId, userRole.getRoleId());
		return remove(lambdaQueryWrapper);
	}
	
	/**
	 * 批量取消授权用户角色
	 * 
	 * @param roleId  角色ID
	 * @param userIds 需要删除的用户数据ID
	 * @return 结果
	 */
	@Override
	public boolean deleteUserRoleInfos(Long roleId, Long ...userIds) {
		LambdaQueryWrapper<UserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(UserRole::getRoleId, roleId);
		lambdaQueryWrapper.eq(UserRole::getUserId, Arrays.asList(userIds));
		return remove(lambdaQueryWrapper);
	}

}
