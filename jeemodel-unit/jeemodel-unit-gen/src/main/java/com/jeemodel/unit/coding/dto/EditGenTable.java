package com.jeemodel.unit.coding.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.jeemodel.unit.coding.entity.GenTable;
import com.jeemodel.unit.coding.entity.GenTableColumn;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EditGenTable extends GenTable {

	private static final long serialVersionUID = 1L;

	
	/** 表列信息 */
	@Valid
	private List<GenTableColumn> columns;
	
	
	/** 请求参数 */
	private Map<String, Object> params;

	public Map<String, Object> getParams() {
		if (params == null) {
			params = new HashMap<>();
		}
		return params;
	}
}
