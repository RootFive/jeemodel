package com.jeemodel.solution.captcha.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@ApiModel(description = "图片验证码响应信息")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaImageResp {
	
	@ApiModelProperty(value = "验证码开关", example = "true")
	private boolean  captchaOnOff;
	
	@ApiModelProperty(value = "验证码uuid", example = "0c472be9ee3f41acab864861b4aa9bee")
	private String uuid;
	
	@ApiModelProperty(value = "验证码Base64编码", example = "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")
	private String img;
	
}
