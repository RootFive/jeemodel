package com.jeemodel.unit.manage.controller.system;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeemodel.core.utils.StringUtils;

/**
 * 首页
 *
 * @author Rootfive
 */
@RestController
public class SysIndexController {
	
	/**
	 * 访问首页，提示语
	 */
	@RequestMapping("/")
	public String index() {
		return StringUtils.format("欢迎使用【JeeModel】后台管理框架，当前版本：Q2022，请通过前端地址访问。");
	}
}
