package com.jeemodel.unit.manage.bean.dto.system;

import java.util.HashMap;
import java.util.Map;

import com.jeemodel.unit.manage.bean.entity.UserLogLogin;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 	系统访问记录-分页请求DTO
 * @author Rootfive
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserLogLoginListReq extends UserLogLogin{

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
