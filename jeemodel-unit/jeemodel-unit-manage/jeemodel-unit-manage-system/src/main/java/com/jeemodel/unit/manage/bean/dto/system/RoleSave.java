package com.jeemodel.unit.manage.bean.dto.system;

import com.jeemodel.unit.manage.core.bean.entity.Role;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色-更新部门数据权限
 * 
 * @author Rootfive
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleSave extends Role {

	private static final long serialVersionUID = 1L;

	/** 菜单组 */
	private Long[] menuIds;

	/** 部门组（数据权限） */
	private Long[] deptIds;

}
