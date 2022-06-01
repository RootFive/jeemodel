package com.jeemodel.unit.coding.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DBTableListReq extends DBTable{
	
	/** 其他参数 */
	private Map<String, Object> params;


	public Map<String, Object> getParams() {
		if (params == null) {
			params = new HashMap<>();
		}
		return params;
	}

}
