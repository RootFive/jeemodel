package com.jeemodel.bean.exception.base;

import com.jeemodel.bean.enums.code.sub.ISubCodeSystem;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>基础系统异常：直白讲就是系统出错了，或程序有Bug，或环境有问题。用户（接口调用方）自己无法解决。</p>
 * <blockquote>
 * 		<pre>用户对这些异常是无能为力的，碰到这类异常系统统一处理-就是告诉用户:系统出现异常了，请报告给管理员....</pre>
 * 		<pre>比如空指针，SQL语法错误，数据库连不上，代码有bug等等。</pre>
 * 		<pre>这类异常一出现，一定要LOG记录下来，维护人员要第一时间就要去解决的，所以异常要带足够的信息，比如：
 * 		 1、数据访问不了，数据库的名称、host、端口号都log打印出来。
 * 		 2、调用WS出错的，就要把当时的URL、参数、http method也打印出来。
 * 		这类异常，最好是log里一看就知道哪里出问题了。</pre>
 * </blockquote>
 * 
 * @author Rootfive
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseSystemException extends BaseJeeModelException {

	private static final long serialVersionUID = 1L;

	/**
	 * 空构造方法，避免反序列化问题，禁止在程序中使用
	 */
	@Deprecated
	public BaseSystemException() {
	}
	
	//===============
	// 上面是基础构造 XXX
	// 下面是拓展构造 XXX
	//===============
	
	
	/**
	 * 构造方法：记录栈异常，生成栈追踪信息
	 * @param subCodeSystem
	 */
	public BaseSystemException(ISubCodeSystem subCodeSystem) {
		super(subCodeSystem,subCodeSystem.getExplain(),true);
	}
	
	/**
	 * 构造方法：记录栈异常，生成栈追踪信息
	 * @param subCodeSystem
	 * @param writableStackTrace 表示是否生成栈追踪信息
	 * @param template 异常的描述信息模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 */
	public BaseSystemException(ISubCodeSystem subCodeSystem, boolean writableStackTrace, String template, Object... args) {
		super( subCodeSystem,getCustomMsg( subCodeSystem, template,args),writableStackTrace);
	}
	
	/**
	 * 构造方法：记录栈异常，生成栈追踪信息
	 * 包含message和cause, 会记录栈异常
	 * @param subCode
	 * @param cause   导致此异常发生的父异常，即追踪信息里的caused by
	 * @param template 异常的描述信息模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 */
	public BaseSystemException(ISubCodeSystem subCodeSystem,Throwable cause, String template, Object... args) {
		super(subCodeSystem,getCustomMsg(subCodeSystem, template,args),cause);
	}
}
