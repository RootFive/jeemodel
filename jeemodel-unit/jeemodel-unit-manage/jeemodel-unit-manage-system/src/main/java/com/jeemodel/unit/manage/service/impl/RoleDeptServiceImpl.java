package com.jeemodel.unit.manage.service.impl;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeemodel.unit.manage.bean.entity.RoleDept;
import com.jeemodel.unit.manage.mapper.RoleDeptMapper;
import com.jeemodel.unit.manage.service.IRoleDeptService;

/**
 * 角色和部门关联Service业务层处理
 * 
 * @author JeeModel
 * @date 2022-01-10
 */
@Service
public class RoleDeptServiceImpl extends ServiceImpl<RoleDeptMapper, RoleDept> implements IRoleDeptService {
	
	/**
	 * 通过角色ID删除角色和部门关联
	 * 
	 * @param roleId 角色ID
	 * @return 结果
	 */
	@Override
	public int deleteRoleDeptByRoleId(Long roleId) {
		LambdaQueryWrapper<RoleDept> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(RoleDept::getRoleId, roleId);
		return baseMapper.delete(lambdaQueryWrapper);
	}
	/**
	 * 批量删除角色部门关联信息
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteRoleDept(Long[] roleIds) {
		LambdaQueryWrapper<RoleDept> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.in(RoleDept::getRoleId, Arrays.asList(roleIds));
		return baseMapper.delete(lambdaQueryWrapper);
	}
	
}
