package com.jeemodel.bean.rpc;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页请求数据
 * @author Rootfive
 *
 */
@Deprecated
@Data
@ApiModel(description = "分页请求数据")
public class PingPage {
	
	@ApiModelProperty(value = "【必传】,当前记录起始索引", example = "1", required = true)
    @NotEmpty
	private Integer pageNum;

	@ApiModelProperty(value = "【必传】,每页显示记录数", example = "10", required = true)
    @NotEmpty
	private Integer pageSize;

	@ApiModelProperty(value = "排序列", example = "id")
	private String orderByColumn;

	@ApiModelProperty(value = "排序的方向desc或者asc", example = "desc")
	private String isAsc;
}
