package com.jeemodel.unit.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeemodel.unit.manage.bean.dto.system.DictDataListReq;
import com.jeemodel.unit.manage.core.bean.entity.DictData;

/**
 * 字典 业务层
 * 
 * @author Rootfive
 */
public interface IDictDataService extends IService<DictData>{
	
	/**
	 * 根据条件分页查询字典数据
	 * 
	 * @param listReq 字典数据信息
	 * @return 字典数据集合信息
	 */
	public List<DictData> selectDictDataList(DictDataListReq listReq);

	/**
	 * 根据字典类型查询字典数据信息
	 * 
	 * @param dictType  字典类型
	 * @return 字典标签
	 */
	public List<DictData> selectDictLabel(String dictType);
	
	/**
	 * 根据字典数据ID查询信息
	 * 
	 * @param id 字典数据ID
	 * @return 字典数据
	 */
	public DictData selectDictDataById(Long id);

	/**
	 * 批量删除字典数据信息
	 * 
	 * @param ids 需要删除的字典数据ID
	 * @return 结果
	 */
	public void deleteDictDataByIds(Long[] ids);

	/**
	 * 新增保存字典数据信息
	 * 
	 * @param dictData 字典数据信息
	 * @return 结果
	 */
	public int insertDictData(DictData dictData);

	/**
	 * 修改保存字典数据信息
	 * 
	 * @param dictData 字典数据信息
	 * @return 结果
	 */
	public int updateDictData(DictData dictData);
	
	/**
	 * 查询字典数据
	 * 
	 * @param dictType 字典类型
	 * @return 字典数据
	 */
	int countDictDataByType(String dictType);
	
	/**
	 * 同步修改字典类型
	 * 
	 * @param oldDictType 旧字典类型
	 * @param newDictType 新旧字典类型
	 * @return 结果
	 */
	boolean updateDictDataType(String oldDictType,String newDictType);

}
