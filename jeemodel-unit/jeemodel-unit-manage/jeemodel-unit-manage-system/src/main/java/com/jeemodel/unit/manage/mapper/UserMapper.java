package com.jeemodel.unit.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeemodel.unit.manage.bean.dto.user.UserList;
import com.jeemodel.unit.manage.bean.dto.user.UserListReq;
import com.jeemodel.unit.manage.core.bean.entity.User;
import com.jeemodel.unit.manage.core.bean.model.UserDataScope;

/**
 * 用户表 数据层
 * 
 * @author Rootfive
 */
public interface UserMapper extends BaseMapper<User>{
	/**
	 * 根据条件分页查询用户列表
	 * 
	 * @param User 用户分页请求信息
	 * @return 用户信息集合信息
	 */
	public List<UserList> selectUserList(@Param("listReq") UserListReq listReq,@Param("dataScopeParams") Map<String, Object> dataScopeParams);

	/**
	 * 根据条件分页查询未已配用户角色列表
	 * 
	 * @param user 用户信息
	 * @return 用户信息集合信息
	 */
	public List<User> selectAllocatedList(User user);

	/**
	 * 根据条件分页查询未分配用户角色列表
	 * 
	 * @param user 用户信息
	 * @return 用户信息集合信息
	 */
	public List<User> selectUnallocatedList(User user);

	/**
	 * 通过用户名查询用户
	 * 
	 * @param userName 用户名
	 * @return 用户对象信息
	 */
	public UserDataScope selectUserByUserName(String userName);

	/**
	 * 通过用户ID查询用户
	 * 
	 * @param id 用户ID
	 * @return 用户对象信息
	 */
	public User selectUserById(Long id);



}
