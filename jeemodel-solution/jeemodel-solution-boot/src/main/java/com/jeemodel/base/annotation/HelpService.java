package com.jeemodel.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

/**
 * @author Rootfive
 * 注解 @HelpService 这个注解修饰的作用的类，命名规则建议以 Helper结尾，
 * <pre>
 * 	被标注类-命名规则：建议以 Helper结尾，格式为：XXXXHelper,距离如：UserHelper,
 * 	被标注类-意义作用：用于处理复杂业务的定做（制）处理，特别是组合多个@Service服务实现({@link org.springframework.stereotype.Service})的业务
 * </pre>
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface HelpService {
	
	/**
	 * The value may indicate a suggestion for a logical component name,
	 * to be turned into a Spring bean in case of an autodetected component.
	 * 
	 * 该值建议表示逻辑组件名称，在自动检测到组件的情况下，该名称将转换为springbean
	 * @return the suggested component name, if any (or empty String otherwise)
	 */
	@AliasFor(annotation = Component.class)
	String value() default "";
}