package com.jeemodel.solution.captcha.service.impl;

import org.springframework.stereotype.Service;

import com.jeemodel.solution.captcha.dto.CaptchaImage;
import com.wf.captcha.ChineseCaptcha;

@Service("ChineseCaptcha")
public class ChineseCaptchaService extends AbstractCaptchaService {

	@Override
	public CaptchaImage createCaptcha(int width, int height) {
		ChineseCaptcha chineseCaptcha = new ChineseCaptcha(width, height);
		return super.toCaptchaImage(chineseCaptcha);
	}


}
