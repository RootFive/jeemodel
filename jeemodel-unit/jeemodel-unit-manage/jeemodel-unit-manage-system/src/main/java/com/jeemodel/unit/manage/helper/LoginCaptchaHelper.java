package com.jeemodel.unit.manage.helper;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

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

	@Value("${jeemodel.unit.manage.system.captcha.type:0}")
	private Integer captchaType;

	@Override
	protected boolean sceneToGetCaptchaOnOff(String scene) {
		return configService.selectCaptchaOnOff();
	}

	@Override
	protected CaptchaTypeEnum sceneToGetCaptchaType(String scene) {
		// 生成验证码
		CaptchaTypeEnum codeType = CaptchaTypeEnum.getCaptchaTypeByTypeCode(captchaType);

		return codeType != null ? codeType : CaptchaTypeEnum.ARITHMETIC;
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
