package com.jeemodel.unit.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeemodel.unit.manage.bean.entity.UserPost;

/**
 * 用户与岗位关联Service接口
 * 
 * @author JeeModel
 * @date 2022-01-10
 */
public interface IUserPostService extends IService<UserPost> {
	
	/**
	 * 通过用户ID删除用户和岗位关联
	 * 
	 * @param userId 用户ID
	 * @return 结果
	 */
	boolean deleteUserPostByUserId(Long ... userIds);
	
	
	/**
	 * 通过岗位ID查询岗位使用数量
	 * 
	 * @param postId 岗位ID
	 * @return 结果
	 */
	int countUserPostById(Long postId);
}
