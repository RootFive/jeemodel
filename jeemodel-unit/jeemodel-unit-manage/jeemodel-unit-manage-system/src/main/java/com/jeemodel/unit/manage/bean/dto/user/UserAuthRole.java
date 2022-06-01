package com.jeemodel.unit.manage.bean.dto.user;

import com.jeemodel.unit.manage.core.bean.entity.Role;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@ApiModel(description = "根据用户编号获取授权角色")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class UserAuthRole  extends Role{
	
	private static final long serialVersionUID = 1L;
	
	/** 用户是否存在此角色标识 默认不存在 */
	private boolean flag = false;
}
