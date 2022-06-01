package com.jeemodel.unit.manage.bean.dto.user;

import java.util.List;

import com.jeemodel.unit.manage.core.bean.entity.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@ApiModel(description = "根据用户编号获取用户授权")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuth {
	
	@ApiModelProperty(value = "用户信息")
	private User user;
	
	@ApiModelProperty(value = "用户可以操作的角色信息")
	private List<UserAuthRole> roles;
}
