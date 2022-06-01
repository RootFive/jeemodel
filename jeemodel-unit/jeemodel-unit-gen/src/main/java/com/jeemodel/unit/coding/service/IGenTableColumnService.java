package com.jeemodel.unit.coding.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeemodel.unit.coding.entity.GenTableColumn;

/**
 * 业务字段 服务层
 * 
 * @author Rootfive
 */
public interface IGenTableColumnService extends IService<GenTableColumn> {
	/**
	 * 查询业务字段列表
	 * 
	 * @param tableId 业务字段编号
	 * @return 业务字段集合
	 */
	public List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId);

	/**
	 * 新增业务字段
	 * 
	 * @param genTableColumn 业务字段信息
	 * @return 结果
	 */
	public int insertGenTableColumn(GenTableColumn genTableColumn);

	/**
	 * 修改业务字段
	 * 
	 * @param genTableColumn 业务字段信息
	 * @return 结果
	 */
	public int updateGenTableColumn(GenTableColumn genTableColumn);

	/**
	 * 删除业务字段
	 * 
	 * @param genTableColumns 列数据
	 * @return 结果
	 */
	boolean deleteGenTableColumns(List<GenTableColumn> genTableColumns);

	/**
	 * 批量删除业务字段
	 * 
	 * @param tableIds 需要删除的数据ID
	 * @return 结果
	 */
	boolean deleteGenTableColumnByTableIds(Long... tableIds);

}
