package com.jeemodel.unit.manage.bean.dto;

import java.util.List;

import com.jeemodel.unit.manage.bean.model.TreeSelect;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "角色菜单列表下拉树")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuTreeselect {

	@ApiModelProperty(value = "菜单ID列表")
	private List<Long> checkedKeys;
	
	@ApiModelProperty(value = "菜单下拉树")
	private List<TreeSelect> menus ;
}
