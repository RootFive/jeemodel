package com.jeemodel.solution.captcha.service.impl;

import org.springframework.stereotype.Service;

import com.jeemodel.solution.captcha.dto.CaptchaImage;
import com.wf.captcha.ChineseGifCaptcha;

/**
 * @author Rootfive 中文验证码
 */
@Service("ChineseGifCaptcha")
public class ChineseGifCaptchaService extends AbstractCaptchaService {

	@Override
	public CaptchaImage createCaptcha(int width, int height) {
		ChineseGifCaptcha chineseGifCaptcha = new ChineseGifCaptcha(width, height);
		return super.toCaptchaImage(chineseGifCaptcha);
	}

}
