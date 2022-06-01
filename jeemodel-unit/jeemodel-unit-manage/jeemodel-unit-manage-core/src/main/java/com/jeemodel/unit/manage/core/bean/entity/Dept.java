package com.jeemodel.unit.manage.core.bean.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeemodel.data.entity.BaseEntityCRUDX;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门表 manage_dept
 * 
 * @author Rootfive
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("manage_dept")
public class Dept extends BaseEntityCRUDX {
	private static final long serialVersionUID = 1L;
	
	/** 父部门ID */
	private Long parentId;

	/** 祖级列表 */
	private String ancestors;

	/** 部门名称 */
	@NotBlank(message = "部门名称不能为空")
	@Size(min = 0, max = 30, message = "部门名称长度不能超过30个字符")
	private String deptName;

	/** 显示顺序 */
	@NotBlank(message = "显示顺序不能为空")
	private String orderNum;

	/** 负责人 */
	private String leader;

	/** 联系电话 */
	@Size(min = 0, max = 11, message = "联系电话长度不能超过11个字符")
	private String phone;

	/** 邮箱 */
	@Email(message = "邮箱格式不正确")
	@Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
	private String email;

	/** 部门状态:0正常,1停用 */
	private String status;


	
	
	
	/** 父部门名称 */
	@TableField(exist = false)
	private String parentName;

//	/** 子部门 */
//	private List<Dept> children = new ArrayList<Dept>();


}
