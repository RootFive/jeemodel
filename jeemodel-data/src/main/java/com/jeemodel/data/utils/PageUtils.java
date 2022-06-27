package com.jeemodel.data.utils;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jeemodel.bean.enums.code.sub.impl.OKCodeEnum;
import com.jeemodel.bean.rpc.ApiEchoContext;
import com.jeemodel.bean.rpc.PongTable;
import com.jeemodel.bean.rpc.PongUtils;
import com.jeemodel.bean.utils.StringFormatUtils;
import com.jeemodel.core.page.PageDomain;
import com.jeemodel.core.page.TableSupport;
import com.jeemodel.core.utils.NumberUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.core.utils.sql.SqlUtil;

public class PageUtils extends PongUtils{

	/**
	 * 设置请求分页数据
	 */
	public static void startPage() {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		Integer pageNum = pageDomain.getPageNum();
		Integer pageSize = pageDomain.getPageSize();
		if (NumberUtils.isPositiveInteger(pageNum) && NumberUtils.isPositiveInteger(pageSize)) {
			String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
			Boolean reasonable = pageDomain.getReasonable();
			PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
		}
	}

	/**
	 * 设置请求排序数据
	 */
	public static void startOrderBy() {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		if (StringUtils.isNotEmpty(pageDomain.getOrderBy())) {
			String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
			PageHelper.orderBy(orderBy);
		}
	}


	/**
	 * 响应请求分页数据
	 * @param <T>
	 * @param list
	 * @return
	 */
	public static <T> PongTable<T> okTable(List<T> list) {
		String echo = ApiEchoContext.getEcho();
		long total = new PageInfo<T>(list).getTotal();
		return new PongTable<T>(echo, OKCodeEnum.ALL, list, total);
	}
	

	/**
	 * 响应请求分页数据
	 * @param <T>
	 * @param list
	 * @param format 字符串模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 * @return
	 */
	public static <T> PongTable<T> okTable(List<T> list,String format, Object... args) {
		String echo = ApiEchoContext.getEcho();
		long total = new PageInfo<T>(list).getTotal();
		String customMsg = StringFormatUtils.format(format, args);
		return new PongTable<T>(echo, OKCodeEnum.ALL, list, total,customMsg);
	}
}
