package com.jeemodel.unit.manage.service;

import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeemodel.unit.manage.bean.dto.system.RoleListReq;
import com.jeemodel.unit.manage.bean.dto.system.RoleSave;
import com.jeemodel.unit.manage.bean.dto.user.UserAuthRole;
import com.jeemodel.unit.manage.bean.entity.UserRole;
import com.jeemodel.unit.manage.core.bean.entity.Role;

/**
 * 角色业务层
 * 
 * @author Rootfive
 */
public interface IRoleService extends IService<Role>{
	/**
	 * 根据条件分页查询角色数据
	 * 
	 * @param role 角色信息
	 * @return 角色数据集合信息
	 */
	public List<Role> selectRoleList(RoleListReq role);

	/**
	 * 根据用户ID查询角色列表
	 * 
	 * @param userId 用户ID
	 * @return 角色列表
	 */
	public List<UserAuthRole> selectRolesByUserId(Long userId);

	/**
	 * 根据用户ID查询角色权限
	 * 
	 * @param userId 用户ID
	 * @return 权限列表
	 */
	public Set<String> selectRolePermissionByUserId(Long userId);

	/**
	 * 查询所有角色
	 * 
	 * @return 角色列表
	 */
	public List<Role> selectRoleAll();

	/**
	 * 根据用户ID获取角色选择框列表
	 * 
	 * @param userId 用户ID
	 * @return 选中角色ID列表
	 */
	public List<Long> selectRoleListByUserId(Long userId);

	/**
	 * 通过角色ID查询角色
	 * 
	 * @param id 角色ID
	 * @return 角色对象信息
	 */
	public Role selectRoleById(Long id);

	/**
	 * 校验角色名称是否唯一
	 * 
	 * @param role 角色信息
	 * @return 结果
	 */
	public String checkRoleNameUnique(Role role);

	/**
	 * 校验角色权限是否唯一
	 * 
	 * @param role 角色信息
	 * @return 结果
	 */
	public String checkRoleKeyUnique(Role role);

	/**
	 * 校验角色是否允许操作
	 * 
	 * @param role 角色信息
	 */
	public void checkRoleAllowed(Role role);

	/**
	 * 校验角色是否有数据权限
	 * 
	 * @param roleId 角色id
	 */
	public void checkRoleDataScope(Long roleId);

	/**
	 * 通过角色ID查询角色使用数量
	 * 
	 * @param roleId 角色ID
	 * @return 结果
	 */
	public int countUserRoleByRoleId(Long roleId);

	/**
	 * 新增保存角色信息
	 * 
	 * @param role 角色信息
	 * @return 结果
	 */
	public boolean insertRole(RoleSave role);

	/**
	 * 修改保存角色信息
	 * 
	 * @param role 角色信息
	 * @return 结果
	 */
	public boolean updateRole(RoleSave role);

	/**
	 * 修改角色状态
	 * 
	 * @param role 角色信息
	 * @return 结果
	 */
	public int updateRoleStatus(Role role);

	/**
	 * 修改数据权限信息
	 * 
	 * @param role 角色信息
	 * @return 结果
	 */
	public int authDataScope(RoleSave role);

	/**
	 * 通过角色ID删除角色
	 * 
	 * @param id 角色ID
	 * @return 结果
	 */
	public int deleteRoleById(Long id);

	/**
	 * 批量删除角色信息
	 * 
	 * @param ids 需要删除的角色ID
	 * @return 结果
	 */
	public int deleteRoleByIds(Long[] ids);

	/**
	 * 取消授权用户角色
	 * 
	 * @param userRole 用户和角色关联信息
	 * @return 结果
	 */
	public boolean deleteAuthUser(UserRole userRole);

	/**
	 * 批量取消授权用户角色
	 * 
	 * @param roleId  角色ID
	 * @param userIds 需要取消授权的用户数据ID
	 * @return 结果
	 */
	public boolean deleteAuthUsers(Long roleId, Long[] userIds);

	/**
	 * 批量选择授权用户角色
	 * 
	 * @param roleId  角色ID
	 * @param userIds 需要删除的用户数据ID
	 * @return 结果
	 */
	public boolean insertAuthUsers(Long roleId, Long[] userIds);
}
