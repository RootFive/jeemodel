package com.jeemodel.solution.file.dto;


import java.util.List;

import lombok.Data;

/**
 * @author Rootfive
 *
 * @param <T>
 */
@Data
public class ExcelSheet<T> {
	
    /**
     * 工作表名称
     */
    private String sheetName;

    /**
     * 导入导出数据列表
     */
    private List<T> listData;

    /**
     * 实体对象
     */
    public Class<T> dataClass;

    
    public ExcelSheet(String sheetName, List<T> listData, Class<T> clazz) {
		this.sheetName = sheetName;
		this.listData = listData;
		this.dataClass = clazz;
	}


}
