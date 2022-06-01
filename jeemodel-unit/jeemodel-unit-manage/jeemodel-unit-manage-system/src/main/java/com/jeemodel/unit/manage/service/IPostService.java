package com.jeemodel.unit.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeemodel.unit.manage.bean.entity.Post;

/**
 * 岗位信息 服务层
 * 
 */
public interface IPostService extends IService<Post>{
	
	/**
	 * 查询岗位信息集合
	 * 
	 * @param post 岗位信息
	 * @return 岗位列表
	 */
	public List<Post> selectPostList(Post post);

	/**
	 * 根据用户ID获取岗位选择框列表
	 * 
	 * @param userId 用户ID
	 * @return 选中岗位ID列表
	 */
	public List<Long> selectPostListByUserId(Long userId);

	/**
	 * 校验岗位名称
	 * 
	 * @param post 岗位信息
	 * @return 结果
	 */
	public String checkPostNameUnique(Post post);

	/**
	 * 校验岗位编码
	 * 
	 * @param post 岗位信息
	 * @return 结果
	 */
	public String checkPostCodeUnique(Post post);

	/**
	 * 通过岗位ID查询岗位使用数量
	 * 
	 * @param postId 岗位ID
	 * @return 结果
	 */
	public int countUserPostById(Long postId);

	/**
	 * 批量删除岗位信息
	 * 
	 * @param ids 需要删除的岗位ID
	 * @return 结果
	 * @throws Exception 异常
	 */
	public int deletePostByIds(Long[] ids);

}
