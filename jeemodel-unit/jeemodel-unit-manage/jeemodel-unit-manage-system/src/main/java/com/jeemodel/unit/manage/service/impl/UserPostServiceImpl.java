package com.jeemodel.unit.manage.service.impl;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeemodel.unit.manage.bean.entity.UserPost;
import com.jeemodel.unit.manage.mapper.UserPostMapper;
import com.jeemodel.unit.manage.service.IUserPostService;

/**
 * 用户与岗位关联Service业务层处理
 * 
 * @author JeeModel
 * @date 2022-01-10
 */
@Service
public class UserPostServiceImpl extends ServiceImpl<UserPostMapper, UserPost> implements IUserPostService {

	/**
	 * 通过用户ID删除用户和岗位关联
	 * 
	 * @param userId 用户ID
	 * @return 结果
	 */
	@Override
	public boolean deleteUserPostByUserId(Long ... userIds) {
		LambdaQueryWrapper<UserPost> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.in(UserPost::getUserId, Arrays.asList(userIds));
		return remove(lambdaQueryWrapper);
	}

	
	/**
	 * 通过岗位ID查询岗位使用数量
	 * 
	 * @param postId 岗位ID
	 * @return 结果
	 */
	@Override
	public int countUserPostById(Long postId) {
		return lambdaQuery().eq(UserPost::getPostId, postId).count();
	}

   
}
