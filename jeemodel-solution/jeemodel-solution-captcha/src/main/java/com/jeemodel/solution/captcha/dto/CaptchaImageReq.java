package com.jeemodel.solution.captcha.dto;

import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "CaptchaImageReq", description = "图片验证码请求信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaImageReq {

	@ApiModelProperty(value = "应用场景、情景", example = "manage_logon")
	private String scene;

	@ApiModelProperty(value = "验证码图片-宽度", example = "160")
	private int width;

	@ApiModelProperty(value = "验证码图片-高度", example = "60")
	private int height;

	@Override
	public String toString() {
		JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
		builder.append("scene", scene);
		builder.append("width", width);
		builder.append("height", height);
		return builder.toString();
	}
}
