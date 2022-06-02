package com.jeemodel.unit.manage.manager.factory;

import java.time.LocalDateTime;
import java.util.TimerTask;

import com.jeemodel.core.constant.Constants;
import com.jeemodel.core.utils.LogUtils;
import com.jeemodel.core.utils.ServletUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.core.utils.ip.IpUtils;
import com.jeemodel.core.utils.spring.SpringUtils;
import com.jeemodel.unit.manage.bean.entity.UserLogLogin;
import com.jeemodel.unit.manage.bean.entity.UserLogOper;
import com.jeemodel.unit.manage.core.constant.ManageConstants;
import com.jeemodel.unit.manage.service.IUserLogLoginService;
import com.jeemodel.unit.manage.service.IUserLogOperService;

import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;

/**
 * 异步工厂（产生任务用）
 * 
 * @author Rootfive
 */
@Slf4j
public class AsyncFactory {

	/**
	 * 记录登录信息
	 * 
	 * @param username 用户名
	 * @param status   状态
	 * @param message  消息
	 * @param args     列表
	 * @return 任务task
	 */
	public static TimerTask recordLogininfor(final String username, final String status, final String message,
			final Object... args) {
		final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
		final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
		return new TimerTask() {
			@Override
			public void run() {
				String address = IpUtils.getRealAddressByIP(ip);
				StringBuilder s = new StringBuilder();
				s.append(LogUtils.getBlock(ip));
				s.append(address);
				s.append(LogUtils.getBlock(username));
				s.append(LogUtils.getBlock(status));
				s.append(LogUtils.getBlock(message));
				// 打印信息到日志
				log.info(s.toString(), args);
				// 获取客户端操作系统
				String os = userAgent.getOperatingSystem().getName();
				// 获取客户端浏览器
				String browser = userAgent.getBrowser().getName();
				// 封装对象
				UserLogLogin logininfor = new UserLogLogin();
				logininfor.setUserName(username);
				logininfor.setIpaddr(ip);
				logininfor.setLoginLocation(address);
				logininfor.setBrowser(browser);
				logininfor.setOs(os);
				logininfor.setMsg(message);
				logininfor.setLoginTime(LocalDateTime.now());
				// 日志状态
				if (StringUtils.equalsAny(status, ManageConstants.LOGIN_SUCCESS, ManageConstants.LOGOUT, ManageConstants.REGISTER)) {
					logininfor.setStatus(Constants.SUCCESS);
				} else if (ManageConstants.LOGIN_FAIL.equals(status)) {
					logininfor.setStatus(Constants.FAIL);
				}
				// 插入数据
				SpringUtils.getBean(IUserLogLoginService.class).save(logininfor);
			}
		};
	}

	/**
	 * 操作日志记录
	 * 
	 * @param operLog 操作日志信息
	 * @return 任务task
	 */
	public static TimerTask recordOper(final UserLogOper operLog) {
		return new TimerTask() {
			@Override
			public void run() {
				// 远程查询操作地点
				operLog.setOperLocation(IpUtils.getRealAddressByIP(operLog.getOperIp()));
				SpringUtils.getBean(IUserLogOperService.class).save(operLog);
			}
		};
	}
}
