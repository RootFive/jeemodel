package com.jeemodel.unit.manage.bean.dto.system;

import java.util.HashMap;
import java.util.Map;

import com.jeemodel.unit.manage.core.bean.entity.Role;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Rootfive
 *
 */
@ApiModel(description = "角色-分页请求DTO")
@Data
@EqualsAndHashCode(callSuper=false)
public class RoleListReq extends Role{
	
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
