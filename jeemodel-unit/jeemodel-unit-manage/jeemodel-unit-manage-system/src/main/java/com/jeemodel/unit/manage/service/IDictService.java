package com.jeemodel.unit.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeemodel.unit.manage.bean.dto.system.DictListReq;
import com.jeemodel.unit.manage.core.bean.entity.Dict;
import com.jeemodel.unit.manage.core.bean.entity.DictData;

/**
 * 字典 业务层
 * 
 * @author Rootfive
 */
public interface IDictService extends IService<Dict>{
	/**
	 * 根据条件分页查询字典类型
	 * 
	 * @param listReq 分页信息
	 * @return 字典类型集合信息
	 */
	public List<Dict> selectDictTypeList(DictListReq listReq);

	/**
	 * 根据所有字典类型
	 * 
	 * @return 字典类型集合信息
	 */
	public List<Dict> selectDictTypeAll();

	/**
	 * 根据字典类型查询字典数据
	 * 
	 * @param dictType 字典类型
	 * @return 字典数据集合信息
	 */
	public List<DictData> selectDictDataByType(String dictType);

	/**
	 * 根据字典类型ID查询信息
	 * 
	 * @param id 字典类型ID
	 * @return 字典类型
	 */
	public Dict selectDictTypeById(Long id);

	/**
	 * 根据字典类型查询信息
	 * 
	 * @param dictType 字典类型
	 * @return 字典类型
	 */
	public Dict selectDictTypeByType(String dictType);

	/**
	 * 批量删除字典信息
	 * 
	 * @param ids 需要删除的字典ID
	 * @return 结果
	 */
	public void deleteDictTypeByIds(Long[] ids);

	/**
	 * 加载字典缓存数据
	 */
	public void loadingDictCache();

	/**
	 * 清空字典缓存数据
	 */
	public void clearDictCache();

	/**
	 * 重置字典缓存数据
	 */
	public void resetDictCache();

	/**
	 * 新增保存字典类型信息
	 * 
	 * @param dictType 字典类型信息
	 * @return 结果
	 */
	public int insertDictType(Dict dictType);

	/**
	 * 修改保存字典类型信息
	 * 
	 * @param dictType 字典类型信息
	 * @return 结果
	 */
	public int updateDictType(Dict dictType);

	/**
	 * 校验字典类型称是否唯一
	 * 
	 * @param dictType 字典类型
	 * @return 结果
	 */
	public String checkDictTypeUnique(Dict dictType);
}
