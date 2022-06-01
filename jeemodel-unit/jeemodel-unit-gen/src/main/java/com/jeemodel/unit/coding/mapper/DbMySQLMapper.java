package com.jeemodel.unit.coding.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jeemodel.unit.coding.dto.DBTableListReq;
import com.jeemodel.unit.coding.dto.DBTableSchema;
import com.jeemodel.unit.coding.entity.GenTable;
import com.jeemodel.unit.coding.entity.GenTableColumn;

@Mapper
public interface DbMySQLMapper {

	/**
	 * 查询据库列表
	 * 
	 * @param genTable 业务信息
	 * @return 数据库表集合
	 */
	List<GenTable> selectDbTableList(DBTableListReq listReq);

	/**
	 * 查询据库列表
	 * 
	 * @param listTableSchema 表名称组
	 * @return 数据库表集合
	 */
	List<GenTable> selectDbTableListByNames(List<DBTableSchema> listTableSchema);
	
	/**
	 * 根据表名称查询列信息
	 * 
	 * @param tableName 表名称
	 * @return 列信息
	 */
	List<GenTableColumn> selectDbTableColumnsByName(GenTable table);
	
}
