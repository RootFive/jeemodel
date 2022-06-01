package com.jeemodel.unit.coding.dto;

import java.util.List;

import javax.validation.Valid;

import com.jeemodel.unit.coding.entity.GenTable;
import com.jeemodel.unit.coding.entity.GenTableColumn;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GenTableIncludeColumn extends GenTable {
	
	private static final long serialVersionUID = 1L;
	
	/** 主键信息 */
	private GenTableColumn pkColumn;
	
	/** 子表信息 */
	private GenTableIncludeColumn subTable;
	
	/** 表列信息 */
	@Valid
	private List<GenTableColumn> columns;
	
	
	/** 树编码字段 */
	private String treeCode;
	
	/** 树父编码字段 */
	private String treeParentCode;

	/** 树名称字段 */
	private String treeName;

	/** 上级菜单ID字段 */
	private String parentMenuId;

	/** 上级菜单名称字段 */
	private String parentMenuName;

}
