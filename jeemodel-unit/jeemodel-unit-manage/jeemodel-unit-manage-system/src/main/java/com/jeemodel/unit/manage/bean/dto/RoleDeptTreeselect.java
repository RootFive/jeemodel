package com.jeemodel.unit.manage.bean.dto;

import java.util.List;

import com.jeemodel.unit.manage.bean.model.TreeSelect;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "角色部门列表树（列表下拉树）")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDeptTreeselect {
	
	@ApiModelProperty(value = "部门ID列表")
	private List<Long> checkedKeys;
	
	@ApiModelProperty(value = "部门下拉树")
	private List<TreeSelect> depts;
}
