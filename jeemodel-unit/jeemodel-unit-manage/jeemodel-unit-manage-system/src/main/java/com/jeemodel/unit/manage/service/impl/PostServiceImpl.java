package com.jeemodel.unit.manage.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeemodel.bean.exception.type.CheckException;
import com.jeemodel.core.utils.BlankUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.unit.manage.bean.entity.Post;
import com.jeemodel.unit.manage.core.constant.UserConstants;
import com.jeemodel.unit.manage.mapper.PostMapper;
import com.jeemodel.unit.manage.service.IPostService;
import com.jeemodel.unit.manage.service.IUserPostService;

/**
 * 岗位信息 服务层处理
 * 
 * @author Rootfive
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {

	@Autowired
	private PostMapper postMapper;

	@Autowired
	private IUserPostService userPostService;

	/**
	 * 查询岗位信息集合
	 * 
	 * @param post 岗位信息
	 * @return 岗位信息集合
	 */
	@Override
	public List<Post> selectPostList(Post post) {
		LambdaQueryChainWrapper<Post> lambdaQuery = lambdaQuery();
		lambdaQuery.like(StringUtils.isNotBlank(post.getPostCode()),Post::getPostCode, post.getPostCode());
		lambdaQuery.eq(StringUtils.isNotBlank(post.getStatus()),Post::getStatus, post.getStatus());
		lambdaQuery.like(StringUtils.isNotBlank(post.getPostName()),Post::getPostName, post.getPostName());
		lambdaQuery.orderByAsc(Post::getPostSort);
		return lambdaQuery.list();
	}


	/**
	 * 根据用户ID获取岗位选择框列表
	 * 
	 * @param userId 用户ID
	 * @return 选中岗位ID列表
	 */
	@Override
	public List<Long> selectPostListByUserId(Long userId) {
		return postMapper.selectPostListByUserId(userId);
	}

	/**
	 * 校验岗位名称是否唯一
	 * 
	 * @param post 岗位信息
	 * @return 结果
	 */
	@Override
	public String checkPostNameUnique(Post post) {
		Long id = BlankUtils.isNull(post.getId()) ? -1L : post.getId();
		Post info = lambdaQuery().eq(Post::getPostName, post.getPostName()).one();
		if (BlankUtils.isNotNull(info) && info.getId().longValue() != id.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校验岗位编码是否唯一
	 * 
	 * @param post 岗位信息
	 * @return 结果
	 */
	@Override
	public String checkPostCodeUnique(Post post) {
		Long id = BlankUtils.isNull(post.getId()) ? -1L : post.getId();
		Post info = lambdaQuery().eq(Post::getPostCode, post.getPostCode()).one();
		if (BlankUtils.isNotNull(info) && info.getId().longValue() != id.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 通过岗位ID查询岗位使用数量
	 * 
	 * @param postId 岗位ID
	 * @return 结果
	 */
	@Override
	public int countUserPostById(Long postId) {
		return userPostService.countUserPostById(postId);
	}


	/**
	 * 批量删除岗位信息
	 * 
	 * @param ids 需要删除的岗位ID
	 * @return 结果
	 * @throws Exception 异常
	 */
	@Override
	public int deletePostByIds(Long[] ids) {
		for (Long id : ids) {
			Post post = getById(id);
			if (countUserPostById(id) > 0) {
				throw new CheckException(String.format("%1$s已分配,不能删除", post.getPostName()));
			}
		}
		
		return postMapper.deleteBatchIds(Arrays.asList(ids));
	}
}
