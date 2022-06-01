package com.jeemodel.core.utils;

import java.io.PrintWriter;
import java.io.StringWriter;


import com.jeemodel.bean.enums.code.sub.ISubCodeServiceLogic;
import com.jeemodel.bean.enums.code.sub.impl.ErrorCodeEnum;
import com.jeemodel.bean.enums.code.sub.impl.FAILCodeEnum;
import com.jeemodel.bean.enums.code.sub.impl.MissingCodeEnum;
import com.jeemodel.bean.enums.code.sub.impl.UnknownCodeEnum;
import com.jeemodel.bean.enums.code.sub.impl.WarnCodeEnum;
import com.jeemodel.bean.exception.base.BaseServiceLogicException;
import com.jeemodel.bean.exception.base.BaseSystemException;
import com.jeemodel.bean.exception.type.CheckException;
import com.jeemodel.bean.exception.type.ServiceException;

/**
 * 工具类异常
 * 
 * @author Rootfive
 */
public class ExceptionUtils  extends org.apache.commons.lang3.exception.ExceptionUtils {
	
	/**
	 * 自定义
	 * @param subCode
	 * @return
	 */
	public static BaseServiceLogicException build(ISubCodeServiceLogic subCodeServiceLogic) {
		return new BaseServiceLogicException(subCodeServiceLogic);
	}
	
	public static BaseServiceLogicException customMsg(ISubCodeServiceLogic subCodeServiceLogic, String template, Object... args) {
		return new BaseServiceLogicException(subCodeServiceLogic, template, args);
	}

	
	/**
	 * 错误的请求：ERROR_110
	 * @return
	 */
	public static BaseSystemException error() {
		return new BaseSystemException(ErrorCodeEnum.ERROR);
	}
	
	/**
	 * 错误的请求：ERROR_110
	 * @param template 字符串模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 * @return
	 */
	public static BaseSystemException error(String template, Object... args) {
		return new BaseSystemException(ErrorCodeEnum.ERROR,true,template, args);
	}
	
	/**
	 * 缺少必选参数：MISSING_119
	 * @return
	 */
	public static CheckException missing() {
		return new CheckException(MissingCodeEnum.MISSING);
	}
	
	/**
	 * 缺少必选参数：MISSING_119
	 * @param template 字符串模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 * @return
	 */
	public static CheckException missing(String template, Object... args) {
		return new CheckException(MissingCodeEnum.MISSING,template, args);
	}
	
	
	
	/**
	 * 非法的参数：WARN_120
	 * @return
	 */
	public static CheckException warn() {
		return new CheckException(WarnCodeEnum.WARN);
	}
	
	/**
	 * 非法的参数：WARN_120
	 * @param template 字符串模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 * @return
	 */
	public static CheckException warn(String template, Object... args) {
		return new CheckException(WarnCodeEnum.WARN,template, args);
	}
	
	
	/**
	 * 业务处理失败：FAIL_122
	 * @return
	 */
	public static CheckException fail() {
		return new CheckException(FAILCodeEnum.FAIL);
	}
	
	/**
	 * 业务处理失败：FAIL_122
	 * @param template 字符串模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 * @return
	 */
	public static CheckException fail(String template, Object... args) {
		return new CheckException(FAILCodeEnum.FAIL,template, args);
	}
	
	
	
	/**
	 * 未知的错误：UNKNOWN_112_112
	 * @return
	 */
	public static BaseSystemException unknown() {
		return new BaseSystemException(UnknownCodeEnum.UNKNOWN);
	}
	
	/**
	 * 未知的错误：UNKNOWN_112_112
	 * @param template 字符串模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 * @return
	 */
	public static BaseSystemException unknown(String template, Object... args) {
		return new BaseSystemException(UnknownCodeEnum.UNKNOWN,true,template, args);
	}
	
	
	/**
	 * 返回API维护异常
	 * @return
	 */
	public static BaseServiceLogicException offAPI() {
		return offAPI(null);
	}
	
	/**
	 * 返回API维护异常
	 * @param template 异常的描述信息模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 * @return
	 */
	public static BaseServiceLogicException offAPI(String template, Object... args) {
		return new BaseServiceLogicException(FAILCodeEnum.F0103, template, args);
	}
	
	
	
	/**
	 * 返回系统维护异常
	 * @return
	 */
	public static BaseServiceLogicException offSystem() {
		return offSystem( null);
	}
	
	/**
	 * 返回系统维护异常
	 * @param template 异常的描述信息模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 * @return
	 */
	public static BaseServiceLogicException offSystem(String template, Object... args) {
		return new BaseServiceLogicException(FAILCodeEnum.F0104, template, args);
	}
	
	
	
	/**
	 * 程序错误
	 * @return
	 */
	public static ServiceException sosProgError() {
		return sosProgError( null);
	}
	
	/**
	 * 程序错误
	 * @param template 异常的描述信息模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 * @return
	 */
	public static ServiceException sosProgError(String template, Object... args) {
		return ServiceException.progError(template,args);
	}
	
	
	/**
	 * 子服务器宕机不可用
	 * @return
	 */
	public static ServiceException sosSubServiceDown() {
		return sosSubServiceDown(null);
	}
	
	
	/**
	 * 子服务器宕机不可用
	 * @param template 异常的描述信息模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 * @return
	 */
	public static ServiceException sosSubServiceDown(String template, Object... args) {
		return ServiceException.subServiceDown( template,args);
	}
	
	
	/**
	 * 获取exception的详细错误信息。
	 */
	public static String getExceptionMessage(Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw, true));
		String str = sw.toString();
		return str;
	}

	public static String getRootErrorMessage(Exception e) {
		Throwable root = ExceptionUtils.getRootCause(e);
		root = (root == null ? e : root);
		if (root == null) {
			return "";
		}
		String msg = root.getMessage();
		if (msg == null) {
			return "null";
		}
		return StringUtils.defaultString(msg);
	}
}
