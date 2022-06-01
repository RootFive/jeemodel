package com.jeemodel.unit.coding.dto;

import lombok.Data;

@Data
public class DBTableSchema {
	
	/** 数据库名 */
	private String tableSchema;
	
	/** 表名称 */
	private String tableName;
}
