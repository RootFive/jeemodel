package com.jeemodel.solution.captcha.service.impl;

import com.jeemodel.solution.captcha.byec.base.Captcha;
import com.jeemodel.solution.captcha.dto.CaptchaImage;
import com.jeemodel.solution.captcha.service.ICaptchaService;

public abstract class AbstractCaptchaService implements ICaptchaService {

	protected CaptchaImage toCaptchaImage(Captcha captcha) {
		String codeValue = captcha.text().toLowerCase();
		String image = captcha.toBase64();
		return CaptchaImage.builder().code(codeValue).img(image).build();
	}
}
