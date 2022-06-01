package com.jeemodel.unit.coding.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeemodel.unit.coding.dto.GenTableIncludeColumn;
import com.jeemodel.unit.coding.entity.GenTable;

/**
 * 业务 数据层
 * 
 * @author Rootfive
 */
public interface GenTableMapper extends BaseMapper<GenTable> {
	/**
	 * 查询业务列表
	 * 
	 * @param genTable 业务信息
	 * @return 业务集合
	 */
	List<GenTable> selectGenTableList(GenTable genTable);

	/**
	 * 查询所有表信息
	 * 
	 * @return 表信息集合
	 */
	List<GenTableIncludeColumn> allGenTableIncludeColumn();

	/**
	 * 查询表ID业务信息
	 * 
	 * @param id 业务ID
	 * @return 业务信息
	 */
	GenTableIncludeColumn selectGenTableById(Long id);

	/**
	 * 查询表名称业务信息
	 * 
	 * @param tableName 表名称
	 * @return 业务信息
	 */
	GenTableIncludeColumn selectGenTableByName(@Param("tableDb") String tableDb, @Param("tableName") String tableName);
}
