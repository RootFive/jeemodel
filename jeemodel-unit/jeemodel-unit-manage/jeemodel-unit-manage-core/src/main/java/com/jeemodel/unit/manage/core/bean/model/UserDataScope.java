package com.jeemodel.unit.manage.core.bean.model;

import java.util.List;

import com.jeemodel.bean.annotation.Excel;
import com.jeemodel.bean.annotation.Excels;
import com.jeemodel.bean.annotation.Excel.Type;
import com.jeemodel.unit.manage.core.bean.entity.Dept;
import com.jeemodel.unit.manage.core.bean.entity.Role;
import com.jeemodel.unit.manage.core.bean.entity.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 	用户-数据权限
 * @author Rootfive
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDataScope extends User {
	
	private static final long serialVersionUID = 1L;

	/** 角色对象 */
	private List<Role> roles;
	
	/** 部门对象 */
	@Excels({ @Excel(name = "部门名称", targetAttr = "deptName", type = Type.EXPORT),
			@Excel(name = "部门负责人", targetAttr = "leader", type = Type.EXPORT) })
	private Dept dept;
	
	
}
