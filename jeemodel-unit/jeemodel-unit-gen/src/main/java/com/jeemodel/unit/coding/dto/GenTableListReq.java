package com.jeemodel.unit.coding.dto;

import java.util.HashMap;
import java.util.Map;

import com.jeemodel.unit.coding.entity.GenTable;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(description = "代码生成业务-分页请求DTO")
@Data
@EqualsAndHashCode(callSuper = false)
public class GenTableListReq extends GenTable {
	
	private static final long serialVersionUID = 1L;
	
	/** 其他参数 */
	private Map<String, Object> params;


	public Map<String, Object> getParams() {
		if (params == null) {
			params = new HashMap<>();
		}
		return params;
	}

}
