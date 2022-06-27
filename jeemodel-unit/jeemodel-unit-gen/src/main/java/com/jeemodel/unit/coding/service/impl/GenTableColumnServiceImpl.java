package com.jeemodel.unit.coding.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeemodel.unit.coding.entity.GenTableColumn;
import com.jeemodel.unit.coding.mapper.GenTableColumnMapper;
import com.jeemodel.unit.coding.service.IGenTableColumnService;

/**
 * 业务字段 服务层实现
 * 
 * @author Rootfive
 */
@DS("gen")
@Service
public class GenTableColumnServiceImpl extends ServiceImpl<GenTableColumnMapper, GenTableColumn> implements IGenTableColumnService {
	
	
	@Autowired
	private GenTableColumnMapper genTableColumnMapper;

	/**
	 * 查询业务字段列表
	 * 
	 * @param tableId 业务字段编号
	 * @return 业务字段集合
	 */
	@Override
	public List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId) {
		return lambdaQuery().eq(GenTableColumn::getTableId, tableId).list();
//		return genTableColumnMapper.selectGenTableColumnListByTableId(tableId);
	}

	/**
	 * 新增业务字段
	 * 
	 * @param genTableColumn 业务字段信息
	 * @return 结果
	 */
	@Override
	public int insertGenTableColumn(GenTableColumn genTableColumn) {
//		return genTableColumnMapper.insertGenTableColumn(genTableColumn);
		return genTableColumnMapper.insert(genTableColumn);
	}

	/**
	 * 修改业务字段
	 * 
	 * @param genTableColumn 业务字段信息
	 * @return 结果
	 */
	@Override
	public int updateGenTableColumn(GenTableColumn genTableColumn) {
//		return genTableColumnMapper.updateGenTableColumn(genTableColumn);
		return genTableColumnMapper.updateById(genTableColumn);
	}

	
	/**
	 * 删除业务字段
	 * 
	 * @param genTableColumns 列数据
	 * @return 结果
	 */
	@Override
	public boolean deleteGenTableColumns(List<GenTableColumn> genTableColumns) {
		List<Long> columnIds = genTableColumns.stream().map(GenTableColumn::getId).collect(Collectors.toList());
		LambdaQueryWrapper<GenTableColumn> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.in(GenTableColumn::getId, columnIds);
		return remove(lambdaQueryWrapper);
	}

	
	/**
	 * 批量删除业务字段
	 * 
	 * @param tableIds 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public boolean deleteGenTableColumnByTableIds(Long... tableIds) {
		LambdaQueryWrapper<GenTableColumn> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.in(GenTableColumn::getTableId, Arrays.asList(tableIds));
		return remove(lambdaQueryWrapper);
	}

}
