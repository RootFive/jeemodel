package com.jeemodel.unit.manage.core.bean.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeemodel.bean.annotation.Excel;
import com.jeemodel.data.entity.BaseEntityCRUDX;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 角色表 manage_role
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("manage_role")
public class Role extends BaseEntityCRUDX {
		
	private static final long serialVersionUID = 1L;


	/** 角色名称 */
	@NotBlank(message = "角色名称不能为空")
	@Size(min = 0, max = 30, message = "角色名称长度不能超过30个字符")
	@Excel(name = "角色名称")
	private String roleName;

	/** 角色权限 */
	@NotBlank(message = "权限字符不能为空")
	@Size(min = 0, max = 100, message = "权限字符长度不能超过100个字符")
	@Excel(name = "角色权限")
	private String roleKey;

	/** 角色排序 */
	@NotBlank(message = "显示顺序不能为空")
	@Excel(name = "角色排序")
	private String roleSort;

	/** 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限） */
	@Excel(name = "数据范围", readConverterExp = "1=所有数据权限,2=自定义数据权限,3=本部门数据权限,4=本部门及以下数据权限,5=仅本人数据权限")
	private String dataScope;

	/** 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示） */
	private boolean menuCheckStrictly;

	/** 部门树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示 ） */
	private boolean deptCheckStrictly;

	/** 角色状态（0正常 1停用） */
	@Excel(name = "角色状态", readConverterExp = "0=正常,1=停用")
	private String status;


	
	//TODO 未完成字段
//	/** 用户是否存在此角色标识 默认不存在 */
//	private boolean flag = false;
//
//	/** 菜单组 */
//	private Long[] menuIds;
//
//	/** 部门组（数据权限） */
//	private Long[] deptIds;


	
	public Role(Long roleId) {
		this.id = roleId;
	}


	public boolean isAdmin() {
		return isAdmin(this.roleKey);
	}

	public static boolean isAdmin(String roleKey) {
		return StringUtils.isNotBlank(roleKey) && StringUtils.equals("admin", roleKey);
	}



}
