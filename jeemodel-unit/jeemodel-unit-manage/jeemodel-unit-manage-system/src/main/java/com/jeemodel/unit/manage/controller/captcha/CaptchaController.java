package com.jeemodel.unit.manage.controller.captcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jeemodel.bean.rpc.PongData;
import com.jeemodel.bean.rpc.PongUtils;
import com.jeemodel.solution.captcha.dto.CaptchaImageReq;
import com.jeemodel.solution.captcha.dto.CaptchaImageResp;
import com.jeemodel.unit.manage.helper.LoginCaptchaHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * 验证码操作处理
 * 
 * @author Rootfive
 */
@Slf4j
@RestController
public class CaptchaController {

	@Autowired
	private LoginCaptchaHelper loginCaptchaHelper;

	/**
	 * 生成验证码
	 */
	@GetMapping("/captchaImage")
	public PongData<CaptchaImageResp> getCode(@RequestBody(required = false) CaptchaImageReq captchaImageReq) {
		log.debug("captchaImageReq={}", captchaImageReq);
		CaptchaImageResp buildCaptcha = loginCaptchaHelper.buildCaptcha(captchaImageReq);

		return PongUtils.okData(buildCaptcha);
	}
}
