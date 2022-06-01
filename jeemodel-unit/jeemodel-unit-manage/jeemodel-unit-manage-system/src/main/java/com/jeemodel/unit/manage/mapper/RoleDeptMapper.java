package com.jeemodel.unit.manage.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeemodel.unit.manage.bean.entity.RoleDept;

/**
 * 角色与部门关联表 数据层
 * 
 * @author Rootfive
 */
public interface RoleDeptMapper extends BaseMapper<RoleDept>{

	/**
	 * 批量新增角色部门信息
	 * 
	 * @param roleDeptList 角色部门列表
	 * @return 结果
	 */
	public int batchRoleDept(List<RoleDept> roleDeptList);
}
