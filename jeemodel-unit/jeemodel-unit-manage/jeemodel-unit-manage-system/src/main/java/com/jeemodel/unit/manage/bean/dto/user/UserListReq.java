package com.jeemodel.unit.manage.bean.dto.user;

import java.util.HashMap;
import java.util.Map;

import com.jeemodel.unit.manage.core.bean.entity.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 	用户-分页请求DTO
 * @author Rootfive
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserListReq extends User{
	
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
