package com.jeemodel.unit.manage.bean.dto;

import java.util.Set;

import com.jeemodel.unit.manage.core.bean.entity.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel( description = "用户的角色权限信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRolesPermissions {

	@ApiModelProperty(value = "用户对象")
	private User user;

	@ApiModelProperty(value = "角色集合")
	private Set<String> roles;

	@ApiModelProperty(value = "权限集合")
	private Set<String> permissions;
}
