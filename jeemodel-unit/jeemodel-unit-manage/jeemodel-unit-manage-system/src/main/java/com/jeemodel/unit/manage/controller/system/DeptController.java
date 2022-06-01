package com.jeemodel.unit.manage.controller.system;

import java.util.Iterator;
import java.util.List;

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

import com.jeemodel.bean.http.Pong;
import com.jeemodel.bean.http.PongData;
import com.jeemodel.bean.http.PongUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.core.web.controller.BaseController;
import com.jeemodel.unit.manage.bean.dto.RoleDeptTreeselect;
import com.jeemodel.unit.manage.bean.dto.system.DeptTree;
import com.jeemodel.unit.manage.bean.model.TreeSelect;
import com.jeemodel.unit.manage.core.annotation.Log;
import com.jeemodel.unit.manage.core.bean.entity.Dept;
import com.jeemodel.unit.manage.core.constant.UserConstants;
import com.jeemodel.unit.manage.core.enums.BusinessType;
import com.jeemodel.unit.manage.core.utils.SecurityUtils;
import com.jeemodel.unit.manage.service.IDeptService;

/**
 * 部门信息
 * 
 * @author Rootfive
 */
@RestController
@RequestMapping("/manage/dept")
public class DeptController extends BaseController {
	@Autowired
	private IDeptService deptService;

	/**
	 * 获取部门列表
	 */
	@PreAuthorize("@ss.hasPermi('manage:dept:list')")
	@GetMapping("/list")
	public PongData<List<DeptTree>> list(Dept dept) {
		List<DeptTree> depts = deptService.selectDeptList(dept);
		return PongUtils.okData(depts);
	}

	/**
	 * 查询部门列表（排除节点）
	 */
	@PreAuthorize("@ss.hasPermi('manage:dept:list')")
	@GetMapping("/list/exclude/{id}")
	public PongData<List<DeptTree>> excludeChild(@PathVariable(value = "id", required = false) Long id) {
		List<DeptTree> depts = deptService.selectDeptList(new Dept());
		Iterator<DeptTree> it = depts.iterator();
		while (it.hasNext()) {
			Dept d = (Dept) it.next();
			if (d.getId().intValue() == id
					|| ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), id + "")) {
				it.remove();
			}
		}
		return PongUtils.okData(depts);
	}

	/**
	 * 根据部门编号获取详细信息
	 */
	@PreAuthorize("@ss.hasPermi('manage:dept:query')")
	@GetMapping(value = "/{id}")
	public PongData<Dept> getInfo(@PathVariable Long id) {
		deptService.checkDeptDataScope(id);
		return PongUtils.okData(deptService.selectDeptById(id));
	}

	/**
	 * 获取部门下拉树列表
	 */
	@GetMapping("/treeselect")
	public PongData<List<TreeSelect>> treeselect(Dept dept) {
		List<DeptTree> depts = deptService.selectDeptList(dept);
		List<TreeSelect> deptTreeSelect = deptService.buildDeptTreeSelect(depts);
		return PongUtils.okData(deptTreeSelect);
	}

	/**
	 * 加载对应角色部门列表树
	 */
	@GetMapping(value = "/roleDeptTreeselect/{roleId}")
	public PongData<RoleDeptTreeselect> roleDeptTreeselect(@PathVariable("roleId") Long roleId) {
		List<DeptTree> deptList = deptService.selectDeptList(new Dept());
		
		List<Long> checkedKeys = deptService.selectDeptListByRoleId(roleId);
		List<TreeSelect> depts = deptService.buildDeptTreeSelect(deptList);
		RoleDeptTreeselect roleDeptTreeselect = new RoleDeptTreeselect(checkedKeys, depts);
		return PongUtils.okData(roleDeptTreeselect);
	}

	/**
	 * 新增部门
	 */
	@PreAuthorize("@ss.hasPermi('manage:dept:add')")
	@Log(title = "部门管理", businessType = BusinessType.INSERT)
	@PostMapping
	public Pong add(@Validated @RequestBody Dept dept) {
		if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
			return PongUtils.fail("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
		}
		dept.setCreateBy(SecurityUtils.getUsername());
		return PongUtils.result(deptService.insertDept(dept));
	}

	/**
	 * 修改部门
	 */
	@PreAuthorize("@ss.hasPermi('manage:dept:edit')")
	@Log(title = "部门管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public Pong edit(@Validated @RequestBody Dept dept) {
		if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
			return PongUtils.fail("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
		} else if (dept.getParentId().equals(dept.getId())) {
			return PongUtils.fail("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
		} else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus())
				&& deptService.selectNormalChildrenDeptById(dept.getId()) > 0) {
			return PongUtils.fail("该部门包含未停用的子部门！");
		}
		dept.setUpdateBy(SecurityUtils.getUsername());
		return PongUtils.result(deptService.updateDept(dept));
	}

	/**
	 * 删除部门
	 */
	@PreAuthorize("@ss.hasPermi('manage:dept:remove')")
	@Log(title = "部门管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
	public Pong remove(@PathVariable Long id) {
		if (deptService.hasChildByDeptId(id)) {
			return PongUtils.fail("存在下级部门,不允许删除");
		}
		if (deptService.checkDeptExistUser(id)) {
			return PongUtils.fail("部门存在用户,不允许删除");
		}
		return PongUtils.result(deptService.deleteDeptById(id));
	}
}
