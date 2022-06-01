package com.jeemodel.bean.exception.base;

import org.apache.commons.lang3.StringUtils;

import com.jeemodel.bean.enums.code.sub.IBaseSubCode;
import com.jeemodel.bean.utils.StringFormatUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 异常规范 @see https://docs.oracle.com/javase/specs/jls/se8/html/jls-11.html
 * 
 * <p>JeeModel自定义基础异常：通过使用父类中4参数的构造方法精确控制异常类的行为</p>
 * <blockquote>
 * 		<pre>当我们想要创建"轻量级"异常时，使用第2个构造方法即可{@link #BaseJeemodelException(String)}</pre>
 * 		<pre>如果我们想将系统级异常封装成一下，并希望在日志中打印栈追踪时，就使用第4个构造方法{@link #BaseJeemodelException(String, Throwable)}</pre>
 * 		<pre>PS: 只有在高并发系统中做上述优化才会有明显效果。如果抛异常不频繁的话也不会有明显效果，因为即便是慢50倍，实际也是纳秒级的区别，对一个请求处理来说微不足道。</pre>
 * 		
 * 		<pre>API相应异常：此异常类在业务程序中，需要终止程序返回API非正常返回时使用，由全局异常拦截 GlobalExceptionHandler</pre>
 * 		<pre>GlobalExceptionHandler拦截之后，调用ApiRespUtils工具类解析业务异常，封装API响应</pre>
 * </blockquote>
 * 
 * @author Rootfive
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseJeeModelException extends RuntimeException  implements IToBeJeeModelException {

	private static final long serialVersionUID = 1L;

	private IBaseSubCode baseSubCode;
	
	private String customMsg;

	/**
	 * 空构造方法，避免反序列化问题，禁止在程序中使用
	 */
	@Deprecated
	public BaseJeeModelException() {
	}

	/**
	 * 仅包含message, 没有cause, 也不记录栈异常, 性能最高
	 * @param message 异常的描述信息
	 */
	private BaseJeeModelException(String message) {
		this(message, false);
	}

	/**
	 * 包含message, 可指定是否生成栈追踪信息
	 * 
	 * @param message          异常的描述信息
	 * @param writableStackTrace 表示是否生成栈追踪信息
	 */
	private BaseJeeModelException(String message, boolean writableStackTrace) {
		this(message, null, false, writableStackTrace);
	}

	/**
	 * 包含message和cause, 会记录栈异常
	 * 
	 * @param message 异常的描述信息
	 * @param cause   导致此异常发生的父异常，即追踪信息里的caused by
	 */
	private BaseJeeModelException(String message, Throwable cause) {
		this(message, cause, false, true);
	}	
	
	/**
	 * @param message			  异常的描述信息，也就是在打印栈追踪信息时异常类名后面紧跟着的描述字符串（the detail message.）	
	 * @param cause				  导致此异常发生的父异常，即追踪信息里的caused by （the cause.  (A {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 * @param enableSuppression   关于异常挂起的参数（whether or not suppression is enabled or disabled）
	 * @param writableStackTrace  表示是否生成栈追踪信息（whether or not the stack trace should be writable）
	 */
	private BaseJeeModelException(String message, Throwable cause, boolean enableSuppression,boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	
	//===============
	// 上面是基础构造 XXX
	// 下面是拓展构造 XXX
	//===============
	
	
	
	/**
	 * 构造方法：不记录栈异常, 性能最高
	 * @param baseSubCode
	 * @param customMsg
	 */
	protected BaseJeeModelException(IBaseSubCode baseSubCode, String customMsg) {
		this(customMsg);
		this.baseSubCode = baseSubCode;
		this.customMsg = customMsg;
	}

	/**
	 * 构造方法：可以记录栈异常，生成栈追踪信息
	 * @param baseSubCode
	 * @param customMsg
	 * @param writableStackTrace 表示是否生成栈追踪信息
	 */
	protected BaseJeeModelException(IBaseSubCode baseSubCode, String customMsg, boolean writableStackTrace) {
		this(customMsg,writableStackTrace);
		this.baseSubCode = baseSubCode;
		this.customMsg = customMsg;
		
	}
	
	/**
	 * 构造方法：记录栈异常，生成栈追踪信息
	 * @param baseSubCode
	 * @param customMsg
	 * @param cause   导致此异常发生的父异常，即追踪信息里的caused by
	 */
	protected BaseJeeModelException(IBaseSubCode baseSubCode, String customMsg, Throwable cause) {
		this(customMsg,cause);
		this.baseSubCode = baseSubCode;
		this.customMsg = customMsg;
	}
	
	//===============
	// 上面是拓展构造 XXX
	// 下面是公共方法 XXX
	//===============
	/**
	 * 自定义描述
	 * @param baseSubCode
	 * @param template 字符串模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 * @return
	 */
	protected static final  String getCustomMsg(IBaseSubCode baseSubCode, String template, Object... args) {
		String customMsg = StringFormatUtils.format(template, args);
		return StringUtils.isNotBlank(customMsg) ? customMsg : baseSubCode.getExplain();
	}

	
	
	

}
