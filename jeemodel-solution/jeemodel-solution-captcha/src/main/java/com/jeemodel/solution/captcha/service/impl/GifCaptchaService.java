package com.jeemodel.solution.captcha.service.impl;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.jeemodel.solution.captcha.dto.CaptchaImage;
import com.wf.captcha.GifCaptcha;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Rootfive
 *	Gif验证码类
 */
@Slf4j
@Service("GifCaptcha")
public class GifCaptchaService extends AbstractCaptchaService{

	@Override
	public CaptchaImage createCaptcha(int width, int height) {
		int nextInt = new Random().nextInt(10);
		GifCaptcha gifCaptcha = new GifCaptcha(width, height);
		gifCaptcha.setLen(5);
		try {
			gifCaptcha.setFont(nextInt, 32f);
		} catch (IOException | FontFormatException e) {
			log.warn("生成验证码出现异常：", e);
		}
		return super.toCaptchaImage(gifCaptcha);
	}

}
