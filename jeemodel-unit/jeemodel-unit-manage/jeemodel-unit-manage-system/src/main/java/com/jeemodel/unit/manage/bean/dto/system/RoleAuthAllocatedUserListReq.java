package com.jeemodel.unit.manage.bean.dto.system;

import java.util.HashMap;
import java.util.Map;

import com.jeemodel.unit.manage.core.bean.entity.User;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Rootfive
 *
 */
@ApiModel(description = "查询角色已分配用户-分页请求DTO")
@Data
@EqualsAndHashCode(callSuper=false)
public class RoleAuthAllocatedUserListReq extends User{
	
	private static final long serialVersionUID = 1L;
	
	/** 角色ID */ 
	private Long roleId;
	
	/** 其他参数 */
	private Map<String, Object> params;


	public Map<String, Object> getParams() {
		if (params == null) {
			params = new HashMap<>();
		}
		return params;
	}
	
}
