package com.jeemodel.unit.manage.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeemodel.core.utils.DateTimeUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.unit.manage.bean.dto.system.UserLogOperListReq;
import com.jeemodel.unit.manage.bean.entity.UserLogOper;
import com.jeemodel.unit.manage.mapper.UserLogOperMapper;
import com.jeemodel.unit.manage.service.IUserLogOperService;

/**
 * 操作日志 服务层处理
 * 
 */
@Service
public class UserLogOperServiceImpl extends ServiceImpl<UserLogOperMapper, UserLogOper> implements IUserLogOperService {

	/**
	 * 查询系统操作日志集合
	 * 
	 * @param operLog 操作日志对象
	 * @return 操作日志集合
	 */
	@Override
	public List<UserLogOper> selectOperLogList(UserLogOperListReq listReq) {
		LambdaQueryChainWrapper<UserLogOper> lambdaQuery = lambdaQuery();
		lambdaQuery.like(StringUtils.isNotBlank(listReq.getTitle()),UserLogOper::getTitle, listReq.getTitle());
		lambdaQuery.eq(listReq.getBusinessType() != null,UserLogOper::getBusinessType, listReq.getBusinessType());
		if (ArrayUtils.isNotEmpty(listReq.getBusinessTypes())) {
			lambdaQuery.in(UserLogOper::getBusinessType,Arrays.asList( listReq.getBusinessTypes()));
		}
		lambdaQuery.eq(listReq.getStatus() != null,UserLogOper::getStatus, listReq.getStatus());
		lambdaQuery.like(StringUtils.isNotBlank(listReq.getOperName()),UserLogOper::getOperName, listReq.getOperName());
		
		Map<String, Object> params = listReq.getParams();
		Date beginTime = DateTimeUtils.parseDate(MapUtils.getString(params, "beginTime"));
		Date endTime = DateTimeUtils.parseDate(MapUtils.getString(params, "endTime"));
		lambdaQuery.between( beginTime!= null && endTime!= null , UserLogOper::getCreateTime, beginTime, endTime);
		lambdaQuery.orderByDesc(UserLogOper::getId);
		
		return lambdaQuery.list();
	}

	/**
	 * 清空操作日志
	 */
	@Override
	public void cleanOperLog() {
		baseMapper.cleanOperLog();
	}
}
