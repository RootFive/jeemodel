package com.jeemodel.core.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import com.jeemodel.bean.exception.type.UtilsException;
import com.jeemodel.core.utils.spring.SpringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 获取i18n资源文件
 * 
 * @author Rootfive
 */
@Slf4j
public class MessageUtils {
	/**
	 * 根据消息键和参数 获取消息 委托给spring messageSource
	 *
	 * @param code 消息键
	 * @param args 参数
	 * @return 获取国际化翻译值
	 */
	public static String message(String code, Object... args) {
		MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
		try {
			return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
		} catch (NoSuchMessageException e) {
			log.error("NoSuchMessageException={}", e);
			throw new UtilsException("获取自定义消息异常");
		}
	}
}
