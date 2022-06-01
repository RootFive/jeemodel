package com.jeemodel.solution.captcha.dto;

import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "CaptchaCheckReq", description = "校验-验证码请求信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaCheckReq {

	@ApiModelProperty(value = "验证码uuid", example = "bff12301-bcd3-4b07-9439-8197015c5523")
	private String uuid;

	@ApiModelProperty(value = "验证码code", example = "24")
	private String code;

	@Override
	public String toString() {
		JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
		builder.append("uuid", uuid);
		builder.append("code", code);
		return builder.toString();
	}
}
