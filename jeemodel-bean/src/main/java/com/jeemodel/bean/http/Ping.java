package com.jeemodel.bean.http;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Deprecated
@Data
@ApiModel(description = "请求参数")
public class Ping<T> {
	
	@ApiModelProperty(value = "【必传】,API回声，会在当次响应返回", example = "7daf70d482bd6cf7", required = true)
    @NotEmpty
	private String echo;
	
	@ApiModelProperty(value = "【必传】,请求公共参数", required = true)
	@Valid
	@NotNull
	private PingTop top;
	
	@ApiModelProperty(value = "业务数据", required = true)
	@NotNull
	@Valid
	private T data;

	@Override
	public String toString() {
		JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
		if (echo != null)
			builder.append("echo", echo);
		if (top != null)
			builder.append("top", top);
		if (data != null)
			builder.append("data", data);
		return builder.toString();
	}
}
