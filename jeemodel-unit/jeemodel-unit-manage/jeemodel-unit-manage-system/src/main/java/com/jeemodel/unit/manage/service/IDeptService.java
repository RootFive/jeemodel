package com.jeemodel.unit.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeemodel.unit.manage.bean.dto.system.DeptTree;
import com.jeemodel.unit.manage.bean.model.TreeSelect;
import com.jeemodel.unit.manage.core.bean.entity.Dept;

/**
 * 部门管理 服务层
 * 
 * @author Rootfive
 */
public interface IDeptService extends IService<Dept>{
	/**
	 * 查询部门管理数据
	 * 
	 * @param dept 部门信息
	 * @return 部门信息集合
	 */
	public List<DeptTree> selectDeptList(Dept dept);

	/**
	 * 构建前端所需要树结构
	 * 
	 * @param depts 部门列表
	 * @return 树结构列表
	 */
	public List<DeptTree> buildDeptTree(List<DeptTree> depts);

	/**
	 * 构建前端所需要下拉树结构
	 * 
	 * @param depts 部门列表
	 * @return 下拉树结构列表
	 */
	public List<TreeSelect> buildDeptTreeSelect(List<DeptTree> depts);

	/**
	 * 根据角色ID查询部门树信息
	 * 
	 * @param roleId 角色ID
	 * @return 选中部门列表
	 */
	public List<Long> selectDeptListByRoleId(Long roleId);

	/**
	 * 根据部门ID查询信息
	 * 
	 * @param id 部门ID
	 * @return 部门信息
	 */
	public Dept selectDeptById(Long id);

	/**
	 * 根据ID查询所有子部门（正常状态）
	 * 
	 * @param id 部门ID
	 * @return 子部门数
	 */
	public int selectNormalChildrenDeptById(Long id);

	/**
	 * 是否存在部门子节点
	 * 
	 * @param id 部门ID
	 * @return 结果
	 */
	public boolean hasChildByDeptId(Long id);

	/**
	 * 查询部门是否存在用户
	 * 
	 * @param deptId 部门ID
	 * @return 结果 true 存在 false 不存在
	 */
	public boolean checkDeptExistUser(Long deptId);

	/**
	 * 校验部门名称是否唯一
	 * 
	 * @param dept 部门信息
	 * @return 结果
	 */
	public String checkDeptNameUnique(Dept dept);

	/**
	 * 校验部门是否有数据权限
	 * 
	 * @param id 部门id
	 */
	public void checkDeptDataScope(Long id);

	/**
	 * 新增保存部门信息
	 * 
	 * @param dept 部门信息
	 * @return 结果
	 */
	public int insertDept(Dept dept);

	/**
	 * 修改保存部门信息
	 * 
	 * @param dept 部门信息
	 * @return 结果
	 */
	public int updateDept(Dept dept);

	/**
	 * 删除部门管理信息
	 * 
	 * @param id 部门ID
	 * @return 结果
	 */
	public boolean deleteDeptById(Long id);
}
