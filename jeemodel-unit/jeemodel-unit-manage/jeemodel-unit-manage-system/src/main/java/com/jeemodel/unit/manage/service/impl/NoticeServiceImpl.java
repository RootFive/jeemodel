package com.jeemodel.unit.manage.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.unit.manage.bean.entity.Notice;
import com.jeemodel.unit.manage.mapper.NoticeMapper;
import com.jeemodel.unit.manage.service.INoticeService;

/**
 * 公告 服务层实现
 * 
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

	/**
	 * 查询公告列表
	 * 
	 * @param notice 公告信息
	 * @return 公告集合
	 */
	@Override
	public List<Notice> selectNoticeList(Notice notice) {
		LambdaQueryChainWrapper<Notice> lambdaQuery = lambdaQuery();
		lambdaQuery.like(StringUtils.isNotBlank(notice.getNoticeTitle()),Notice::getNoticeTitle, notice.getNoticeTitle());
		lambdaQuery.eq(StringUtils.isNotBlank(notice.getNoticeType()),Notice::getNoticeType, notice.getNoticeType());
		lambdaQuery.like(StringUtils.isNotBlank(notice.getCreateBy()),Notice::getCreateBy, notice.getCreateBy());
		lambdaQuery.orderByDesc(Notice::getId);
		return lambdaQuery.list();
	}
}
