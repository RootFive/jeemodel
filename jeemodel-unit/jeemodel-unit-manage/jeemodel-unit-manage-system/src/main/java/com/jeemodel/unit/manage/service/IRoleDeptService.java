package com.jeemodel.unit.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeemodel.unit.manage.bean.entity.RoleDept;

/**
 * 角色和部门关联Service接口
 * 
 * @author JeeModel
 * @date 2022-01-10
 */
public interface IRoleDeptService extends IService<RoleDept> {
	/**
	 * 通过角色ID删除角色和部门关联
	 * 
	 * @param roleId 角色ID
	 * @return 结果
	 */
	int deleteRoleDeptByRoleId(Long roleId);

	/**
	 * 批量删除角色部门关联信息
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	int deleteRoleDept(Long[] roleIds);

}
