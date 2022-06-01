package com.jeemodel.unit.manage.bean.dto.user;

import com.jeemodel.unit.manage.core.bean.entity.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 	用户-保存（新增、修改）DTO
 * @author Rootfive
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserSave extends User{
	
	private static final long serialVersionUID = 1L;

	/** 岗位组 */
	private Long[] postIds;
	
	/** 角色组 */
	private Long[] roleIds;
}
