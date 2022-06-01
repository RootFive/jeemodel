package com.jeemodel.solution.captcha.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import com.google.code.kaptcha.Producer;
import com.jeemodel.bean.exception.type.UtilsException;
import com.jeemodel.core.utils.sign.Base64;
import com.jeemodel.solution.captcha.dto.CaptchaImage;
import com.jeemodel.solution.captcha.service.ICaptchaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("ArithmeticCaptcha")
public class ArithmeticCaptchaService  implements ICaptchaService{

	//@Resource(name = "captchaProducer")
	//private Producer captchaProducer;

	@Resource(name = "captchaProducerMath")
	private Producer captchaProducerMath;

	// 验证码类型:默认数学计算 math 数学计算 char 字符验证
	//private String captchaType ="math";
	
	
	@Override
	public CaptchaImage createCaptcha(int width, int height) {
		
		
		String capText = captchaProducerMath.createText();
		String capStr = capText.substring(0, capText.lastIndexOf("@"));
		String code = capText.substring(capText.lastIndexOf("@") + 1).toLowerCase();;
		BufferedImage image = captchaProducerMath.createImage(capStr);
		// 转换流信息写出
		FastByteArrayOutputStream os = new FastByteArrayOutputStream();
		try {
			ImageIO.write(image, "jpg", os);
			String img ="data:image/gif;base64," +  Base64.encode(os.toByteArray());
			return CaptchaImage.builder().code(code).img(img).build();
		} catch (IOException e) {
			log.warn("验证码生成出现异常",e);
			throw new UtilsException("验证码（校验码）获取异常"); 
		}
	}
}
