package com.jeemodel.unit.manage.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeemodel.core.utils.DateTimeUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.unit.manage.bean.dto.system.DictDataListReq;
import com.jeemodel.unit.manage.core.bean.entity.DictData;
import com.jeemodel.unit.manage.core.utils.DictUtils;
import com.jeemodel.unit.manage.mapper.DictDataMapper;
import com.jeemodel.unit.manage.service.IDictDataService;

/**
 * 字典 业务层处理
 * 
 * @author Rootfive
 */
@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements IDictDataService {
	@Autowired
	private DictDataMapper dictDataMapper;

	/**
	 * 根据条件分页查询字典数据
	 * 
	 * @param dictData 字典数据信息
	 * @return 字典数据集合信息
	 */
	@Override
	public List<DictData> selectDictDataList(DictDataListReq listReq) {
		LambdaQueryChainWrapper<DictData> lambdaQuery = lambdaQuery();
		lambdaQuery.eq(StringUtils.isNotBlank(listReq.getDictType()), DictData::getDictType, listReq.getDictType());
		lambdaQuery.eq(StringUtils.isNotBlank(listReq.getStatus()), DictData::getStatus, listReq.getStatus());
		lambdaQuery.like(StringUtils.isNotBlank(listReq.getDictLabel()), DictData::getDictLabel, listReq.getDictLabel());
		
		Map<String, Object> params = listReq.getParams();
		Date beginTime = DateTimeUtils.parseDate(MapUtils.getString(params, "beginTime"));
		Date endTime = DateTimeUtils.parseDate(MapUtils.getString(params, "endTime"));
		lambdaQuery.between( beginTime!= null && endTime!= null , DictData::getCreateTime, beginTime, endTime);
		
		lambdaQuery.orderByAsc(DictData::getDictSort);
		return lambdaQuery.list();
	}

	/**
	 * 根据字典类型和字典键值查询字典数据信息
	 * 
	 * @param dictType  字典类型
	 * @param dictValue 字典键值
	 * @return 字典标签
	 */
	@Override
	public List<DictData> selectDictLabel(String dictType) {
		LambdaQueryChainWrapper<DictData> lambdaQuery = lambdaQuery();
		lambdaQuery.eq(DictData::getDictType, dictType);
		lambdaQuery.eq(DictData::getStatus, '0');
		lambdaQuery.orderByAsc(DictData::getDictSort);
		return lambdaQuery.list();
	}
	
	/**
	 * 根据字典数据ID查询信息
	 * 
	 * @param id 字典数据ID
	 * @return 字典数据
	 */
	@Override
	public DictData selectDictDataById(Long id) {
		return dictDataMapper.selectById(id);
	}

	/**
	 * 批量删除字典数据信息
	 * 
	 * @param ids 需要删除的字典数据ID
	 * @return 结果
	 */
	@Override
	public void deleteDictDataByIds(Long[] ids) {
		for (Long id : ids) {
			DictData data = selectDictDataById(id);
			dictDataMapper.deleteById(id);
			List<DictData> dictDatas = selectDictLabel(data.getDictType());
			DictUtils.setDictCache(data.getDictType(), dictDatas);
		}
	}

	/**
	 * 新增保存字典数据信息
	 * 
	 * @param data 字典数据信息
	 * @return 结果
	 */
	@Override
	public int insertDictData(DictData data) {
		int row = dictDataMapper.insert(data);
		if (row > 0) {
			List<DictData> dictDatas = selectDictLabel(data.getDictType());
			DictUtils.setDictCache(data.getDictType(), dictDatas);
		}
		return row;
	}

	/**
	 * 修改保存字典数据信息
	 * 
	 * @param data 字典数据信息
	 * @return 结果
	 */
	@Override
	public int updateDictData(DictData data) {
		int row = dictDataMapper.updateById(data);
		if (row > 0) {
			List<DictData> dictDatas = selectDictLabel(data.getDictType());
			DictUtils.setDictCache(data.getDictType(), dictDatas);
		}
		return row;
	}

	
	/**
	 * 查询字典数据
	 * 
	 * @param dictType 字典类型
	 * @return 字典数据
	 */
	@Override
	public int countDictDataByType(String dictType) {
		return lambdaQuery().eq(DictData::getDictType, dictType).count();
	}

	/**
	 * 同步修改字典类型
	 * 
	 * @param oldDictType 旧字典类型
	 * @param newDictType 新旧字典类型
	 * @return 结果
	 */
	@Override
	public boolean updateDictDataType(String oldDictType, String newDictType) {
		return lambdaUpdate().set(DictData::getDictType, newDictType).eq(DictData::getDictType, oldDictType).update();
	}
}
