package com.jeemodel.unit.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeemodel.unit.manage.bean.entity.UserLogLogin;

/**
 * 系统访问日志情况信息 数据层
 * 
 * @author Rootfive
 */
public interface UserLogLoginMapper extends BaseMapper<UserLogLogin>{

	/**
	 * 清空系统登录日志
	 * 
	 * @return 结果
	 */
	int cleanUserLogLogin();
}
