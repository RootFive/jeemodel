package com.jeemodel.unit.manage.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeemodel.bean.exception.type.CheckException;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.core.utils.bean.BeanUtils;
import com.jeemodel.core.utils.spring.SpringUtils;
import com.jeemodel.unit.manage.annotation.DataScope;
import com.jeemodel.unit.manage.aspectj.DataScopeContext;
import com.jeemodel.unit.manage.bean.dto.system.RoleListReq;
import com.jeemodel.unit.manage.bean.dto.system.RoleSave;
import com.jeemodel.unit.manage.bean.dto.user.UserAuthRole;
import com.jeemodel.unit.manage.bean.entity.RoleDept;
import com.jeemodel.unit.manage.bean.entity.RoleMenu;
import com.jeemodel.unit.manage.bean.entity.UserRole;
import com.jeemodel.unit.manage.core.bean.entity.Role;
import com.jeemodel.unit.manage.core.bean.entity.User;
import com.jeemodel.unit.manage.core.constant.UserConstants;
import com.jeemodel.unit.manage.core.utils.SecurityUtils;
import com.jeemodel.unit.manage.mapper.RoleDeptMapper;
import com.jeemodel.unit.manage.mapper.RoleMapper;
import com.jeemodel.unit.manage.service.IRoleDeptService;
import com.jeemodel.unit.manage.service.IRoleMenuService;
import com.jeemodel.unit.manage.service.IRoleService;
import com.jeemodel.unit.manage.service.IUserRoleService;

/**
 * 角色 业务层处理
 * 
 * @author Rootfive
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

	
	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private RoleDeptMapper roleDeptMapper;
	
	
	@Autowired
	private IRoleMenuService roleMenuService;


	@Autowired
	private IRoleDeptService roleDeptService;
	
	@Autowired
	private IUserRoleService userRoleService;
	

	/**
	 * 根据条件分页查询角色数据
	 * 
	 * @param role 角色信息
	 * @return 角色数据集合信息
	 */
	@Override
	@DataScope(deptAlias = "d")
	public List<Role> selectRoleList(RoleListReq role) {
		Map<String, Object> dataScopeParams = DataScopeContext.get();
		return roleMapper.selectRoleList(role,dataScopeParams);
	}

	/**
	 * 根据用户ID查询角色
	 * 
	 * @param userId 用户ID
	 * @return 角色列表
	 */
	@Override
	public List<UserAuthRole> selectRolesByUserId(Long userId) {
		List<Role> userRoles = roleMapper.selectRolePermissionByUserId(userId);
		List<Role> roles = selectRoleAll();
		
		List<UserAuthRole> allRoles = BeanUtils.converList(roles, UserAuthRole.class);
		for (UserAuthRole role : allRoles) {
			for (Role userRole : userRoles) {
				if (role.getId().longValue() == userRole.getId().longValue()) {
					role.setFlag(true);
					break;
				}
			}
		}
		return allRoles;
	}

	/**
	 * 根据用户ID查询权限
	 * 
	 * @param userId 用户ID
	 * @return 权限列表
	 */
	@Override
	public Set<String> selectRolePermissionByUserId(Long userId) {
		List<Role> perms = roleMapper.selectRolePermissionByUserId(userId);
		Set<String> permsSet = new HashSet<>();
		for (Role perm : perms) {
			if (StringUtils.isNotNull(perm)) {
				permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
			}
		}
		return permsSet;
	}

	/**
	 * 查询所有角色
	 * 
	 * @return 角色列表
	 */
	@Override
	public List<Role> selectRoleAll() {
		return SpringUtils.getAopProxy(this).selectRoleList(new RoleListReq());
	}

	/**
	 * 根据用户ID获取角色选择框列表
	 * 
	 * @param userId 用户ID
	 * @return 选中角色ID列表
	 */
	@Override
	public List<Long> selectRoleListByUserId(Long userId) {
		return roleMapper.selectRoleListByUserId(userId);
	}

	/**
	 * 通过角色ID查询角色
	 * 
	 * @param id 角色ID
	 * @return 角色对象信息
	 */
	@Override
	public Role selectRoleById(Long id) {
		return roleMapper.selectRoleById(id);
	}

	/**
	 * 校验角色名称是否唯一
	 * 
	 * @param role 角色信息
	 * @return 结果
	 */
	@Override
	public String checkRoleNameUnique(Role role) {
		Long roleId = StringUtils.isNull(role.getId()) ? -1L : role.getId();
		Role info = roleMapper.checkRoleNameUnique(role.getRoleName());
		if (StringUtils.isNotNull(info) && info.getId().longValue() != roleId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校验角色权限是否唯一
	 * 
	 * @param role 角色信息
	 * @return 结果
	 */
	@Override
	public String checkRoleKeyUnique(Role role) {
		Long roleId = StringUtils.isNull(role.getId()) ? -1L : role.getId();
		Role info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
		if (StringUtils.isNotNull(info) && info.getId().longValue() != roleId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校验角色是否允许操作
	 * 
	 * @param role 角色信息
	 */
	@Override
	public void checkRoleAllowed(Role role) {
		if (StringUtils.isNotNull(role.getId()) && role.isAdmin()) {
			throw new CheckException("不允许操作超级管理员角色");
		}
	}

	/**
	 * 校验角色是否有数据权限
	 * 
	 * @param roleId 角色id
	 */
	@Override
	public void checkRoleDataScope(Long roleId) {
		if (!User.isAdmin(SecurityUtils.getUserId())) {
			RoleListReq role = new RoleListReq();
			role.setId(roleId);
			List<Role> roles = SpringUtils.getAopProxy(this).selectRoleList(role);
			if (StringUtils.isEmpty(roles)) {
				throw new CheckException("没有权限访问角色数据！");
			}
		}
	}

	/**
	 * 通过角色ID查询角色使用数量
	 * 
	 * @param roleId 角色ID
	 * @return 结果
	 */
	@Override
	public int countUserRoleByRoleId(Long roleId) {
		return userRoleService.countUserRoleByRoleId(roleId);
	}

	/**
	 * 新增保存角色信息
	 * 
	 * @param role 角色信息
	 * @return 结果
	 */
	@Override
	@Transactional
	public boolean insertRole(RoleSave role) {
		// 新增角色信息
//		roleMapper.insertRole(role);
		roleMapper.insert(role);
		return insertRoleMenu(role);
	}

	/**
	 * 修改保存角色信息
	 * 
	 * @param role 角色信息
	 * @return 结果
	 */
	@Override
	@Transactional
	public boolean updateRole(RoleSave role) {
		// 修改角色信息
//		roleMapper.updateRole(role);
		roleMapper.updateById(role);
		// 删除角色与菜单关联
		roleMenuService.deleteRoleMenuByRoleId(role.getId());
		return insertRoleMenu(role);
	}

	/**
	 * 修改角色状态
	 * 
	 * @param role 角色信息
	 * @return 结果
	 */
	@Override
	public int updateRoleStatus(Role role) {
//		return roleMapper.updateRole(role);
		return roleMapper.updateById(role);
	}

	/**
	 * 修改数据权限信息
	 * 
	 * @param role 角色信息
	 * @return 结果
	 */
	@Override
	@Transactional
	public int authDataScope(RoleSave role) {
		// 修改角色信息
//		roleMapper.updateRole(role);
		roleMapper.updateById(role);
		// 删除角色与部门关联
		roleDeptService.deleteRoleDeptByRoleId(role.getId());
		// 新增角色和部门信息（数据权限）
		return insertRoleDept(role);
	}

	/**
	 * 新增角色菜单信息
	 * 
	 * @param role 角色对象
	 */
	public boolean insertRoleMenu(RoleSave role) {
		boolean result = false;
		// 新增用户与角色管理
		List<RoleMenu> list = new ArrayList<RoleMenu>();
		for (Long menuId : role.getMenuIds()) {
			RoleMenu rm = new RoleMenu();
			rm.setRoleId(role.getId());
			rm.setMenuId(menuId);
			list.add(rm);
		}
		if (list.size() > 0) {
			result = roleMenuService.batchRoleMenu(list);
		}else {
			result = true;
		}
		return result;
	}

	/**
	 * 新增角色部门信息(数据权限)
	 *
	 * @param role 角色对象
	 */
	public int insertRoleDept(RoleSave role) {
		int rows = 1;
		// 新增角色与部门（数据权限）管理
		List<RoleDept> list = new ArrayList<RoleDept>();
		for (Long deptId : role.getDeptIds()) {
			RoleDept rd = new RoleDept();
			rd.setRoleId(role.getId());
			rd.setDeptId(deptId);
			list.add(rd);
		}
		if (list.size() > 0) {
			rows = roleDeptMapper.batchRoleDept(list);
		}
		return rows;
	}

	/**
	 * 通过角色ID删除角色
	 * 
	 * @param id 角色ID
	 * @return 结果
	 */
	@Override
	@Transactional
	public int deleteRoleById(Long id) {
		// 删除角色与菜单关联
		roleMenuService.deleteRoleMenuByRoleId(id);
		// 删除角色与部门关联
		roleDeptService.deleteRoleDeptByRoleId(id);
		return roleMapper.deleteRoleById(id);
	}

	/**
	 * 批量删除角色信息
	 * 
	 * @param roleIds 需要删除的角色ID
	 * @return 结果
	 */
	@Override
	@Transactional
	public int deleteRoleByIds(Long[] ids) {
		for (Long roleId : ids) {
			checkRoleAllowed(new Role(roleId));
			Role role = selectRoleById(roleId);
			if (countUserRoleByRoleId(roleId) > 0) {
				throw new CheckException(String.format("%1$s已分配,不能删除", role.getRoleName()));
			}
		}
		// 删除角色与菜单关联
		roleMenuService.deleteRoleMenu(ids);
		// 删除角色与部门关联
		roleDeptService.deleteRoleDept(ids);
		return roleMapper.deleteRoleByIds(ids);
	}

	/**
	 * 取消授权用户角色
	 * 
	 * @param userRole 用户和角色关联信息
	 * @return 结果
	 */
	@Override
	public boolean deleteAuthUser(UserRole userRole) {
		return userRoleService.deleteUserRoleInfo(userRole);
	}

	/**
	 * 批量取消授权用户角色
	 * 
	 * @param roleId  角色ID
	 * @param userIds 需要取消授权的用户数据ID
	 * @return 结果
	 */
	@Override
	public boolean deleteAuthUsers(Long roleId, Long[] userIds) {
		return userRoleService.deleteUserRoleInfos(roleId, userIds);
	}

	/**
	 * 批量选择授权用户角色
	 * 
	 * @param roleId  角色ID
	 * @param userIds 需要删除的用户数据ID
	 * @return 结果
	 */
	@Override
	public boolean insertAuthUsers(Long roleId, Long[] userIds) {
		// 新增用户与角色管理
		List<UserRole> list = new ArrayList<UserRole>();
		for (Long userId : userIds) {
			UserRole ur = new UserRole();
			ur.setUserId(userId);
			ur.setRoleId(roleId);
			list.add(ur);
		}
//		return userRoleMapper.batchUserRole(list);
		return userRoleService.saveBatch(list);
	}
}
