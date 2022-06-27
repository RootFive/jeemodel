package com.jeemodel.unit.manage.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeemodel.bean.exception.type.CheckException;
import com.jeemodel.core.utils.BlankUtils;
import com.jeemodel.core.utils.DataTypeConvertUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.core.utils.spring.SpringUtils;
import com.jeemodel.unit.manage.annotation.DataScope;
import com.jeemodel.unit.manage.aspectj.DataScopeContext;
import com.jeemodel.unit.manage.bean.dto.system.DeptTree;
import com.jeemodel.unit.manage.bean.model.TreeSelect;
import com.jeemodel.unit.manage.core.bean.entity.Dept;
import com.jeemodel.unit.manage.core.bean.entity.Role;
import com.jeemodel.unit.manage.core.bean.entity.User;
import com.jeemodel.unit.manage.core.constant.UserConstants;
import com.jeemodel.unit.manage.core.utils.SecurityUtils;
import com.jeemodel.unit.manage.mapper.DeptMapper;
import com.jeemodel.unit.manage.mapper.RoleMapper;
import com.jeemodel.unit.manage.service.IDeptService;

/**
 * 部门管理 服务实现
 * 
 * @author Rootfive
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept>  implements IDeptService {

	@Autowired
	private DeptMapper deptMapper;

	@Autowired
	private RoleMapper roleMapper;

	/**
	 * 查询部门管理数据
	 * 
	 * @param dept 部门信息
	 * @return 部门信息集合
	 */
	@Override
	@DataScope(deptAlias = "d")
	public List<DeptTree> selectDeptList(Dept dept) {
		Map<String, Object> dataScopeParams = DataScopeContext.get();
		return deptMapper.selectDeptList(dept,dataScopeParams);
	}

	/**
	 * 构建前端所需要树结构
	 * 
	 * @param depts 部门列表
	 * @return 树结构列表
	 */
	@Override
	public List<DeptTree> buildDeptTree(List<DeptTree> depts) {
		List<DeptTree> returnList = new ArrayList<DeptTree>();
		List<Long> tempList = new ArrayList<Long>();
		for (Dept dept : depts) {
			tempList.add(dept.getId());
		}
		for (Iterator<DeptTree> iterator = depts.iterator(); iterator.hasNext();) {
			DeptTree dept = (DeptTree) iterator.next();
			// 如果是顶级节点, 遍历该父节点的所有子节点
			if (!tempList.contains(dept.getParentId())) {
				recursionFn(depts, dept);
				returnList.add(dept);
			}
		}
		if (returnList.isEmpty()) {
			returnList = depts;
		}
		return returnList;
	}

	/**
	 * 构建前端所需要下拉树结构
	 * 
	 * @param depts 部门列表
	 * @return 下拉树结构列表
	 */
	@Override
	public List<TreeSelect> buildDeptTreeSelect(List<DeptTree> depts) {
		List<DeptTree> deptTrees = buildDeptTree(depts);
		return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
	}

	/**
	 * 根据角色ID查询部门树信息
	 * 
	 * @param roleId 角色ID
	 * @return 选中部门列表
	 */
	@Override
	public List<Long> selectDeptListByRoleId(Long roleId) {
		Role role = roleMapper.selectRoleById(roleId);
		return deptMapper.selectDeptListByRoleId(roleId, role.isDeptCheckStrictly());
	}

	/**
	 * 根据部门ID查询信息
	 * 
	 * @param id 部门ID
	 * @return 部门信息
	 */
	@Override
	public Dept selectDeptById(Long id) {
		return deptMapper.selectById(id);
	}

	/**
	 * 根据ID查询所有子部门（正常状态）
	 * 
	 * @param id 部门ID
	 * @return 子部门数
	 */
	@Override
	public int selectNormalChildrenDeptById(Long id) {
		return deptMapper.selectNormalChildrenDeptById(id);
	}

	/**
	 * 是否存在子节点
	 * 
	 * @param id 部门ID
	 * @return 结果
	 */
	@Override
	public boolean hasChildByDeptId(Long id) {
		int result = lambdaQuery().eq(Dept::getDelFlag, 0).eq(Dept::getParentId, id).count();;
		return result > 0;
	}

	/**
	 * 查询部门是否存在用户
	 * 
	 * @param deptId 部门ID
	 * @return 结果 true 存在 false 不存在
	 */
	@Override
	public boolean checkDeptExistUser(Long deptId) {
		int result = deptMapper.checkDeptExistUser(deptId);
		return result > 0;
	}

	/**
	 * 校验部门名称是否唯一
	 * 
	 * @param dept 部门信息
	 * @return 结果
	 */
	@Override
	public String checkDeptNameUnique(Dept dept) {
		Long deptId = BlankUtils.isNull(dept.getId()) ? -1L : dept.getId();
//		Dept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
		Dept info = lambdaQuery().eq(Dept::getDeptName, dept.getDeptName()).eq(Dept::getParentId,dept.getParentId()).one();
		
		if (BlankUtils.isNotNull(info) && info.getId().longValue() != deptId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校验部门是否有数据权限
	 * 
	 * @param deptId 部门id
	 */
	@Override
	public void checkDeptDataScope(Long id) {
		if (!User.isAdmin(SecurityUtils.getUserId())) {
			Dept dept = new Dept();
			dept.setId(id);
			List<DeptTree> depts = SpringUtils.getAopProxy(this).selectDeptList(dept);
			if (BlankUtils.isBlank(depts)) {
				throw new CheckException("没有权限访问部门数据！");
			}
		}
	}

	/**
	 * 新增保存部门信息
	 * 
	 * @param dept 部门信息
	 * @return 结果
	 */
	@Override
	public int insertDept(Dept dept) {
//		Dept info = deptMapper.selectDeptById(dept.getParentId());
		Dept info = deptMapper.selectById(dept.getParentId());
		// 如果父节点不为正常状态,则不允许新增子节点
		if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
			throw new CheckException("部门停用，不允许新增");
		}
		dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
		return deptMapper.insert(dept);
//		return deptMapper.insertDept(dept);
	}

	/**
	 * 修改保存部门信息
	 * 
	 * @param dept 部门信息
	 * @return 结果
	 */
	@Override
	public int updateDept(Dept dept) {
//		Dept newParentDept = deptMapper.selectDeptById(dept.getParentId());
		Dept newParentDept = deptMapper.selectById(dept.getParentId());
		Dept oldDept = deptMapper.selectById(dept.getId());
		if (BlankUtils.isNotNull(newParentDept) && BlankUtils.isNotNull(oldDept)) {
			String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getId();
			String oldAncestors = oldDept.getAncestors();
			dept.setAncestors(newAncestors);
			updateDeptChildren(dept.getId(), newAncestors, oldAncestors);
		}
//		int result = deptMapper.updateDept(dept);
		int result = deptMapper.updateById(dept);
		if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()) && StringUtils.isNotEmpty(dept.getAncestors())
				&& !StringUtils.equals("0", dept.getAncestors())) {
			// 如果该部门是启用状态，则启用该部门的所有上级部门
			updateParentDeptStatusNormal(dept);
		}
		return result;
	}

	/**
	 * 修改该部门的父级部门状态
	 * 
	 * @param dept 当前部门
	 */
	private void updateParentDeptStatusNormal(Dept dept) {
		String ancestors = dept.getAncestors();
		Long[] deptIds = DataTypeConvertUtils.toLongArray(ancestors);
//		deptMapper.updateDeptStatusNormal(deptIds);
		lambdaUpdate().set(Dept::getStatus, 0).in(Dept::getId,Arrays.asList(deptIds)).update();
	}

	/**
	 * 修改子元素关系
	 * 
	 * @param id       被修改的部门ID
	 * @param newAncestors 新的父ID集合
	 * @param oldAncestors 旧的父ID集合
	 */
	public void updateDeptChildren(Long id, String newAncestors, String oldAncestors) {
		List<Dept> children = deptMapper.selectChildrenDeptById(id);
		for (Dept child : children) {
			child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
		}
		if (children.size() > 0) {
			deptMapper.updateDeptChildren(children);
		}
	}

	/**
	 * 删除部门管理信息
	 * 
	 * @param id 部门ID
	 * @return 结果
	 */
	@Override
	public boolean deleteDeptById(Long id) {
		return lambdaUpdate().set(Dept::getDelFlag, 2).eq(Dept::getId,id).update();

	}

	/**
	 * 递归列表
	 */
	private void recursionFn(List<DeptTree> list, DeptTree t) {
		// 得到子节点列表
		List<DeptTree> childList = getChildList(list, t);
		t.setChildren(childList);
		for (DeptTree tChild : childList) {
			if (hasChild(list, tChild)) {
				recursionFn(list, tChild);
			}
		}
	}

	/**
	 * 得到子节点列表
	 */
	private List<DeptTree> getChildList(List<DeptTree> list, DeptTree t) {
		List<DeptTree> tlist = new ArrayList<DeptTree>();
		Iterator<DeptTree> it = list.iterator();
		while (it.hasNext()) {
			DeptTree n = (DeptTree) it.next();
			if (BlankUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getId().longValue()) {
				tlist.add(n);
			}
		}
		return tlist;
	}

	/**
	 * 判断是否有子节点
	 */
	private boolean hasChild(List<DeptTree> list, DeptTree t) {
		return getChildList(list, t).size() > 0;
	}
}
