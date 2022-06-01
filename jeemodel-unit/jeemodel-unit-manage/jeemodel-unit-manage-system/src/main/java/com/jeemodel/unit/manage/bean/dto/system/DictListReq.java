package com.jeemodel.unit.manage.bean.dto.system;

import java.util.HashMap;
import java.util.Map;

import com.jeemodel.unit.manage.core.bean.entity.Dict;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class DictListReq extends Dict{
	
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
