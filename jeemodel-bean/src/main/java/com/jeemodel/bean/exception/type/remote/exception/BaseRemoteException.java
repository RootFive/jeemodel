package com.jeemodel.bean.exception.type.remote.exception;

import com.jeemodel.bean.enums.code.sub.ISubCodeSystem;
import com.jeemodel.bean.enums.code.sub.impl.SosCodeEnum;
import com.jeemodel.bean.exception.base.BaseSystemException;

/**
 * 远程访问异常
 */
public class BaseRemoteException extends BaseSystemException {
	private static final long serialVersionUID = 1L;

	/**
	 * 空构造方法，避免反序列化问题
	 */
	@Deprecated
	public BaseRemoteException() {
	}

	/**
	 * 自定义描述
	 * @param subCode
	 * @param writableStackTrace 表示是否生成栈追踪信息
	 * @param template 字符串模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 */
	private BaseRemoteException(ISubCodeSystem subCodeSystem, boolean writableStackTrace, String template,
			Object... args) {
		super(subCodeSystem, writableStackTrace, template, args);
	}

	//===============
	// 上面是拓展构造 XXX
	// 下面是静态构造 XXX
	//===============

	/**
	 * 远程访问异常
	 * @param template 异常的描述信息模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 * @return
	 */
	public BaseRemoteException(String template, Object... args) {
		this(SosCodeEnum.SOS_REMOTING, true, template, args);
	}

	/**
	 * 远程访问异常
	 * @param template 异常的描述信息模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 * @return
	 */
	public static BaseRemoteException remoting(String template, Object... args) {
		return new BaseRemoteException(template, args);
	}

	/**
	 * 远程访问异常
	 * @param template 异常的描述信息模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 * @return
	 */
	public BaseRemoteException(Throwable cause, String template, Object... args) {
		super(SosCodeEnum.SOS_REMOTING, cause, template, args);
	}

	/**
	 * 远程访问异常
	 * @param template 异常的描述信息模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 * @return
	 */
	public static BaseRemoteException remoting(Throwable cause, String template, Object... args) {
		return new BaseRemoteException(cause, template, args);
	}

}
