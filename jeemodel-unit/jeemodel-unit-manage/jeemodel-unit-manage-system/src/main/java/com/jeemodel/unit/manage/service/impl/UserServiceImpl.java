package com.jeemodel.unit.manage.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeemodel.bean.exception.type.CheckException;
import com.jeemodel.core.utils.BlankUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.core.utils.bean.BeanUtils;
import com.jeemodel.core.utils.spring.SpringUtils;
import com.jeemodel.unit.manage.annotation.DataScope;
import com.jeemodel.unit.manage.aspectj.DataScopeContext;
import com.jeemodel.unit.manage.bean.dto.user.UserList;
import com.jeemodel.unit.manage.bean.dto.user.UserListReq;
import com.jeemodel.unit.manage.bean.dto.user.UserSave;
import com.jeemodel.unit.manage.bean.entity.Post;
import com.jeemodel.unit.manage.bean.entity.UserPost;
import com.jeemodel.unit.manage.bean.entity.UserRole;
import com.jeemodel.unit.manage.core.bean.entity.Role;
import com.jeemodel.unit.manage.core.bean.entity.User;
import com.jeemodel.unit.manage.core.bean.model.UserDataScope;
import com.jeemodel.unit.manage.core.constant.UserConstants;
import com.jeemodel.unit.manage.core.utils.SecurityUtils;
import com.jeemodel.unit.manage.mapper.PostMapper;
import com.jeemodel.unit.manage.mapper.RoleMapper;
import com.jeemodel.unit.manage.mapper.UserMapper;
import com.jeemodel.unit.manage.service.IConfigService;
import com.jeemodel.unit.manage.service.IUserPostService;
import com.jeemodel.unit.manage.service.IUserRoleService;
import com.jeemodel.unit.manage.service.IUserService;

/**
 * 用户 业务层处理
 * 
 * @author Rootfive
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private PostMapper postMapper;

	@Autowired
	private IUserPostService userPostService;

	@Autowired
	private IConfigService configService;

	@Autowired
	private IUserRoleService userRoleService;
	
	
	
	/**
	 * 根据条件分页查询用户列表
	 * 
	 * @param user 用户信息
	 * @return 用户信息集合信息
	 */
	@Override
	@DataScope(deptAlias = "d", userAlias = "u")
	public List<UserList> selectUserList(UserListReq listReq) {
		Map<String, Object> dataScopeParams = DataScopeContext.get();
		return userMapper.selectUserList(listReq,dataScopeParams);
	}

	/**
	 * 根据条件分页查询已分配用户角色列表
	 * 
	 * @param user 用户信息
	 * @return 用户信息集合信息
	 */
	@Override
	@DataScope(deptAlias = "d", userAlias = "u")
	public List<User> selectAllocatedList(User user) {
		return userMapper.selectAllocatedList(user);
	}

	/**
	 * 根据条件分页查询未分配用户角色列表
	 * 
	 * @param user 用户信息
	 * @return 用户信息集合信息
	 */
	@Override
	@DataScope(deptAlias = "d", userAlias = "u")
	public List<User> selectUnallocatedList(User user) {
		return userMapper.selectUnallocatedList(user);
	}

	/**
	 * 通过用户名查询用户
	 * 
	 * @param userName 用户名
	 * @return 用户对象信息
	 */
	@Override
	public UserDataScope selectUserByUserName(String userName) {
		return userMapper.selectUserByUserName(userName);
	}

	/**
	 * 通过用户ID查询用户
	 * 
	 * @param userId 用户ID
	 * @return 用户对象信息
	 */
	@Override
	public User selectUserById(Long userId) {
		return userMapper.selectUserById(userId);
	}

	/**
	 * 查询用户所属角色组
	 * 
	 * @param userName 用户名
	 * @return 结果
	 */
	@Override
	public String selectUserRoleGroup(String userName) {
		List<Role> list = roleMapper.selectRolesByUserName(userName);
		if (CollectionUtils.isEmpty(list)) {
			return StringUtils.EMPTY;
		}
		return list.stream().map(Role::getRoleName).collect(Collectors.joining(","));
	}

	/**
	 * 查询用户所属岗位组
	 * 
	 * @param userName 用户名
	 * @return 结果
	 */
	@Override
	public String selectUserPostGroup(String userName) {
		List<Post> list = postMapper.selectPostsByUserName(userName);
		if (CollectionUtils.isEmpty(list)) {
			return StringUtils.EMPTY;
		}
		return list.stream().map(Post::getPostName).collect(Collectors.joining(","));
	}

	/**
	 * 校验用户名称是否唯一
	 * 
	 * @param userName 用户名称
	 * @return 结果
	 */
	@Override
	public String checkUserNameUnique(String userName) {
//		int count = userMapper.checkUserNameUnique(userName);
		int count = lambdaQuery().eq(User::getUserName, userName).count();
		if (count > 0) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校验手机号码是否唯一
	 *
	 * @param user 用户信息
	 * @return
	 */
	@Override
	public String checkPhoneUnique(User user) {
		Long userId = BlankUtils.isNull(user.getId()) ? -1L : user.getId();
//		User info = userMapper.checkPhoneUnique(user.getPhonenumber());
		User info = lambdaQuery().eq(User::getPhonenumber, user.getPhonenumber()).select(User::getId,User::getPhonenumber).one();
		if (BlankUtils.isNotNull(info) && info.getId().longValue() != userId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校验email是否唯一
	 *
	 * @param user 用户信息
	 * @return
	 */
	@Override
	public String checkEmailUnique(User user) {
		Long userId = BlankUtils.isNull(user.getId()) ? -1L : user.getId();
//		User info = userMapper.checkEmailUnique(user.getEmail());
//		User info = lambdaQuery().eq(User::getEmail, user.getEmail()).one();
		User info = lambdaQuery().eq(User::getEmail, user.getEmail()).select(User::getId,User::getEmail).one();
		if (BlankUtils.isNotNull(info) && info.getId().longValue() != userId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校验用户是否允许操作
	 * 
	 * @param user 用户信息
	 */
	@Override
	public void checkUserAllowed(User user) {
		if (BlankUtils.isNotNull(user.getId()) && user.isAdmin()) {
			throw new CheckException("不允许操作超级管理员用户");
		}
	}

	/**
	 * 校验用户是否有数据权限
	 * 
	 * @param userId 用户id
	 */
	@Override
	public void checkUserDataScope(Long userId) {
		if (!User.isAdmin(SecurityUtils.getUserId())) {
			UserListReq user = new UserListReq();
			user.setId(userId);
			List<UserList> users = SpringUtils.getAopProxy(this).selectUserList(user);
			if (BlankUtils.isBlank(users)) {
				throw new CheckException("没有权限访问用户数据！");
			}
		}
	}

	/**
	 * 新增保存用户信息
	 * 
	 * @param userSave 用户信息
	 * @return 结果
	 */
	@Override
	@Transactional
	public int insertUser(UserSave userSave) {
		// 新增用户信息
		int rows = userMapper.insert(userSave);
		// 新增用户岗位关联
		insertUserPost(userSave);
		// 新增用户与角色管理
		insertUserRole(userSave);
		return rows;
	}

	/**
	 * 注册用户信息
	 * 
	 * @param userSave 用户信息
	 * @return 结果
	 */
	@Override
	public boolean registerUser(UserSave userSave) {
		return save(userSave);
	}

	/**
	 * 修改保存用户信息
	 * 
	 * @param user 用户信息
	 * @return 结果
	 */
	@Override
	@Transactional
	public int updateUser(UserSave userSave) {
		Long userId = userSave.getId();
		// 删除用户与角色关联
		userRoleService.deleteUserRoleByUserId(userId);
		// 新增用户与角色管理
		insertUserRole(userSave);
		// 删除用户与岗位关联
		userPostService.deleteUserPostByUserId(userId);
		// 新增用户与岗位管理
		insertUserPost(userSave);
		return userMapper.updateById(userSave);
	}

	/**
	 * 用户授权角色
	 * 
	 * @param userId  用户ID
	 * @param roleIds 角色组
	 */
	@Override
	@Transactional
	public void insertUserAuth(Long userId, Long[] roleIds) {
		userRoleService.deleteUserRoleByUserId(userId);
		insertUserRole(userId, roleIds);
	}

	/**
	 * 修改用户状态
	 * 
	 * @param user 用户信息
	 * @return 结果
	 */
	@Override
	public int updateUserStatus(User user) {
		return userMapper.updateById(user);
	}

	/**
	 * 修改用户基本信息
	 * 
	 * @param user 用户信息
	 * @return 结果
	 */
	@Override
	public int updateUserProfile(User user) {
		return userMapper.updateById(user);
	}

	/**
	 * 修改用户头像
	 * 
	 * @param userName 用户名
	 * @param avatar   头像地址
	 * @return 结果
	 */
	@Override
	public boolean updateUserAvatar(String userName, String avatar) {
		LambdaUpdateChainWrapper<User> lambdaUpdate = lambdaUpdate();
		lambdaUpdate.set(User::getAvatar, avatar);
		lambdaUpdate.eq(User::getUserName, userName);
		return lambdaUpdate.update();
	}

	/**
	 * 重置用户密码
	 * 
	 * @param user 用户信息
	 * @return 结果
	 */
	@Override
	public int resetPwd(User user) {
		return userMapper.updateById(user);
	}

	/**
	 * 重置用户密码
	 * 
	 * @param userName 用户名
	 * @param password 密码
	 * @return 结果
	 */
	@Override
	public boolean resetUserPwd(String userName, String password) {
		LambdaUpdateChainWrapper<User> lambdaUpdate = lambdaUpdate();
		lambdaUpdate.set(User::getPassword, password);
		lambdaUpdate.eq(User::getUserName, userName);
		return lambdaUpdate.update();
	}

	/**
	 * 新增用户角色信息
	 * 
	 * @param user 用户对象
	 */
	public void insertUserRole(UserSave user) {
		Long[] roles = user.getRoleIds();
		if (BlankUtils.isNotNull(roles)) {
			// 新增用户与角色管理
			List<UserRole> list = new ArrayList<UserRole>();
			for (Long roleId : roles) {
				UserRole ur = new UserRole();
				ur.setUserId(user.getId());
				ur.setRoleId(roleId);
				list.add(ur);
			}
			if (list.size() > 0) {
				userRoleService.saveBatch(list);
			}
		}
	}

	/**
	 * 新增用户岗位信息
	 * 
	 * @param user 用户对象
	 */
	public void insertUserPost(UserSave user) {
		Long[] posts = user.getPostIds();
		if (BlankUtils.isNotNull(posts)) {
			// 新增用户与岗位管理
			List<UserPost> list = new ArrayList<UserPost>();
			for (Long postId : posts) {
				UserPost up = new UserPost();
				up.setUserId(user.getId());
				up.setPostId(postId);
				list.add(up);
			}
			if (list.size() > 0) {
				userPostService.saveBatch(list);
			}
		}
	}

	/**
	 * 新增用户角色信息
	 * 
	 * @param userId  用户ID
	 * @param roleIds 角色组
	 */
	public void insertUserRole(Long userId, Long[] roleIds) {
		if (BlankUtils.isNotNull(roleIds)) {
			// 新增用户与角色管理
			List<UserRole> list = new ArrayList<UserRole>();
			for (Long roleId : roleIds) {
				UserRole ur = new UserRole();
				ur.setUserId(userId);
				ur.setRoleId(roleId);
				list.add(ur);
			}
			if (list.size() > 0) {
				userRoleService.saveBatch(list);
			}
		}
	}

	/**
	 * 通过用户ID删除用户
	 * 
	 * @param id 用户ID
	 * @return 结果
	 */
	@Override
	@Transactional
	public boolean deleteUserById(Long id) {
		// 删除用户与角色关联
		userRoleService.deleteUserRoleByUserId(id);
		// 删除用户与岗位表
		userPostService.deleteUserPostByUserId(id);
		return deleteUser(id);
	}
	
	
	/**
	 * 批量删除用户信息
	 * 
	 * @param ids 需要删除的用户ID
	 * @return 结果
	 */
	private boolean deleteUser(Long...ids) {
		LambdaUpdateChainWrapper<User> lambdaUpdate = lambdaUpdate();
		lambdaUpdate.set(User::getDelFlag, 2);
		lambdaUpdate.in(User::getId, Arrays.asList(ids));
		
		return lambdaUpdate.update();
	}
	

	/**
	 * 批量删除用户信息
	 * 
	 * @param userIds 需要删除的用户ID
	 * @return 结果
	 */
	@Override
	@Transactional
	public boolean deleteUserByIds(Long[] ids) {
		for (Long userId : ids) {
			checkUserAllowed(new User(userId));
		}
		// 删除用户与角色关联
		userRoleService.deleteUserRoleByUserId(ids);
		// 删除用户与岗位关联
		userPostService.deleteUserPostByUserId(ids);
		return deleteUser(ids);
	}

	/**
	 * 导入用户数据
	 * 
	 * @param userList        用户数据列表
	 * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
	 * @param operName        操作用户
	 * @return 结果
	 */
	@Override
	public String importUser(List<User> userList, Boolean isUpdateSupport, String operName) {
		if (BlankUtils.isNull(userList) || userList.size() == 0) {
			throw new CheckException("导入用户数据不能为空！");
		}
		int successNum = 0;
		int failureNum = 0;
		StringBuilder successMsg = new StringBuilder();
		StringBuilder failureMsg = new StringBuilder();
		String password = configService.selectConfigByKey("sys.user.initPassword");
		for (User user : userList) {
			try {
				// 验证是否存在这个用户
				User u = userMapper.selectUserByUserName(user.getUserName());
				if (BlankUtils.isNull(u)) {
					UserSave userSave = BeanUtils. conver(user,UserSave.class);
					userSave.setPassword(SecurityUtils.encryptPassword(password));
					userSave.setCreateBy(operName);
					this.insertUser(userSave);
					successNum++;
					successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 导入成功");
				} else if (isUpdateSupport) {
					UserSave userSave = BeanUtils. conver(user,UserSave.class);
					userSave.setUpdateBy(operName);
					this.updateUser(userSave);
					successNum++;
					successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 更新成功");
				} else {
					failureNum++;
					failureMsg.append("<br/>" + failureNum + "、账号 " + user.getUserName() + " 已存在");
				}
			} catch (Exception e) {
				failureNum++;
				String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
				failureMsg.append(msg + e.getMessage());
				log.error(msg, e);
			}
		}
		if (failureNum > 0) {
			failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
			throw new CheckException(failureMsg.toString());
		} else {
			successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
		}
		return successMsg.toString();
	}
}
