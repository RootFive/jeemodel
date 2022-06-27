package com.jeemodel.unit.manage.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jeemodel.bean.rpc.Pong;
import com.jeemodel.bean.rpc.PongUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.core.web.controller.BaseController;
import com.jeemodel.unit.manage.core.bean.model.RegisterBody;
import com.jeemodel.unit.manage.service.IConfigService;
import com.jeemodel.unit.manage.web.service.RegisterService;

/**
 * 注册验证
 * 
 * @author Rootfive
 */
@RestController
public class RegisterController extends BaseController {
	@Autowired
	private RegisterService registerService;

	@Autowired
	private IConfigService configService;

	@PostMapping("/register")
	public Pong register(@RequestBody RegisterBody user) {
		if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
			return PongUtils.fail("当前系统没有开启注册功能！");
		}
		String msg = registerService.register(user);
		return StringUtils.isEmpty(msg) ? PongUtils.ok() : PongUtils.error(msg);
	}
}
