package com.jeemodel.unit.idcode.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeemodel.core.utils.BlankUtils;

import com.jeemodel.unit.idcode.bean.dto.UseSceneEditSave;
import com.jeemodel.unit.idcode.bean.dto.UseSceneListReq;
import com.jeemodel.unit.idcode.bean.entity.UseScene;
import com.jeemodel.unit.idcode.mapper.UseSceneMapper;
import com.jeemodel.unit.idcode.service.IUseSceneService;

import com.jeemodel.unit.manage.core.utils.SecurityUtils;

/**
 * 统一标识编码使用场景Service业务层处理
 * 
 * @author JeeModel
 * @date 2022-06-27
 */
@Service
public class UseSceneServiceImpl extends ServiceImpl<UseSceneMapper, UseScene> implements IUseSceneService {

	
	
    /**
     * 查询-统一标识编码使用场景列表(支持分页，使用分页助手之前调用)
     * 
     * @param listReq 统一标识编码使用场景
     * @return 统一标识编码使用场景列表
     */
    @Override
    public List<UseScene> selectUseSceneList(UseSceneListReq listReq) {
        LambdaQueryChainWrapper<UseScene> lambdaQuery = lambdaQuery();
        //统一标识编码使用场景-名称， like模糊查询：【value like 查询值 】
        lambdaQuery.like(BlankUtils.isNotBlank(listReq.getSceneName()), UseScene::getSceneName, listReq.getSceneName());
        //统一标识编码使用场景-编码（36进制，最大1295）， 等于查询EQ 【value = 查询值】
        lambdaQuery.eq(BlankUtils.isNotBlank(listReq.getSceneCode()), UseScene::getSceneCode, listReq.getSceneCode());
        //统一标识编码-长度（3-6）， 等于查询EQ 【value = 查询值】
        lambdaQuery.eq(BlankUtils.isNotBlank(listReq.getUidLength()), UseScene::getUidLength, listReq.getUidLength());
        //统一标识编码序列号， 等于查询EQ 【value = 查询值】
        lambdaQuery.eq(BlankUtils.isNotBlank(listReq.getUidSerial()), UseScene::getUidSerial, listReq.getUidSerial());
        //状态（0正常 1停用）， 等于查询EQ 【value = 查询值】
        lambdaQuery.eq(BlankUtils.isNotBlank(listReq.getStatus()), UseScene::getStatus, listReq.getStatus());
        //删除时间， 等于查询EQ 【value = 查询值】
        lambdaQuery.eq(BlankUtils.isNotBlank(listReq.getDelTime()), UseScene::getDelTime, listReq.getDelTime());
			
        return lambdaQuery.list();
    }

	
    /**
     * 新增统一标识编码使用场景
     * 
     * @param useScene 统一标识编码使用场景
     * @return 结果
     */
    @Override
    public boolean insertUseScene(UseSceneEditSave useSceneEditSave) {
        UseScene useScene= new UseScene(useSceneEditSave);
        useScene.setCreateBy(SecurityUtils.getUsername());

        return save(useScene);
    }

    /**
     * 修改统一标识编码使用场景
     * 
     * @param useScene 统一标识编码使用场景
     * @return 结果
     */
    @Override
    public boolean updateUseScene(UseSceneEditSave useSceneEditSave) {
        UseScene useScene= new UseScene(useSceneEditSave);
        useScene.setUpdateBy(SecurityUtils.getUsername());

        return updateById(useScene);
    }

	

}
