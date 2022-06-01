package com.jeemodel.unit.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeemodel.unit.manage.bean.entity.UserRole;

/**
 * 用户和角色关联Service接口
 * 
 * @author JeeModel
 * @date 2022-01-10
 */
public interface IUserRoleService extends IService<UserRole> {
	
	/**
	 * 通过用户ID删除用户和角色关联
	 * 
	 * @param userId 用户ID
	 * @return 结果
	 */
	boolean deleteUserRoleByUserId(Long ...userIds);
	
	
	
	/**
	 * 通过角色ID查询角色使用数量
	 * 
	 * @param roleId 角色ID
	 * @return 结果
	 */
	int countUserRoleByRoleId(Long roleId);
	
	
	/**
	 * 删除用户和角色关联信息
	 * 
	 * @param userRole 用户和角色关联信息
	 * @return 结果
	 */
	boolean deleteUserRoleInfo(UserRole userRole);
	
	
	/**
	 * 批量取消授权用户角色
	 * 
	 * @param roleId  角色ID
	 * @param userIds 需要删除的用户数据ID
	 * @return 结果
	 */
	boolean deleteUserRoleInfos(Long roleId, Long ...userIds);
}
