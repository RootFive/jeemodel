package com.jeemodel.core.web.exception;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.jeemodel.bean.enums.code.sub.impl.ErrorCodeEnum;
import com.jeemodel.bean.enums.code.sub.impl.MissingCodeEnum;
import com.jeemodel.bean.exception.base.BaseJeeModelException;
import com.jeemodel.bean.exception.base.BaseServiceLogicException;
import com.jeemodel.bean.exception.base.BaseSystemException;
import com.jeemodel.bean.rpc.PongTable;
import com.jeemodel.bean.rpc.PongUtils;
import com.jeemodel.core.utils.ServletUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.core.utils.ip.IpUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	protected void printRequestLog(Throwable t, boolean isError) {
		HttpServletRequest request = ServletUtils.getRequest();
		// 当前请求IP
		String reqIP = IpUtils.getIpAddr(request);
		// 被拦截请求URI
		String reqURI = request.getRequestURI();

		// 请求方式(POST/GET/PUT/DELETE等)
		String reqMethod = request.getMethod();

		if (isError) {
			log.error("请求IP={},reqURI={},reqMethod={},getMessage={}", reqIP, reqURI, reqMethod, t.getLocalizedMessage(), t);
		} else {
			log.warn("请求IP={},reqURI={},reqMethod={},getMessage={}", reqIP, reqURI, reqMethod, t.getLocalizedMessage());
		}
	}
	
	/**
	 * 请求资源找不到异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	public PongTable<?> handlerNoFoundException(Exception e) {
		printRequestLog(e, false);
		return PongUtils.diy(ErrorCodeEnum.E0201);
	}
	
	
	/**
	 * 请求方式不支持
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public PongTable<?> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
			HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		String message = StringUtils.format("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
		log.warn(message);
		printRequestLog(e, false);
		return PongUtils.diy(ErrorCodeEnum.E0204,message);
	}
	
	/**
	 * 参数绑定异常
	 */
	@ExceptionHandler(BindException.class)
	public PongTable<?> handleBindException(BindException e) {
		printRequestLog(e, false);
		String message = e.getAllErrors().get(0).getDefaultMessage();
		return PongUtils.diy(ErrorCodeEnum.E0206,message);
	}

	/**
	 * 方法参数无效异常
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public PongTable<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		printRequestLog(e, false);
		String message =null;
		// 获取异常信息
		BindingResult exceptions = e.getBindingResult();
		// 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
		if (exceptions.hasErrors()) {
			List<ObjectError> errors = exceptions.getAllErrors();
			if (!errors.isEmpty()) {
				// 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
				FieldError fieldError = (FieldError) errors.get(0);
				message = fieldError.getDefaultMessage();
			}
		}else {
			message = e.getBindingResult().getFieldError().getDefaultMessage();
		}
		return PongUtils.diy(MissingCodeEnum.MISSING,message);
	}
	
	/**
	 * <p>出现以下异常，请尽快修复代码bug</p>
	 * <blockquote>
	 * <pre>
	 *     1、java.lang.NullPointerException(空指针异常)	
	 *     		原因：程序中调用了未经初始化的对象或者是不存在的对象
	 *     2、java.lang.ClassNotFoundException			
	 *     		原因：指定的类不存在，比如：调用Class.forName(),或者调用ClassLoad的finaSystemClass(); 或者LoadClass();
	 *     3、java.lang.NumberFormatException		
	 *     		原因：字符串转换为数字异常;
	 *     4、java.lang.IndexOutOfBoundsException	
	 *     		原因：数组下标越界异常;
	 *     5、java.lang.IllegalArgumentException	
	 *     		原因：方法的参数错误,比如g.setColor(int red,int green,int blue)这个方法中的三个值，如果有超过255的也会出现这个异常
	 *     6、java.lang.ArithmeticException	
	 *     		原因：数学运算异常,比如当算术运算中出现了除以零这样的运算就会出这样的异常
	 *     7、java.lang.ClassCastException	
	 *     		原因：数据类型转换异常，试图将对某个对象强制执行向下转型，但该对象又不可转换又不可转换为其子类的实例时将引发该异常
	 * </pre>
	 * </blockquote>
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = { 
			NullPointerException.class, 
			ClassNotFoundException.class, 
			NumberFormatException.class, 
			IndexOutOfBoundsException.class,
			IllegalArgumentException.class, 
			ArithmeticException.class, 
			ClassCastException.class })
	public PongTable<?> handlerProgramException(Exception e) {
		printRequestLog(e, true);
		return PongUtils.unknown();
	}
	
	/**
	 * 自定义-业务逻辑异常
	 */
	@ExceptionHandler(BaseServiceLogicException.class)
	public PongTable<?>  handleBaseServiceLogicException(BaseServiceLogicException e, HttpServletRequest request) {
		printRequestLog(e, false);
		return PongUtils.diy(e.getBaseSubCode(),e.getCustomMsg());
	}
	
	/**
	 * 自定义-系统异常
	 */
	@ExceptionHandler(BaseSystemException.class)
	public PongTable<?>  handleBaseSystemException(BaseSystemException e, HttpServletRequest request) {
		printRequestLog(e, true);
		String message = e.getCustomMsg();
		return PongUtils.diy(e.getBaseSubCode(),message);
	}
	

	/**
	 * 自定义异常
	 */
	@ExceptionHandler(BaseJeeModelException.class)
	public PongTable<?>  handleBaseJMException(BaseJeeModelException e) {
		printRequestLog(e, true);
		return PongUtils.diy(e.getBaseSubCode(),e.getCustomMsg());
	}
	
	/**
	 * 拦截未知的运行时异常
	 */
	@ExceptionHandler(RuntimeException.class)
	public PongTable<?> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
		printRequestLog(e, true);
		return PongUtils.unknown();
	}

	/**
	 * 系统异常
	 */
	@ExceptionHandler(Exception.class)
	public PongTable<?> handleException(Exception e, HttpServletRequest request) {
		printRequestLog(e, true);
		return PongUtils.unknown();
	}
}
