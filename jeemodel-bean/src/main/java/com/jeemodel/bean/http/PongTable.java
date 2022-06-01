package com.jeemodel.bean.http;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jeemodel.bean.enums.code.sub.IBaseSubCode;
import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@ApiModel(description = "列表查询的响应")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PongTable<T> extends PongData<List<T>>{

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "列表数据")
    private List<T> data;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "总记录数", example = "9999")
    private Long total;
	

	public PongTable(String echo, IBaseSubCode baseSubCode,List<T> data, Long total) {
		super(echo,baseSubCode, data);
		this.data = data;
		this.total = total;
	}
	
	public PongTable(String echo, IBaseSubCode baseSubCode,List<T> data, Long total,String customMsg) {
		super(echo,baseSubCode, data,customMsg);
		this.data = data;
		this.total = total;
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
		if (total != null) {
			builder.append("total", total);
		}
		if (data != null) {
			builder.append("data", data.size());
		}
		return builder.toString();
	}	
}
