package com.jeemodel.unit.manage.bean.dto.user;


import com.jeemodel.unit.manage.core.bean.entity.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "用户简介信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

	@ApiModelProperty(value = "用户对象")
	private User user;

	@ApiModelProperty(value = "角色名")
	String roleGroup;

	@ApiModelProperty(value = "岗位名")
	String postGroup;
}
