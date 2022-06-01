package com.jeemodel.unit.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeemodel.unit.manage.bean.entity.Notice;

/**
 * 公告 服务层
 * 
 * @author Rootfive
 */
public interface INoticeService extends IService<Notice>{

	/**
	 * 查询公告列表
	 * 
	 * @param notice 公告信息
	 * @return 公告集合
	 */
	public List<Notice> selectNoticeList(Notice notice);
}
