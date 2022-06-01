package com.jeemodel.unit.coding.model;

import lombok.Data;

@Data
public class BaseTypeModel {

	/** 表对应实体类的基类BaseEntity类型 */
	private String baseEntityType;
	
	/** 表对应DTO的基类BaseDTO类型 */
	private String baseDTOType;
	
	/** 表对应编辑保存DTO的基类BaseEditSave类型 */
	private String baseEditSaveType;

	public BaseTypeModel(String baseEntityType, String baseDTOType, String baseEditSaveType) {
		super();
		this.baseEntityType = baseEntityType;
		this.baseDTOType = baseDTOType;
		this.baseEditSaveType = baseEditSaveType;
	}
}
