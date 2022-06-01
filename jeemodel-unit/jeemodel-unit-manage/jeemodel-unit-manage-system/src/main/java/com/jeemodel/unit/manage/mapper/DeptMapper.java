package com.jeemodel.unit.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeemodel.unit.manage.bean.dto.system.DeptTree;
import com.jeemodel.unit.manage.core.bean.entity.Dept;

/**
 * 部门管理 数据层
 * 
 * @author Rootfive
 */
public interface DeptMapper extends BaseMapper<Dept>{
	/**
	 * 查询部门管理数据
	 * 
	 * @param dept 部门信息
	 * @return 部门信息集合
	 */
	public List<DeptTree> selectDeptList(@Param("dept") Dept dept,@Param("dataScopeParams") Map<String, Object> dataScopeParams);

	/**
	 * 根据角色ID查询部门树信息
	 * 
	 * @param roleId            角色ID
	 * @param deptCheckStrictly 部门树选择项是否关联显示
	 * @return 选中部门列表
	 */
	public List<Long> selectDeptListByRoleId(@Param("roleId") Long roleId, @Param("deptCheckStrictly") boolean deptCheckStrictly);


	/**
	 * 根据ID查询所有子部门
	 * 
	 * @param id 部门ID
	 * @return 部门列表
	 */
	public List<Dept> selectChildrenDeptById(Long id);

	/**
	 * 根据ID查询所有子部门（正常状态）
	 * 
	 * @param id 部门ID
	 * @return 子部门数
	 */
	public int selectNormalChildrenDeptById(Long id);


	/**
	 * 查询部门是否存在用户
	 * 
	 * @param deptId 部门ID
	 * @return 结果
	 */
	public int checkDeptExistUser(Long deptId);


	/**
	 * 修改子元素关系
	 * 
	 * @param depts 子元素
	 * @return 结果
	 */
	public int updateDeptChildren(@Param("depts") List<Dept> depts);

}
