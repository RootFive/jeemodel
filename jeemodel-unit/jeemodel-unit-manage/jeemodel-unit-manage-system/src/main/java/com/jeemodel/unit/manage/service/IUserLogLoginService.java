package com.jeemodel.unit.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeemodel.unit.manage.bean.dto.system.UserLogLoginListReq;
import com.jeemodel.unit.manage.bean.entity.UserLogLogin;

/**
 * 系统访问日志情况信息 服务层
 * 
 * @author Rootfive
 */
public interface IUserLogLoginService extends IService<UserLogLogin>{

	/**
	 * 查询系统登录日志集合
	 * 
	 * @param listReq 访问日志分页对象
	 * @return 登录记录集合
	 */
	List<UserLogLogin> selectLogininforList(UserLogLoginListReq listReq);

	/**
	 * 清空系统登录日志
	 */
	void cleanLogininfor();
}
