package com.jeemodel.unit.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeemodel.unit.manage.bean.dto.system.UserLogOperListReq;
import com.jeemodel.unit.manage.bean.entity.UserLogOper;

/**
 * 操作日志 服务层
 * 
 * @author Rootfive
 */
public interface IUserLogOperService extends IService<UserLogOper>{
	/**
	 * 新增操作日志
	 * 
	 * @param operLog 操作日志对象
	 */
//	public void insertOperlog(UserLogOper operLog);

	/**
	 * 查询系统操作日志集合
	 * 
	 * @param listReq 操作日志对象
	 * @return 操作日志集合
	 */
	public List<UserLogOper> selectOperLogList(UserLogOperListReq listReq);

	/**
	 * 批量删除系统操作日志
	 * 
	 * @param ids 需要删除的操作日志ID
	 * @return 结果
	 */
//	public int deleteOperLogByIds(Long[] ids);

	/**
	 * 查询操作日志详细
	 * 
	 * @param id 操作ID
	 * @return 操作日志对象
	 */
//	public UserLogOper selectOperLogById(Long id);

	/**
	 * 清空操作日志
	 */
	public void cleanOperLog();
}
