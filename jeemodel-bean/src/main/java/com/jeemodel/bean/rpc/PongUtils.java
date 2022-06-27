package com.jeemodel.bean.rpc;

import com.jeemodel.bean.enums.code.sub.IBaseSubCode;
import com.jeemodel.bean.enums.code.sub.impl.ErrorCodeEnum;
import com.jeemodel.bean.enums.code.sub.impl.FAILCodeEnum;
import com.jeemodel.bean.enums.code.sub.impl.MissingCodeEnum;
import com.jeemodel.bean.enums.code.sub.impl.OKCodeEnum;
import com.jeemodel.bean.enums.code.sub.impl.UnknownCodeEnum;
import com.jeemodel.bean.enums.code.sub.impl.WarnCodeEnum;
import com.jeemodel.bean.exception.base.IToBeJeeModelException;
import com.jeemodel.bean.utils.StringFormatUtils;

/**
 * API响应结果工具
 * @author Rootfive
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class PongUtils {

	/**
	 * @return 返回成功消息
	 */
	public static Pong ok() {
		String echo = ApiEchoContext.getEcho();
		return new Pong(echo,OKCodeEnum.ALL);
	}
	
	/**
	 * @return 返回成功,自定义描述消息
	 */
	public static Pong ok(String format, Object... args) {
		String echo = ApiEchoContext.getEcho();
		String customMsg = StringFormatUtils.format(format, args);
		return new Pong(echo,OKCodeEnum.ALL,customMsg);
	}

	/**
	 * @param data 数据对象
	 * @return 返回成功数据消息
	 */
	public static <T> PongData<T> okData(T data) {
		String echo = ApiEchoContext.getEcho();
		return new PongData<T>(echo,OKCodeEnum.ALL, data);
	}
	

	/**
	 * @param <T>
	 * @param data
	 * @param format 字符串模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 * @return
	 */
	public static <T> PongData<T> okData(T data,String format, Object... args) {
		String echo = ApiEchoContext.getEcho();
		String customMsg = StringFormatUtils.format(format, args);
		return new PongData<T>(echo,OKCodeEnum.ALL, data,customMsg);
	}
	
	
	// XXX   上面是成功的方法
	
	/**
	 * @return 错误的请求：ERROR_110
	 */
	public static PongTable error() {
		String echo = ApiEchoContext.getEcho();
		return new PongTable(echo,ErrorCodeEnum.ERROR, null,null);
	}
	
	/**
	 * @param template 字符串模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 * @return 错误的请求：ERROR_110
	 */
	public static PongTable error(String format, Object... args) {
		String echo = ApiEchoContext.getEcho();
		String customMsg = StringFormatUtils.format(format, args);
		return new PongTable(echo,ErrorCodeEnum.ERROR, null,null,customMsg);
	}
	
	
	/**
	 * @return 缺少必选参数：MISSING_119
	 */
	public static PongTable missing() {
		String echo = ApiEchoContext.getEcho();
		return new PongTable(echo,MissingCodeEnum.MISSING, null,null);
	}
	
	/**
	 * @param template 字符串模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 * @return 缺少必选参数：MISSING_119
	 */
	public static PongTable missing(String format, Object... args) {
		String echo = ApiEchoContext.getEcho();
		String customMsg = StringFormatUtils.format(format, args);
		return new PongTable(echo,MissingCodeEnum.MISSING, null,null,customMsg);
	}
	
	/**
	 * @return 非法的参数：WARN_120
	 */
	public static PongTable warn() {
		String echo = ApiEchoContext.getEcho();
		return new PongTable(echo,WarnCodeEnum.WARN, null,null);
	}
	
	/**
	 * @param template 字符串模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 * @return 非法的参数：WARN_120
	 */
	public static PongTable warn(String format, Object... args) {
		String echo = ApiEchoContext.getEcho();
		String customMsg = StringFormatUtils.format(format, args);
		return new PongTable(echo,WarnCodeEnum.WARN, null,null,customMsg);
	}
	
	/**
	 * @return  业务处理失败：FAIL_122
	 */
	public static PongTable fail() {
		String echo = ApiEchoContext.getEcho();
		return new PongTable(echo,FAILCodeEnum.FAIL, null,null);
	}
	
	/**
	 * @param template 字符串模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 * @return 业务处理失败：FAIL_122
	 */
	public static PongTable fail(String format, Object... args) {
		String echo = ApiEchoContext.getEcho();
		String customMsg = StringFormatUtils.format(format, args);
		return new PongTable(echo,FAILCodeEnum.FAIL, null,null,customMsg);
	}
	
	/**
	 * @return 未知的错误：UNKNOWN_112_112
	 */
	public static PongTable unknown() {
		String echo = ApiEchoContext.getEcho();
		return new PongTable(echo,UnknownCodeEnum.UNKNOWN, null,null);
	}
	
	/**
	 * 未知的错误：UNKNOWN_112_112
	 * @param template 字符串模板,文本模板，被替换的部分用 {} 表示
	 * @param args   参数列表
	 * @return
	 */
	public static PongTable unknown(String format, Object... args) {
		String echo = ApiEchoContext.getEcho();
		String customMsg = StringFormatUtils.format(format, args);
		return new PongTable(echo,UnknownCodeEnum.UNKNOWN, null,null,customMsg);
	}
	

	/**
	 * @return	返回接口维护消息
	 */
	public static PongTable offAPI() {
		return offAPI("");
	}
	
	
	/**
	 * @return	返回接口维护消息
	 */
	public static PongTable offAPI(String format, Object... args) {
		String echo = ApiEchoContext.getEcho();
		String customMsg = StringFormatUtils.format(format, args);
		return new PongTable(echo,FAILCodeEnum.F0103, null,null,customMsg);
	}
	
	/**
	 * @return	返回系统维护消息
	 */
	public static PongTable offSystem() {
		return offSystem(null);
	}
	
	
	/**
	 * @return	返回系统维护消息
	 */
	public static PongTable offSystem(String format, Object... args) {
		String echo = ApiEchoContext.getEcho();
		String customMsg = StringFormatUtils.format(format, args);
		return new PongTable(echo,FAILCodeEnum.F0104, null,null,customMsg);
	}
	
	
	
	
	
	
	/**
	 * 	业务处理失败-自定义描述消息
	 * @return 返回失败消息
	 */
	public static PongTable diy(IBaseSubCode baseSubCode) {
		String echo = ApiEchoContext.getEcho();
		return new PongTable(echo,baseSubCode, null,null);
	}
	
	
	/**
	 * 	业务处理失败-自定义描述消息
	 * @return 返回失败消息
	 */
	public static PongTable diy(IBaseSubCode baseSubCode,String format, Object... args) {
		String echo = ApiEchoContext.getEcho();
		String customMsg = StringFormatUtils.format(format, args);
		return new PongTable(echo,baseSubCode, null,null,customMsg);
	}
	
	

	

	/**
	 * 	根据rows的是否大于0 返回成功与否
	 * @param rows
	 * @return 执行结果  rows>0   ?     "成功":"失败"
	 */
	public static Pong result(int rows) {
		return rows > 0 ? ok() : fail("失败了，请联系研发同学");
	}
	
	/**
	 * 	根据result的结果 返回成功与否
	 * @param result
	 * @return 执行结果  result ?  "成功":"失败"
	 */
	public static Pong result(Boolean result) {
		return result !=null && result.booleanValue() == true ? ok() : fail("失败了，请联系研发同学");
	}
	
	
	
	/**
	 * 	解析自定义异常并翻译成正常响应报文
	 * @param ce
	 * @return
	 */
	public static PongTable analysisException(IToBeJeeModelException jmException) {
		//API回声
		String echo = ApiEchoContext.getEcho();
		IBaseSubCode baseSubCode = jmException.getBaseSubCode();
		//自定义消息
		String customMsg = jmException.getMessage();
		return  new PongTable(echo,baseSubCode, null,null,customMsg);
	}
	
	
	
	

}
