package com.jeemodel.unit.coding.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jeemodel.unit.coding.entity.GenTableColumn;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "业务表详情")
public class GenTableDetailsVO {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "业务表信息")
    private GenTableIncludeColumn info;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "业务表字段列表")
	private List<GenTableColumn> rows;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "业务表同库的其他业务表信息（包含字段列表）")
	private List<GenTableIncludeColumn> tables;
}
