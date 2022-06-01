package com.jeemodel.unit.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeemodel.unit.manage.bean.dto.user.UserList;
import com.jeemodel.unit.manage.bean.dto.user.UserListReq;
import com.jeemodel.unit.manage.bean.dto.user.UserSave;
import com.jeemodel.unit.manage.core.bean.entity.User;
import com.jeemodel.unit.manage.core.bean.model.UserDataScope;

/**
 * 用户 业务层
 * 
 */
public interface IUserService extends IService<User>{
	/**
	 * 根据条件分页查询用户列表
	 * 
	 * @param listReq 用户分页请求信息
	 * @return 用户信息集合信息
	 */
	public List<UserList> selectUserList(UserListReq listReq);

	/**
	 * 根据条件分页查询已分配用户角色列表
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

	/**
	 * 根据用户ID查询用户所属角色组
	 * 
	 * @param userName 用户名
	 * @return 结果
	 */
	public String selectUserRoleGroup(String userName);

	/**
	 * 根据用户ID查询用户所属岗位组
	 * 
	 * @param userName 用户名
	 * @return 结果
	 */
	public String selectUserPostGroup(String userName);

	/**
	 * 校验用户名称是否唯一
	 * 
	 * @param userName 用户名称
	 * @return 结果
	 */
	public String checkUserNameUnique(String userName);

	/**
	 * 校验手机号码是否唯一
	 *
	 * @param user 用户信息
	 * @return 结果
	 */
	public String checkPhoneUnique(User user);

	/**
	 * 校验email是否唯一
	 *
	 * @param user 用户信息
	 * @return 结果
	 */
	public String checkEmailUnique(User user);

	/**
	 * 校验用户是否允许操作
	 * 
	 * @param user 用户信息
	 */
	public void checkUserAllowed(User user);

	/**
	 * 校验用户是否有数据权限
	 * 
	 * @param userId 用户id
	 */
	public void checkUserDataScope(Long userId);

	/**
	 * 新增用户信息
	 * 
	 * @param userSave 用户信息
	 * @return 结果
	 */
	public int insertUser(UserSave userSave);

	/**
	 * 注册用户信息
	 * 
	 * @param userSave 用户信息
	 * @return 结果
	 */
	public boolean registerUser(UserSave userSave);

	/**
	 * 修改用户信息
	 * 
	 * @param userSave 用户信息
	 * @return 结果
	 */
	public int updateUser(UserSave userSave);

	/**
	 * 用户授权角色
	 * 
	 * @param userId  用户ID
	 * @param roleIds 角色组
	 */
	public void insertUserAuth(Long userId, Long[] roleIds);

	/**
	 * 修改用户状态
	 * 
	 * @param user 用户信息
	 * @return 结果
	 */
	public int updateUserStatus(User user);

	/**
	 * 修改用户基本信息
	 * 
	 * @param user 用户信息
	 * @return 结果
	 */
	public int updateUserProfile(User user);

	/**
	 * 修改用户头像
	 * 
	 * @param userName 用户名
	 * @param avatar   头像地址
	 * @return 结果
	 */
	public boolean updateUserAvatar(String userName, String avatar);

	/**
	 * 重置用户密码
	 * 
	 * @param user 用户信息
	 * @return 结果
	 */
	public int resetPwd(User user);

	/**
	 * 重置用户密码
	 * 
	 * @param userName 用户名
	 * @param password 密码
	 * @return 结果
	 */
	public boolean resetUserPwd(String userName, String password);

	/**
	 * 通过用户ID删除用户
	 * 
	 * @param id 用户ID
	 * @return 结果
	 */
	public boolean deleteUserById(Long id);

	/**
	 * 批量删除用户信息
	 * 
	 * @param ids 需要删除的用户ID
	 * @return 结果
	 */
	public boolean deleteUserByIds(Long[] ids);

	/**
	 * 导入用户数据
	 * 
	 * @param userList        用户数据列表
	 * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
	 * @param operName        操作用户
	 * @return 结果
	 */
	public String importUser(List<User> userList, Boolean isUpdateSupport, String operName);
}
