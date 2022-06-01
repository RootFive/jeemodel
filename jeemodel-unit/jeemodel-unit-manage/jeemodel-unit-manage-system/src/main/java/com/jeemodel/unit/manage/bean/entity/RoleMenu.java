package com.jeemodel.unit.manage.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeemodel.data.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色和菜单关联 manage_role_menu
 * 
 * @author Rootfive
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("manage_role_menu")
public class RoleMenu extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	/** 角色ID */
	private Long roleId;

	/** 菜单ID */
	private Long menuId;
}
