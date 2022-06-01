package com.jeemodel.unit.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeemodel.unit.manage.bean.entity.UserLogOper;

/**
 * 操作日志 数据层
 * 
 * @author Rootfive
 */
public interface UserLogOperMapper extends BaseMapper<UserLogOper> {

	/**
	 * 清空操作日志
	 */
	public void cleanOperLog();
}
