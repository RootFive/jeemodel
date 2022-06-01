package com.jeemodel.unit.manage.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeemodel.core.utils.DateTimeUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.unit.manage.bean.dto.system.UserLogLoginListReq;
import com.jeemodel.unit.manage.bean.entity.UserLogLogin;
import com.jeemodel.unit.manage.mapper.UserLogLoginMapper;
import com.jeemodel.unit.manage.service.IUserLogLoginService;

/**
 * 系统访问日志情况信息 服务层处理
 * 
 * @author Rootfive
 */
@Service
public class UserLogLoginServiceImpl extends ServiceImpl<UserLogLoginMapper, UserLogLogin> implements IUserLogLoginService {


	/**
	 * 查询系统登录日志集合
	 * 
	 * @param logininfor 访问日志对象
	 * @return 登录记录集合
	 */
	@Override
	public List<UserLogLogin> selectLogininforList(UserLogLoginListReq listReq) {
		LambdaQueryChainWrapper<UserLogLogin> lambdaQuery = lambdaQuery();
		lambdaQuery.like(StringUtils.isNotBlank(listReq.getIpaddr()),UserLogLogin::getIpaddr, listReq.getIpaddr());
		lambdaQuery.eq(StringUtils.isNotBlank(listReq.getStatus()),UserLogLogin::getStatus, listReq.getStatus());
		lambdaQuery.like(StringUtils.isNotBlank(listReq.getUserName()),UserLogLogin::getUserName, listReq.getUserName());
		
		Map<String, Object> params = listReq.getParams();
		Date beginTime = DateTimeUtils.parseDate(MapUtils.getString(params, "beginTime"));
		Date endTime = DateTimeUtils.parseDate(MapUtils.getString(params, "endTime"));
		lambdaQuery.between( beginTime!= null && endTime!= null , UserLogLogin::getCreateTime, beginTime, endTime);
		lambdaQuery.orderByDesc(UserLogLogin::getId);
		
		return lambdaQuery.list();
	}

	/**
	 * 清空系统登录日志
	 */
	@Override
	public void cleanLogininfor() {
		baseMapper.cleanUserLogLogin();
	}
}
