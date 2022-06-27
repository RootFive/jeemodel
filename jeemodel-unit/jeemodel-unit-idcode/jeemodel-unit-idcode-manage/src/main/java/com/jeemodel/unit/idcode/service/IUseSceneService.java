package com.jeemodel.unit.idcode.service;

import java.util.List;
import com.jeemodel.unit.idcode.bean.dto.UseSceneEditSave;
import com.jeemodel.unit.idcode.bean.dto.UseSceneListReq;
import com.jeemodel.unit.idcode.bean.entity.UseScene;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 统一标识编码使用场景Service接口
 * 
 * @author JeeModel
 * @date 2022-06-27
 */
public interface IUseSceneService extends IService<UseScene> {

    /**
     * 查询统一标识编码使用场景列表
     * 
     * @param listReq 统一标识编码使用场景
     * @return 统一标识编码使用场景集合
     */
    List<UseScene> selectUseSceneList(UseSceneListReq listReq);


    /**
     * 新增统一标识编码使用场景
     * 
     * @param useScene 统一标识编码使用场景
     * @return 结果
     */
    boolean insertUseScene(UseSceneEditSave useSceneEditSave);

    /**
     * 修改统一标识编码使用场景
     * 
     * @param useScene 统一标识编码使用场景
     * @return 结果
     */
    boolean updateUseScene(UseSceneEditSave useSceneEditSave);		
		
	

}
