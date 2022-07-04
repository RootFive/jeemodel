package com.jeemodel.unit.idcode.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeemodel.core.utils.BlankUtils;
import com.jeemodel.core.utils.ExceptionUtils;
import com.jeemodel.unit.idcode.bean.dto.UseSceneEditSave;
import com.jeemodel.unit.idcode.bean.dto.UseSceneListReq;
import com.jeemodel.unit.idcode.bean.entity.UseScene;
import com.jeemodel.unit.idcode.mapper.UseSceneMapper;
import com.jeemodel.unit.idcode.service.IUseSceneService;
import com.jeemodel.unit.manage.core.utils.SecurityUtils;

/**
 * 场景标识Service业务层处理
 * 
 * @author JeeModel
 * @date 2022-07-04
 */
@DS("idcode")
@Service
public class UseSceneServiceImpl extends ServiceImpl<UseSceneMapper, UseScene> implements IUseSceneService {
	/**
	 * 场景标识-编码 数量上限
	 */
	public static final int SCENE_CODE_MAX = 35 * 35 * 35;

	/**
	 * 查询-场景标识列表(支持分页，使用分页助手之前调用)
	 * 
	 * @param listReq 场景标识
	 * @return 场景标识列表
	 */
	@Override
	public List<UseScene> selectUseSceneList(UseSceneListReq listReq) {
		LambdaQueryChainWrapper<UseScene> lambdaQuery = lambdaQuery();
		// 场景标识-名称， like模糊查询：【value like 查询值 】
		lambdaQuery.like(BlankUtils.isNotBlank(listReq.getSceneName()), UseScene::getSceneName, listReq.getSceneName());
		// 场景标识-编码（35进制，最大42874）， 等于查询EQ 【value = 查询值】
		lambdaQuery.eq(BlankUtils.isNotBlank(listReq.getSceneCode()), UseScene::getSceneCode, listReq.getSceneCode());
		// 标识-长度（3-6）， 等于查询EQ 【value = 查询值】
		lambdaQuery.eq(BlankUtils.isNotBlank(listReq.getUidLength()), UseScene::getUidLength, listReq.getUidLength());
		// 标识序列号， between区间查询-【beginUidSerial <= 查询值 <= endUidSerial】 Java类型是：Long
		lambdaQuery.between(
				BlankUtils.isNotBlank(listReq.getBeginUidSerial()) && BlankUtils.isNotBlank(listReq.getEndUidSerial()),
				UseScene::getUidSerial, listReq.getBeginUidSerial(), listReq.getEndUidSerial());
		// 创建者， 等于查询EQ 【value = 查询值】
		lambdaQuery.eq(BlankUtils.isNotBlank(listReq.getCreateBy()), UseScene::getCreateBy, listReq.getCreateBy());
		// 状态（0正常 1停用）， 等于查询EQ 【value = 查询值】
		lambdaQuery.eq(BlankUtils.isNotBlank(listReq.getStatus()), UseScene::getStatus, listReq.getStatus());
		// 更新者， 等于查询EQ 【value = 查询值】
		lambdaQuery.eq(BlankUtils.isNotBlank(listReq.getUpdateBy()), UseScene::getUpdateBy, listReq.getUpdateBy());
		// 更新时间， between区间查询-【beginUpdateTime <= 查询值 <= endUpdateTime】 Java类型是：Date
		lambdaQuery.between(
				BlankUtils.isNotBlank(listReq.getBeginUpdateTime())
						&& BlankUtils.isNotBlank(listReq.getEndUpdateTime()),
				UseScene::getUpdateTime, listReq.getBeginUpdateTime(), listReq.getEndUpdateTime());
		lambdaQuery.eq(UseScene::getDelFlag, 0);
		return lambdaQuery.list();
	}

	/**
	 * 新增场景标识
	 * 
	 * @param useScene 场景标识+
	 * @return 结果
	 */
	@Transactional(isolation = Isolation.SERIALIZABLE)
	@Override
	public boolean insertUseScene(UseSceneEditSave useSceneEditSave) {
		UseScene useScene = new UseScene(useSceneEditSave);
		lambdaQuery().count();
		Integer count = lambdaQuery().count();

		if (count + 1 >= SCENE_CODE_MAX) {
			throw ExceptionUtils.fail("场景标识使用已经达到上限：{}", SCENE_CODE_MAX);
		}

		// 转35进制编码，并转大写
		String useSceneUID = Integer.toString(count + 1, 35).toUpperCase();
		String sceneCode = buildSceneCode(useSceneUID);

		useScene.setSceneCode(sceneCode);
		useScene.setCreateBy(SecurityUtils.getUsername());
		return save(useScene);
	}

	private String buildSceneCode(String useSceneUID) {
		int length = useSceneUID.length();
		if (length >= 3) {
			return useSceneUID;
		} else if (length == 3) {
			return "Z" + useSceneUID;
		} else {
			return "Z0" + useSceneUID;
		}
	}

	/**
	 * 修改场景标识
	 * 
	 * @param useScene 场景标识
	 * @return 结果
	 */
	@Transactional
	@Override
	public boolean updateUseScene(UseSceneEditSave useSceneEditSave) {
		UseScene useScene = new UseScene(useSceneEditSave);
		//
		useScene.setSceneCode(null);
		useScene.setUidLength(null);
		useScene.setUidSerial(null);
		useScene.setUpdateBy(SecurityUtils.getUsername());

		return updateById(useScene);
	}

	/**
	 * 删除场景标识
	 * 
	 * @param ids 场景标识id
	 * @return 结果
	 */
	@Transactional
	@Override
	public boolean remove(Long[] ids) {
		List<Long> idList = Arrays.asList(ids);
		LambdaUpdateChainWrapper<UseScene> lambdaUpdate = lambdaUpdate();
		lambdaUpdate.in(UseScene::getId, idList);
		lambdaUpdate.eq(UseScene::getDelFlag, 0);
		lambdaUpdate.set(UseScene::getDelFlag, 2);
		lambdaUpdate.set(UseScene::getDelTime, LocalDateTime.now());
		return lambdaUpdate.update();
	}

}
