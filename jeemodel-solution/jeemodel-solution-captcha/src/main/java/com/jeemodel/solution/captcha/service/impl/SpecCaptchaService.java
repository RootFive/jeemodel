package com.jeemodel.solution.captcha.service.impl;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.jeemodel.solution.captcha.byec.SpecCaptcha;
import com.jeemodel.solution.captcha.dto.CaptchaImage;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Rootfive
 * png格式验证码
 */
@Slf4j
@Service("SpecCaptcha")
public class SpecCaptchaService extends AbstractCaptchaService {

	@Override
	public CaptchaImage createCaptcha(int width, int height) {
		int nextInt = new Random().nextInt(10);
		SpecCaptcha specCaptcha = new SpecCaptcha(width, height);
		// 设置：验证码随机字符长度
		specCaptcha.setLen(4);
		try {
			// 设置：验证码的字体
			specCaptcha.setFont(nextInt, 32f);
		} catch (IOException | FontFormatException e) {
			log.warn("生成验证码出现异常：", e);
		}
		return super.toCaptchaImage(specCaptcha);
	}

}
