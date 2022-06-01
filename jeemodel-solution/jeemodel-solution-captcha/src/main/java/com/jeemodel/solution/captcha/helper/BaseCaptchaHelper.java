package com.jeemodel.solution.captcha.helper;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeemodel.bean.exception.type.CheckException;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.solution.captcha.constant.CaptchaConstants;
import com.jeemodel.solution.captcha.dto.CaptchaImage;
import com.jeemodel.solution.captcha.dto.CaptchaImageReq;
import com.jeemodel.solution.captcha.dto.CaptchaImageResp;
import com.jeemodel.solution.captcha.enums.CaptchaSubCode;
import com.jeemodel.solution.captcha.enums.CaptchaTypeEnum;
import com.jeemodel.solution.captcha.service.ICaptchaService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Rootfive
 *	前后端分离项目建议不要存储在session中，存储在redi或者数据库中，redis存储需要一个key，key一同返回给前端用于验证输入：
 */
@Slf4j
public abstract class BaseCaptchaHelper {

	@Autowired
	private Map<String, ICaptchaService> captchaServiceMap;

	/**
	 * 构建验证码图片
	 * @param captchaImageReq
	 * @return
	 */
	public CaptchaImageResp buildCaptcha(CaptchaImageReq captchaImageReq) {
		log.debug("captchaImageReq={}", captchaImageReq);
		// 应用场景、情景
		String scene = (captchaImageReq == null) ? null : captchaImageReq.getScene();
		
		// 验证码开关
		boolean captchaOnOff = StringUtils.isBlank(scene)? true:sceneToGetCaptchaOnOff(scene);
		CaptchaImageResp imageResp = new CaptchaImageResp();
		imageResp.setCaptchaOnOff(captchaOnOff);
		if (!captchaOnOff) {
			return imageResp;
		}
		
		// 验证码类型枚举
		CaptchaTypeEnum captchaType = null;
		// 根据应用场景，获取验证码类型
		if (StringUtils.isBlank(scene)) {
			//应用场景为空，默认需要验证码，，默认算术验证码
			captchaType = CaptchaTypeEnum.ARITHMETIC;
		}else {
			captchaType = sceneToGetCaptchaType(scene);
			// 验证码类型为空，默认算术验证码
			if (captchaType == null) {
				captchaType = CaptchaTypeEnum.ARITHMETIC;
			}
		}
		
		//获取验证码实现
		String captchaTypeBeanName = captchaType.getBeanName();
		ICaptchaService captchaService = captchaServiceMap.get(captchaTypeBeanName);

		int width = 0;
		int height = 0;

		if (captchaImageReq != null) {
			width = captchaImageReq.getWidth();
			height = captchaImageReq.getHeight();
		}

		if (width <= 0 || height <= 0) {
			width = 160;
			height = 60;
		}

		// 创建验证码图片
		CaptchaImage captcha = captchaService.createCaptcha(width, height);
		
		// 验证码缓存codeKey（uuid格式）
		String codeKey = UUID.randomUUID().toString();
		// 创建验证码codeKey
		String codeCacheKey = CaptchaConstants.CAPTCHA_CODE_KEY_PREFIX + codeKey;

		// codeValue(答案)存入缓存
		String codeValue = captcha.getCode();
		String img = captcha.getImg();
		sceneToSetCaptchaToCache(codeCacheKey, codeValue);

		// 将codeKey和Captcha图片返回给前端
		imageResp.setUuid(codeKey);
		imageResp.setImg(img);
		return imageResp;
	}

	/**
	 * 校验验证码
	 * @param codeKey
	 * @param checkCode
	 * @return
	 */
	public boolean verifyCaptcha(String checkCode, String codeKey) {
		if (StringUtils.isBlank(checkCode) || StringUtils.isBlank(codeKey)) {
			// 验证码为空
			throw new CheckException(CaptchaSubCode.CU101);
		}

		// 验证码缓存Key
		String codeCacheKey = CaptchaConstants.CAPTCHA_CODE_KEY_PREFIX + codeKey;
		// 验证码缓存Value
		String codeCacheValue = sceneToGetCaptchaAndRemoveCacheValue(codeCacheKey);
		if (StringUtils.isBlank(codeCacheValue)) {
			// 验证码缓存Value为空，已经过期
			throw new CheckException(CaptchaSubCode.CU102);
		}

		// 忽略大小写判断验证码是否一致
		if (StringUtils.equalsIgnoreCase(checkCode, codeCacheValue)) {
			// 验证码与缓存一致,校验通过
			return true;
		} else {
			// 验证码与缓存不一致,错误
			throw new CheckException(CaptchaSubCode.CU103);
		}
	}

	/**
	 * 根据应用场景--获取验证码开关
	 * @param scene
	 * @return
	 */
	protected abstract boolean sceneToGetCaptchaOnOff(String scene);
	
	/**
	 * 根据应用场景--获取验证码类型枚举
	 * @param scene
	 * @return
	 */
	protected abstract CaptchaTypeEnum sceneToGetCaptchaType(String scene);

	/**
	 * 根据应用场景--把验证码的codeCacheKey和Value存放在缓存,缓存时间各个业务场景自己校验实现
	 * @param codeCacheKey
	 * @param codeValue
	 */
	protected abstract void sceneToSetCaptchaToCache(String codeCacheKey, String codeValue);

	/**
	 * 根据应用场景--从缓存获取验证码,并删除缓存中的验证码（确保验证码一次性使用）
	 * @param codeCacheKey  缓存Key
	 * @return
	 */
	protected abstract String sceneToGetCaptchaAndRemoveCacheValue(String codeCacheKey);

}
