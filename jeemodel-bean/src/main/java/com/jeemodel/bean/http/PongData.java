package com.jeemodel.bean.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jeemodel.bean.enums.code.sub.IBaseSubCode;
import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel(description = "单个对象的响应")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PongData<T> extends Pong {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "数据对象/数据对象列表")
	private T data;
	
	
	/**
	 * @param echo
	 * @param baseSubCode
	 * @param data
	 */
	public PongData(String echo, IBaseSubCode baseSubCode, T data) {
		super(echo,baseSubCode);
		this.data = data;
	}
	
	/**
	 * @param echo
	 * @param baseSubCode
	 * @param data
	 * @param customMsg
	 */
	public PongData(String echo, IBaseSubCode baseSubCode, T data,String customMsg) {
		super(echo,baseSubCode,customMsg);
		this.data = data;
	}
	

	@Override
	public String toString() {
		JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
		if (echo != null) {
			builder.append("echo", echo);
		}
		builder.append("code", code);
		builder.append("msg", msg);
		builder.append("subCode", subCode);
		builder.append("subMsg", subMsg);
		if (data != null) {
			builder.append("data", data);
		}
		return builder.toString();
	}
}
