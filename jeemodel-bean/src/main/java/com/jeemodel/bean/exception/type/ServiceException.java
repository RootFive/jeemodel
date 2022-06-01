package com.jeemodel.bean.exception.type;

import com.jeemodel.bean.enums.code.sub.ISubCodeSystem;
import com.jeemodel.bean.enums.code.sub.impl.SosCodeEnum;
import com.jeemodel.bean.exception.base.BaseSystemException;

import lombok.Getter;

/**
 * 服务异常
 * @author Rootfive
 *
 */
@Getter
public final class ServiceException extends BaseSystemException {

	private static final long serialVersionUID = 1L;

	/**
	 * 空构造方法，避免反序列化问题
	 */
	@Deprecated
	public ServiceException() {
	}

	//===============
	// 上面是基础构造 XXX
	// 下面是拓展构造 XXX
	//===============
	/**
	 * 自定义描述
	 * @param subCode
	 * @param writableStackTrace 表示是否生成栈追踪信息
	 * @param template 字符串模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 */
	private ServiceException(ISubCodeSystem subCodeSystem, boolean writableStackTrace, String template, Object... args) {
		super(subCodeSystem,writableStackTrace,  template, args);
	}
	
	//===============
	// 上面是拓展构造 XXX
	// 下面是静态构造 XXX
	//===============
	
	/**
	 * 程序错误
	 * @return
	 */
	public static ServiceException progError() {
		return progError( null);
	}
	
	/**
	 * 程序错误
	 * @param template 异常的描述信息模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 * @return
	 */
	public static ServiceException progError(String template, Object... args) {
		return new ServiceException(SosCodeEnum.SOS_PROG_E, true, template,args);
	}
	
	
	/**
	 * 子服务器宕机不可用
	 * @return
	 */
	public static ServiceException subServiceDown() {
		return subServiceDown(null);
	}
	
	
	/**
	 * 子服务器宕机不可用
	 * @param template 异常的描述信息模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 * @return
	 */
	public static ServiceException subServiceDown(String template, Object... args) {
		return new ServiceException(SosCodeEnum.SOS_SUB_SERVICE, true, template,args);
	}


}