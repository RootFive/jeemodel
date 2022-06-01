package com.jeemodel.unit.manage.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeemodel.unit.manage.bean.entity.Post;

/**
 * 岗位信息 数据层
 * 
 * @author Rootfive
 */
public interface PostMapper extends BaseMapper<Post>{

	/**
	 * 根据用户ID获取岗位选择框列表
	 * 
	 * @param userId 用户ID
	 * @return 选中岗位ID列表
	 */
	public List<Long> selectPostListByUserId(Long userId);

	/**
	 * 查询用户所属岗位组
	 * 
	 * @param userName 用户名
	 * @return 结果
	 */
	public List<Post> selectPostsByUserName(String userName);

}
