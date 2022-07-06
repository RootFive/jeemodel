package com.jeemodel.core.utils;


import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 切面工具类
 * @author Rootfive
 *
 */
@Slf4j
public class AspectUtils {
	
	
	/**
	 * @param <A>
	 * @param joinPoint （AspectJ 使用 org.aspectj.lang.JoinPoint 接口表示目标类连接点对象）
	 * @param annotationType
	 * @return 获取注解，优先获取方法上，不在方法上，那就获取类上的
	 * @throws Exception 
	 */
	public static <A extends Annotation> A getAnnotation(JoinPoint joinPoint, Class<A> annotationType) throws Exception {
		
		//尝试获取切点，优先获取方法上
		Method method = getMethod(joinPoint);
		if (method != null) {
			return getAnnotation(method, annotationType);
		}
		// 切点不在方法上，那就获取到类上的
		return getAnnotation(getConstructor(joinPoint), annotationType);
	}

	/**
	 * @param joinPoint
	 * @return 获取切面编程的一个方法对象
	 * @throws Exception
	 */
	public final static Method getMethod(JoinPoint joinPoint) throws Exception {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();
		log.debug("method={}", method);
		return method;
	}
	
	/**
	 * @param joinPoint
	 * @return 获取切面编程的一个方法对象
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public final static Constructor getConstructor(JoinPoint joinPoint) throws Exception {
		Signature signature = joinPoint.getSignature();
		ConstructorSignature constructorSignature = (ConstructorSignature) signature;
		Constructor constructor = constructorSignature.getConstructor();
		log.debug("constructor={}", constructor);
		return constructor;
	}
	
	
	/**
	 * @param <A>
	 * @param executable （可以是类对象，也可以是方法对象）
	 * @param annotationType
	 * @return 获取注解方法
	 */
	public static <A extends Annotation> A getAnnotation(Executable executable, Class<A> annotationType) {
		return AnnotationUtils.getAnnotation(executable, annotationType);
	}
	
}
