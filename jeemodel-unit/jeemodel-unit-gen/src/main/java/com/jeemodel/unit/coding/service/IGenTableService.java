package com.jeemodel.unit.coding.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeemodel.unit.coding.dto.DBTableListReq;
import com.jeemodel.unit.coding.dto.EditGenTable;
import com.jeemodel.unit.coding.dto.GenTableIncludeColumn;
import com.jeemodel.unit.coding.dto.GenTableListReq;
import com.jeemodel.unit.coding.entity.GenTable;

/**
 * 业务 服务层
 * 
 * @author Rootfive
 */
public interface IGenTableService extends IService<GenTable> {
	/**
	 * 查询业务列表
	 * 
	 * @param listReq 业务信息
	 * @return 业务集合
	 */
	public List<GenTable> selectGenTableList(GenTableListReq listReq);

	/**
	 * 查询据库列表
	 * 
	 * @param genTable 业务信息
	 * @return 数据库表集合
	 */
	public List<GenTable> selectDbTableList(DBTableListReq listReq);

	/**
	 * 查询据库列表
	 * 
	 * @param tableNames 表名称组
	 * @return 数据库表集合
	 */
	public List<GenTable> selectDbTableListByNames(String ... dbTableSchemas);

	/**
	 * 查询所有表信息
	 * 
	 * @return 表信息集合
	 */
	public List<GenTableIncludeColumn> allGenTableIncludeColumn();

	/**
	 * 查询业务信息
	 * 
	 * @param id 业务ID
	 * @return 业务信息
	 */
	public GenTableIncludeColumn selectGenTableById(Long id);

	/**
	 * 修改业务
	 * 
	 * @param genTable 业务信息
	 * @return 结果
	 */
	public void updateGenTable(EditGenTable genTable);

	/**
	 * 删除业务信息
	 * 
	 * @param tableIds 需要删除的表数据ID
	 * @return 结果
	 */
	public void deleteGenTableByIds(Long[] tableIds);

	/**
	 * 导入表结构
	 * 
	 * @param tableList 导入表列表
	 */
	public void importGenTable(List<GenTable> tableList);

	/**
	 * 预览代码
	 * 
	 * @param tableId 表编号
	 * @return 预览数据列表
	 */
	public Map<String, String> previewCode(Long tableId);

	/**
	 * 生成代码（下载方式）
	 * 
	 * @param tableName 表名称
	 * @return 数据
	 */
	public byte[] downloadCode(Long ... tableIds);

	/**
	 * 生成代码（自定义路径）
	 * 
	 * @param tableId 表ID
	 * @return 数据
	 */
	public void generatorCode(Long tableId);

	/**
	 * 同步数据库
	 * 
	 * @param tableId 表ID
	 */
	public void synchDb(Long tableId);

	/**
	 * 批量生成代码（下载方式）
	 * 
	 * @param tableNames 表数组
	 * @return 数据
	 */
//	public byte[] downloadCode(String[] tableNames);

	/**
	 * 修改保存参数校验
	 * 
	 * @param genTable 业务信息
	 */
	public void validateEdit(EditGenTable genTable);
}
