package com.jeemodel.unit.manage.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeemodel.data.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户和角色关联 manage_user_role
 * 
 * @author Rootfive
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("manage_user_role")
public class UserRole extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	/** 用户ID */
	private Long userId;

	/** 角色ID */
	private Long roleId;
}
