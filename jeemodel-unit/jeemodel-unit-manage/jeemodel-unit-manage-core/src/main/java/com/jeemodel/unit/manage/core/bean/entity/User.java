package com.jeemodel.unit.manage.core.bean.entity;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jeemodel.bean.annotation.Excel;
import com.jeemodel.bean.annotation.Excel.Type;
import com.jeemodel.data.entity.BaseEntityCRUDX;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用户对象 manage_user
 * 
 * @author Rootfive
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("manage_user")
public class User extends BaseEntityCRUDX {
	private static final long serialVersionUID = 1L;

	/** 用户账号 */
	@NotBlank(message = "用户账号不能为空")
	@Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
	@Excel(name = "登录名称")
	private String userName;

	/** 用户昵称 */
	@Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
	@Excel(name = "用户名称")
	private String nickName;
	
	
	/** 用户类型 */
	@Excel(name = "用户类型", readConverterExp = "0=系统用户,1=注册用户")
	private String userType;

	/** 用户性别 */
	@Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
	private String sex;	

	/** 密码 */
	private String password;
	
	/** 用户邮箱 */
	@Email(message = "邮箱格式不正确")
	@Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
	@Excel(name = "用户邮箱")
	private String email;

	/** 手机号码 */
	@Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
	@Excel(name = "手机号码")
	private String phonenumber;	
	
	/** 部门ID */
	@Excel(name = "部门编号", type = Type.IMPORT)
	private Long deptId;

	/** 用户头像 */
	private String avatar;

	/** 最后登录IP */
	@Excel(name = "最后登录IP", type = Type.EXPORT)
	private String loginIp;

	/** 最后登录时间 */
	@Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
	private LocalDateTime loginDate;
	
	

	/** 帐号状态（0正常 1停用） */
	@Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
	private String status;
	
	
	
	
	
	
	
	
	
	
//	/** 盐加密 */
//	private String salt;

//	/** 部门对象 */
//	@Excels({ @Excel(name = "部门名称", targetAttr = "deptName", type = Type.EXPORT),
//			@Excel(name = "部门负责人", targetAttr = "leader", type = Type.EXPORT) })
//	private Dept dept;

//	/** 角色对象 */
//	private List<Role> roles;

//	/** 角色组 */
//	private Long[] roleIds;

//	/** 岗位组 */
//	private Long[] postIds;


	
	
	public User(Long userId) {
		this.id = userId;
	}

	public boolean isAdmin() {
		return isAdmin(this.id);
	}

	public static boolean isAdmin(Long userId) {
		return userId != null && 1L == userId;
	}
	
	@JsonIgnore
	@JsonProperty
	public String getPassword() {
		return password;
	}


}
