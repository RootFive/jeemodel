package com.jeemodel.unit.manage.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeemodel.data.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色和部门关联 manage_role_dept
 * 
 * @author Rootfive
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("manage_role_dept")
public class RoleDept extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 角色ID */
	private Long roleId;

	/** 部门ID */
	private Long deptId;
}
