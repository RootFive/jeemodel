package com.jeemodel.unit.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeemodel.unit.manage.bean.dto.system.RoleListReq;
import com.jeemodel.unit.manage.core.bean.entity.Role;

/**
 * 角色表 数据层
 * 
 * @author Rootfive
 */
public interface RoleMapper extends BaseMapper<Role>{
	/**
	 * 根据条件分页查询角色数据
	 * 
	 * @param role 角色信息
	 * @return 角色数据集合信息
	 */
	public List<Role> selectRoleList(@Param("role") RoleListReq role,@Param("dataScopeParams") Map<String, Object> dataScopeParams);

	/**
	 * 根据用户ID查询角色
	 * 
	 * @param userId 用户ID
	 * @return 角色列表
	 */
	public List<Role> selectRolePermissionByUserId(Long userId);

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
	 * 根据用户ID查询角色
	 * 
	 * @param userName 用户名
	 * @return 角色列表
	 */
	public List<Role> selectRolesByUserName(String userName);

	/**
	 * 校验角色名称是否唯一
	 * 
	 * @param roleName 角色名称
	 * @return 角色信息
	 */
	public Role checkRoleNameUnique(String roleName);

	/**
	 * 校验角色权限是否唯一
	 * 
	 * @param roleKey 角色权限
	 * @return 角色信息
	 */
	public Role checkRoleKeyUnique(String roleKey);



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
}
