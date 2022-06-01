package com.jeemodel.unit.manage.bean.dto.log;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeemodel.bean.annotation.Excel;
import com.jeemodel.bean.dto.BaseDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class UserLogLoginDTO extends BaseDTO{

	/** 用户账号 */
	private String userName;

	/** 登录状态 0成功 1失败 */
	private String status;

	/** 登录IP地址 */
	private String ipaddr;

	/** 登录地点 */
	private String loginLocation;

	/** 浏览器类型 */
	@Excel(name = "浏览器")
	private String browser;

	/** 操作系统 */
	@Excel(name = "操作系统")
	private String os;

	/** 提示消息 */
	@Excel(name = "提示消息")
	private String msg;

	/** 访问时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Excel(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
	private Date loginTime;
	
}
