package com.jeemodel.unit.manage.helper;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeemodel.base.annotation.HelpService;
import com.jeemodel.solution.captcha.constant.CaptchaConstants;
import com.jeemodel.solution.captcha.enums.CaptchaTypeEnum;
import com.jeemodel.solution.captcha.helper.BaseCaptchaHelper;
import com.jeemodel.solution.redis.core.cache.RedisCacheHelper;
import com.jeemodel.unit.manage.service.IConfigService;

/**
 * 登陆验证码
 * @author Rootfive
 *
 */
@HelpService
public class LoginCaptchaHelper extends BaseCaptchaHelper {

	@Autowired
	private IConfigService configService;
	
	

	@Autowired
	private RedisCacheHelper redisCacheHelper;
	

	@Override
	protected boolean sceneToGetCaptchaOnOff(String scene) {
		return configService.selectCaptchaOnOff();
	}

	@Override
	protected CaptchaTypeEnum sceneToGetCaptchaType(String scene) {
		// 生成验证码
		String captchaType = System.currentTimeMillis() % 2 == 0 ? "math" : "char";
		if ("math".equals(captchaType)) {
			return CaptchaTypeEnum.ARITHMETIC;
		} else if ("char".equals(captchaType)) {
			return CaptchaTypeEnum.CHINESE;
		}
		return CaptchaTypeEnum.Gif;
	}

	@Override
	protected void sceneToSetCaptchaToCache(String codeCacheKey, String codeValue) {
		redisCacheHelper.setObject(codeCacheKey, codeValue, CaptchaConstants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
	}

	@Override
	protected String sceneToGetCaptchaAndRemoveCacheValue(String codeCacheKey) {
		String codeCacheValue = redisCacheHelper.getObject(codeCacheKey);
		redisCacheHelper.deleteObject(codeCacheKey);
		return codeCacheValue;
	}

}
