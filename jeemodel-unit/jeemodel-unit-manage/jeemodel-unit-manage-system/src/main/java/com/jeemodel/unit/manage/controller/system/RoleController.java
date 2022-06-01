package com.jeemodel.unit.manage.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

import com.jeemodel.bean.http.Pong;
import com.jeemodel.bean.http.PongData;
import com.jeemodel.bean.http.PongTable;
import com.jeemodel.bean.http.PongUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.core.web.controller.BaseController;
import com.jeemodel.data.utils.PageUtils;
import com.jeemodel.unit.manage.bean.dto.system.RoleAuthAllocatedUserListReq;
import com.jeemodel.unit.manage.bean.dto.system.RoleListReq;
import com.jeemodel.unit.manage.bean.dto.system.RoleSave;
import com.jeemodel.unit.manage.bean.entity.UserRole;
import com.jeemodel.unit.manage.core.annotation.Log;
import com.jeemodel.unit.manage.core.bean.entity.Role;
import com.jeemodel.unit.manage.core.bean.entity.User;
import com.jeemodel.unit.manage.core.bean.model.LoginUser;
import com.jeemodel.unit.manage.core.constant.UserConstants;
import com.jeemodel.unit.manage.core.enums.BusinessType;
import com.jeemodel.unit.manage.core.utils.SecurityUtils;
import com.jeemodel.unit.manage.core.utils.poi.ExcelUtil;
import com.jeemodel.unit.manage.core.web.service.TokenService;
import com.jeemodel.unit.manage.service.IRoleService;
import com.jeemodel.unit.manage.service.IUserService;
import com.jeemodel.unit.manage.web.service.SysPermissionService;

/**
 * 角色信息
 * 
 * @author Rootfive
 */
@RestController
@RequestMapping("/manage/role")
public class RoleController extends BaseController {
	@Autowired
	private IRoleService roleService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private SysPermissionService permissionService;

	@Autowired
	private IUserService userService;

	@PreAuthorize("@ss.hasPermi('manage:role:list')")
	@GetMapping("/list")
	public PongTable<Role> list(RoleListReq role) {
		PageUtils.startPage();
		List<Role> list = roleService.selectRoleList(role);
		return PageUtils.okTable(list);
	}

	@Log(title = "角色管理", businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasPermi('manage:role:export')")
	@PostMapping("/export")
	public void export(HttpServletResponse response, RoleListReq role) {
		List<Role> list = roleService.selectRoleList(role);
		ExcelUtil<Role> util = new ExcelUtil<Role>(Role.class);
		util.exportExcel(response, list, "角色数据");
	}

	/**
	 * 根据角色编号获取详细信息
	 */
	@PreAuthorize("@ss.hasPermi('manage:role:query')")
	@GetMapping(value = "/{id}")
	public PongData<Role> getInfo(@PathVariable Long id) {
		roleService.checkRoleDataScope(id);
		return PongUtils.okData(roleService.selectRoleById(id));
	}

	/**
	 * 新增角色
	 */
	@PreAuthorize("@ss.hasPermi('manage:role:add')")
	@Log(title = "角色管理", businessType = BusinessType.INSERT)
	@PostMapping
	public Pong add(@Validated @RequestBody RoleSave role) {
		if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
			return PongUtils.fail("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
		} else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
			return PongUtils.fail("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
		}
		role.setCreateBy(SecurityUtils.getUsername());
		return PongUtils.result(roleService.insertRole(role));

	}

	/**
	 * 修改保存角色
	 */
	@PreAuthorize("@ss.hasPermi('manage:role:edit')")
	@Log(title = "角色管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public Pong edit(@Validated @RequestBody RoleSave role) {
		roleService.checkRoleAllowed(role);
		if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
			return PongUtils.fail("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
		} else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
			return PongUtils.fail("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
		}
		role.setUpdateBy(SecurityUtils.getUsername());

//		if (roleService.updateRole(role) > 0) {
		if (roleService.updateRole(role)) {
			// 更新缓存用户权限
			LoginUser loginUser = SecurityUtils.getLoginUser();
			if (StringUtils.isNotNull(loginUser.getUser()) && !loginUser.getUser().isAdmin()) {
				loginUser.setPermissions(permissionService.getMenuPermission(loginUser.getUser()));
				loginUser.setUser(userService.selectUserByUserName(loginUser.getUser().getUserName()));
				tokenService.setLoginUser(loginUser);
			}
			return PongUtils.ok();
		}
		return PongUtils.fail("修改角色'" + role.getRoleName() + "'失败，请联系管理员");
	}

	/**
	 * 修改保存数据权限
	 */
	@PreAuthorize("@ss.hasPermi('manage:role:edit')")
	@Log(title = "角色管理", businessType = BusinessType.UPDATE)
	@PutMapping("/dataScope")
	public Pong dataScope(@RequestBody RoleSave role) {
		roleService.checkRoleAllowed(role);
		return PongUtils.result(roleService.authDataScope(role));
	}

	/**
	 * 状态修改
	 */
	@PreAuthorize("@ss.hasPermi('manage:role:edit')")
	@Log(title = "角色管理", businessType = BusinessType.UPDATE)
	@PutMapping("/changeStatus")
	public Pong changeStatus(@RequestBody Role role) {
		roleService.checkRoleAllowed(role);
		role.setUpdateBy(SecurityUtils.getUsername());
		return PongUtils.result(roleService.updateRoleStatus(role));
	}

	/**
	 * 删除角色
	 */
	@PreAuthorize("@ss.hasPermi('manage:role:remove')")
	@Log(title = "角色管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public Pong remove(@PathVariable Long[] ids) {
		return PongUtils.result(roleService.deleteRoleByIds(ids));
	}

	/**
	 * 获取角色选择框列表
	 */
	@PreAuthorize("@ss.hasPermi('manage:role:query')")
	@GetMapping("/optionselect")
	public PongData<List<Role>> optionselect() {
		return PongUtils.okData(roleService.selectRoleAll());
	}

	/**
	 * 查询已分配用户角色列表
	 */
	@PreAuthorize("@ss.hasPermi('manage:role:list')")
	@GetMapping("/authUser/allocatedList")
	public PongTable<User> allocatedList(RoleAuthAllocatedUserListReq user) {
		PageUtils.startPage();
		List<User> list = userService.selectAllocatedList(user);
		return PageUtils.okTable(list);
	}

	/**
	 * 查询未分配用户角色列表
	 */
	@PreAuthorize("@ss.hasPermi('manage:role:list')")
	@GetMapping("/authUser/unallocatedList")
	public PongTable<User> unallocatedList(RoleAuthAllocatedUserListReq user) {
		PageUtils.startPage();
		List<User> list = userService.selectUnallocatedList(user);
		return PageUtils.okTable(list);
	}

	/**
	 * 取消授权用户
	 */
	@PreAuthorize("@ss.hasPermi('manage:role:edit')")
	@Log(title = "角色管理", businessType = BusinessType.GRANT)
	@PutMapping("/authUser/cancel")
	public Pong cancelAuthUser(@RequestBody UserRole userRole) {
		return PongUtils.result(roleService.deleteAuthUser(userRole));
	}

	/**
	 * 批量取消授权用户
	 */
	@PreAuthorize("@ss.hasPermi('manage:role:edit')")
	@Log(title = "角色管理", businessType = BusinessType.GRANT)
	@PutMapping("/authUser/cancelAll")
	public Pong cancelAuthUserAll(Long roleId, Long[] userIds) {
		return PongUtils.result(roleService.deleteAuthUsers(roleId, userIds));
	}

	/**
	 * 批量选择用户授权
	 */
	@PreAuthorize("@ss.hasPermi('manage:role:edit')")
	@Log(title = "角色管理", businessType = BusinessType.GRANT)
	@PutMapping("/authUser/selectAll")
	public Pong selectAuthUserAll(Long roleId, Long[] userIds) {
		return PongUtils.result(roleService.insertAuthUsers(roleId, userIds));
	}
}
