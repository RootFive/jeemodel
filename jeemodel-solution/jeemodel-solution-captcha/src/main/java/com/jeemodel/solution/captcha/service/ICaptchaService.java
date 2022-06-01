package com.jeemodel.solution.captcha.service;

import com.jeemodel.solution.captcha.dto.CaptchaImage;

public interface ICaptchaService {
	
	CaptchaImage createCaptcha(int width, int height);
}
