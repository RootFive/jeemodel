package com.jeemodel.unit.manage.controller.user;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jeemodel.bean.rpc.Pong;
import com.jeemodel.bean.rpc.PongData;
import com.jeemodel.bean.rpc.PongTable;
import com.jeemodel.bean.rpc.PongUtils;
import com.jeemodel.core.utils.BlankUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.core.web.controller.BaseController;
import com.jeemodel.data.utils.PageUtils;
import com.jeemodel.unit.manage.bean.dto.user.UserAuth;
import com.jeemodel.unit.manage.bean.dto.user.UserAuthRole;
import com.jeemodel.unit.manage.bean.dto.user.UserInfo;
import com.jeemodel.unit.manage.bean.dto.user.UserList;
import com.jeemodel.unit.manage.bean.dto.user.UserListReq;
import com.jeemodel.unit.manage.bean.dto.user.UserSave;
import com.jeemodel.unit.manage.bean.entity.Post;
import com.jeemodel.unit.manage.core.annotation.Log;
import com.jeemodel.unit.manage.core.bean.entity.Role;
import com.jeemodel.unit.manage.core.bean.entity.User;
import com.jeemodel.unit.manage.core.constant.UserConstants;
import com.jeemodel.unit.manage.core.enums.BusinessType;
import com.jeemodel.unit.manage.core.enums.ManageSubCode;
import com.jeemodel.unit.manage.core.utils.SecurityUtils;
import com.jeemodel.unit.manage.core.utils.poi.ExcelUtil;
import com.jeemodel.unit.manage.service.IPostService;
import com.jeemodel.unit.manage.service.IRoleService;
import com.jeemodel.unit.manage.service.IUserService;

/**
 * 用户信息
 * 
 * @author Rootfive
 */
@RestController
@RequestMapping("/manage/user")
public class UserController extends BaseController {
	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IPostService postService;

	/**
	 * 获取用户列表
	 */
	@PreAuthorize("@ss.hasPermi('manage:user:list')")
	@GetMapping("/list")
	public PongTable<UserList> list(UserListReq listReq) {
		PageUtils.startPage();
		List<UserList> list = userService.selectUserList(listReq);
		return PageUtils.okTable(list);
	}

	@Log(title = "用户管理", businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasPermi('manage:user:export')")
	@PostMapping("/export")
	public void export(HttpServletResponse response, UserListReq listReq) {
		List<UserList> list = userService.selectUserList(listReq);
		ExcelUtil<UserList> util = new ExcelUtil<UserList>(UserList.class);
		util.exportExcel(response, list, "用户数据");
	}

	@Log(title = "用户管理", businessType = BusinessType.IMPORT)
	@PreAuthorize("@ss.hasPermi('manage:user:import')")
	@PostMapping("/importData")
	public PongData<String> importData(MultipartFile file, boolean updateSupport) throws Exception {
		ExcelUtil<User> util = new ExcelUtil<User>(User.class);
		List<User> userList = util.importExcel(file.getInputStream());
		String operName = SecurityUtils.getUsername();
		String message = userService.importUser(userList, updateSupport, operName);
		return PongUtils.okData(message);
	}

	@PostMapping("/importTemplate")
	public void importTemplate(HttpServletResponse response) {
		ExcelUtil<User> util = new ExcelUtil<User>(User.class);
		util.importTemplateExcel(response, "用户数据");
	}

	/**
	 * 根据用户编号获取详细信息
	 */
	@PreAuthorize("@ss.hasPermi('manage:user:query')")
	@GetMapping(value = { "/", "/{userId}" })
	public PongData<UserInfo> getInfo(@PathVariable(value = "userId", required = false) Long userId) {
		
		userService.checkUserDataScope(userId);
		
		List<Role> roleList = roleService.selectRoleAll();
		List<Role> roles = User.isAdmin(userId) ? roleList: roleList.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList());
		List<Post> posts = postService.lambdaQuery().list();
		
		UserInfo userInfo = new UserInfo(roles, posts);
		
		if (BlankUtils.isNotNull(userId)) {
			User user = userService.selectUserById(userId);
			List<Long> postIds = postService.selectPostListByUserId(userId);
			List<Long> roleIds = roleService.selectRoleListByUserId(userId);
			
			userInfo.setUser(user);
			userInfo.setPostIds(postIds);
			userInfo.setRoleIds(roleIds);
		}
		return PongUtils.okData(userInfo);
	}

	/**
	 * 新增用户
	 */
	@PreAuthorize("@ss.hasPermi('manage:user:add')")
	@Log(title = "用户管理", businessType = BusinessType.INSERT)
	@PostMapping
	public Pong add(@Validated @RequestBody UserSave user) {
		if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName()))) {
			return PongUtils.fail("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
		} else if (StringUtils.isNotEmpty(user.getPhonenumber())
				&& UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
			return PongUtils.fail("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
		} else if (StringUtils.isNotEmpty(user.getEmail())
				&& UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
			return PongUtils.fail("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
		}
		user.setCreateBy(SecurityUtils.getUsername());
		user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
		return PongUtils.result(userService.insertUser(user));
	}

	/**
	 * 修改用户
	 */
	@PreAuthorize("@ss.hasPermi('manage:user:edit')")
	@Log(title = "用户管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public Pong edit(@Validated @RequestBody UserSave user) {
		userService.checkUserAllowed(user);
		if (StringUtils.isNotEmpty(user.getPhonenumber())
				&& UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
			return PongUtils.fail("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
		} else if (StringUtils.isNotEmpty(user.getEmail())
				&& UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
			return PongUtils.fail("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
		}
		user.setUpdateBy(SecurityUtils.getUsername());
		return PongUtils.result(userService.updateUser(user));
	}

	/**
	 * 删除用户
	 */
	@PreAuthorize("@ss.hasPermi('manage:user:remove')")
	@Log(title = "用户管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
	public Pong remove(@PathVariable Long[] userIds) {
		if (ArrayUtils.contains(userIds, SecurityUtils.getUserId())) {
			return PongUtils.fail("当前用户不能删除");
		}
		return PongUtils.result(userService.deleteUserByIds(userIds));
	}

	/**
	 * 重置密码
	 */
	@PreAuthorize("@ss.hasPermi('manage:user:resetPwd')")
	@Log(title = "用户管理", businessType = BusinessType.UPDATE)
	@PutMapping("/resetPwd")
	public Pong resetPwd(@RequestBody User user) {
		userService.checkUserAllowed(user);
		user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
		user.setUpdateBy(SecurityUtils.getUsername());
		return PongUtils.result(userService.resetPwd(user));
	}

	/**
	 * 状态修改
	 */
	@PreAuthorize("@ss.hasPermi('manage:user:edit')")
	@Log(title = "用户管理", businessType = BusinessType.UPDATE)
	@PutMapping("/changeStatus")
	public Pong changeStatus(@RequestBody User user) {
		userService.checkUserAllowed(user);
		user.setUpdateBy(SecurityUtils.getUsername());
		return PongUtils.result(userService.updateUserStatus(user));
	}

	/**
	 * 根据用户编号获取授权角色
	 */
	@SuppressWarnings("unchecked")
	@PreAuthorize("@ss.hasPermi('manage:user:query')")
	@GetMapping("/authRole/{userId}")
	public PongData<UserAuth> authRole(@PathVariable("userId") Long userId) {
		
		User user = userService.selectUserById(userId);
		if (user == null) {
			return PongUtils.diy(ManageSubCode.U0101);
		}
		List<UserAuthRole> roleList = roleService.selectRolesByUserId(userId);
		List<UserAuthRole> roles = user.isAdmin() ? roleList: roleList.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList());

		UserAuth userAuthRole = new UserAuth(user, roles);
		return PongUtils.okData(userAuthRole);
	}

	/**
	 * 用户授权角色
	 */
	@PreAuthorize("@ss.hasPermi('manage:user:edit')")
	@Log(title = "用户管理", businessType = BusinessType.GRANT)
	@PutMapping("/authRole")
	public Pong insertAuthRole(Long userId, Long[] roleIds) {
		userService.insertUserAuth(userId, roleIds);
		return PongUtils.ok();
	}
}
