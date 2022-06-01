package com.jeemodel.unit.manage.bean.dto;


import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "用户登录结果")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResult {
	
	@ApiModelProperty(value = "登录token", example = "jeemodel")
	private String token;

	@Override
	public String toString() {
		JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
		if (token != null)
			builder.append("token", token);
		return builder.toString();
	}
}
