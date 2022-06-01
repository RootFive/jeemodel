package com.jeemodel.unit.manage.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeemodel.bean.exception.type.CheckException;
import com.jeemodel.core.utils.DateTimeUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.unit.manage.bean.dto.system.DictListReq;
import com.jeemodel.unit.manage.core.bean.entity.Dict;
import com.jeemodel.unit.manage.core.bean.entity.DictData;
import com.jeemodel.unit.manage.core.constant.UserConstants;
import com.jeemodel.unit.manage.core.utils.DictUtils;
import com.jeemodel.unit.manage.mapper.DictMapper;
import com.jeemodel.unit.manage.service.IDictDataService;
import com.jeemodel.unit.manage.service.IDictService;

/**
 * 字典 业务层处理
 * 
 * @author Rootfive
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict>  implements IDictService {

	@Autowired
	private DictMapper dictTypeMapper;
	
	@Autowired
	private IDictDataService dictDataService;

	/**
	 * 项目启动时，初始化字典到缓存
	 */
	@PostConstruct
	public void init() {
		loadingDictCache();
	}

	/**
	 * 根据条件分页查询字典类型
	 * 
	 * @param dictType 字典类型信息
	 * @return 字典类型集合信息
	 */
	@Override
	public List<Dict> selectDictTypeList(DictListReq listReq) {
		LambdaQueryChainWrapper<Dict> lambdaQuery = lambdaQuery();
		lambdaQuery.like(StringUtils.isNotBlank(listReq.getDictName()), Dict::getDictName, listReq.getDictName());
		lambdaQuery.eq(StringUtils.isNotBlank(listReq.getStatus()), Dict::getStatus, listReq.getStatus());
		lambdaQuery.like(StringUtils.isNotBlank(listReq.getDictType()), Dict::getDictType, listReq.getDictType());
		
		Map<String, Object> params = listReq.getParams();
		Date beginTime = DateTimeUtils.parseDate(MapUtils.getString(params, "beginTime"));
		Date endTime = DateTimeUtils.parseDate(MapUtils.getString(params, "endTime"));
		lambdaQuery.between( beginTime!= null && endTime!= null , Dict::getCreateTime, beginTime, endTime);
		
		
		return lambdaQuery.list();
	}

	/**
	 * 根据所有字典类型
	 * 
	 * @return 字典类型集合信息
	 */
	@Override
	public List<Dict> selectDictTypeAll() {
		return lambdaQuery().list();
	}

	/**
	 * 根据字典类型查询字典数据
	 * 
	 * @param dictType 字典类型
	 * @return 字典数据集合信息
	 */
	@Override
	public List<DictData> selectDictDataByType(String dictType) {
		List<DictData> dictDatas = DictUtils.getDictCache(dictType);
		if (StringUtils.isNotEmpty(dictDatas)) {
			return dictDatas;
		}
		dictDatas = dictDataService.selectDictLabel(dictType);
		if (StringUtils.isNotEmpty(dictDatas)) {
			DictUtils.setDictCache(dictType, dictDatas);
			return dictDatas;
		}
		return null;
	}

	/**
	 * 根据字典类型ID查询信息
	 * 
	 * @param id 字典类型ID
	 * @return 字典类型
	 */
	@Override
	public Dict selectDictTypeById(Long id) {
		return dictTypeMapper.selectById(id);
	}

	/**
	 * 根据字典类型查询信息
	 * 
	 * @param dictType 字典类型
	 * @return 字典类型
	 */
	@Override
	public Dict selectDictTypeByType(String dictType) {
		return lambdaQuery().eq(Dict::getDictType, dictType).one();
	}

	/**
	 * 批量删除字典类型信息
	 * 
	 * @param ids 需要删除的字典ID
	 * @return 结果
	 */
	@Override
	public void deleteDictTypeByIds(Long[] ids) {
		for (Long id : ids) {
			Dict dictType = selectDictTypeById(id);
			if (dictDataService.countDictDataByType(dictType.getDictType()) > 0) {
				throw new CheckException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
			}
			dictTypeMapper.deleteById(id);
			DictUtils.removeDictCache(dictType.getDictType());
		}
	}

	/**
	 * 加载字典缓存数据
	 */
	@Override
	public void loadingDictCache() {
		List<Dict> dictTypeList = lambdaQuery().list();;
		for (Dict dictType : dictTypeList) {
			List<DictData> dictDatas = dictDataService.selectDictLabel(dictType.getDictType());
			DictUtils.setDictCache(dictType.getDictType(), dictDatas);
		}
	}

	/**
	 * 清空字典缓存数据
	 */
	@Override
	public void clearDictCache() {
		DictUtils.clearDictCache();
	}

	/**
	 * 重置字典缓存数据
	 */
	@Override
	public void resetDictCache() {
		clearDictCache();
		loadingDictCache();
	}

	/**
	 * 新增保存字典类型信息
	 * 
	 * @param dict 字典类型信息
	 * @return 结果
	 */
	@Override
	public int insertDictType(Dict dict) {
		int row = dictTypeMapper.insert(dict);
		if (row > 0) {
			DictUtils.setDictCache(dict.getDictType(), null);
		}
		return row;
	}

	/**
	 * 修改保存字典类型信息
	 * 
	 * @param dict 字典类型信息
	 * @return 结果
	 */
	@Override
	@Transactional
	public int updateDictType(Dict dict) {
		Dict oldDict = dictTypeMapper.selectById(dict.getId());
		dictDataService.updateDictDataType(oldDict.getDictType(), dict.getDictType());
		int row = dictTypeMapper.updateById(dict);
		if (row > 0) {
			List<DictData> dictDatas = dictDataService.selectDictLabel(dict.getDictType());
			DictUtils.setDictCache(dict.getDictType(), dictDatas);
		}
		return row;
	}

	/**
	 * 校验字典类型称是否唯一
	 * 
	 * @param dict 字典类型
	 * @return 结果
	 */
	@Override
	public String checkDictTypeUnique(Dict dict) {
		Long dictId = StringUtils.isNull(dict.getId()) ? -1L : dict.getId();
		Dict dictType = selectDictTypeByType(dict.getDictType());
		if (StringUtils.isNotNull(dictType) && dictType.getId().longValue() != dictId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}
}
