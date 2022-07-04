package com.jeemodel.unit.idcode.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeemodel.unit.idcode.bean.dto.UseSceneEditSave;
import com.jeemodel.unit.idcode.bean.dto.UseSceneListReq;
import com.jeemodel.unit.idcode.bean.entity.UseScene;

/**
 * 场景标识Service接口
 * 
 * @author JeeModel
 * @date 2022-07-04
 */
public interface IUseSceneService extends IService<UseScene> {

	/**
	 * 查询场景标识列表
	 * 
	 * @param listReq 场景标识 (支持分页，使用分页助手之前调用)
	 * @return 场景标识集合
	 */
	List<UseScene> selectUseSceneList(UseSceneListReq listReq);

	/**
	 * 新增场景标识
	 * 
	 * @param useScene 场景标识
	 * @return 结果
	 */
	boolean insertUseScene(UseSceneEditSave useSceneEditSave);

	/**
	 * 修改场景标识
	 * 
	 * @param useScene 场景标识
	 * @return 结果
	 */
	boolean updateUseScene(UseSceneEditSave useSceneEditSave);
	
	/**
	 * 删除场景标识
	 * 
	 * @param ids 场景标识id
	 * @return 结果
	 */
	boolean remove(Long[] ids);

}
