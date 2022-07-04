package com.jeemodel.unit.idcode.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeemodel.unit.idcode.bean.entity.UseScene;

/**
 * 场景标识Mapper接口
 * 
 * @author JeeModel
 * @date 2022-07-04
 */
public interface UseSceneMapper extends BaseMapper<UseScene> {

	/**
	 * UID序列号自增
	 * 
	 * @param sceneCode    统一标识编码
	 * @param incrQuantity 增量
	 * @return
	 */
	Integer autoIncrUidSerial(@Param("sceneCode") String sceneCode, @Param("incrQuantity") int incrQuantity);

	/**
	 * 查询 UID序列号
	 * 
	 * @param sceneCode 统一标识编码
	 * @return
	 */
	Long queryUidSerial(@Param("sceneCode") String sceneCode);
}
