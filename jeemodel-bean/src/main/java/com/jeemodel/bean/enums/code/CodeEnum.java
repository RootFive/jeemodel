package com.jeemodel.bean.enums.code;

/**
 * @author Rootfive  
 * <blockquote>
 * <pre>响应参数-网关返回码Code</pre>
 * </blockquote>
 * @since   JeeMode2022
 */
public enum CodeEnum {

	/**
	 * 	调用成功：OK
	 *  <blockquote>
	 *  <pre>解决方案：调用成功，正常处理业务</pre>
	 *  <pre>Code描述：接受到被调用接口的成功响应。</pre>
	 *  <pre>灵感来源：哦了（地区口头禅），意思是"OK，事情办完了"</pre>
	 *  </blockquote>
	 */
	OK	(0, 	"调用成功"),
	
	/**
	 * 	错误的请求：ERROR_110
	 * <blockquote>
	 * <pre>解决方案：调用失败，检查请求参数</pre>
	 * <pre>Code描述：属于技术性错误，请调用方技术同学重新核实接口：
	 *	1、地址、报文格式、公共参数信息（请求渠道、请求时间戳、请求签名和API信息等）
	 *	2、除上述几种类型之外，还有一些可疑的请求异常（比如非法攻击、暴力攻击等）。</pre>
	 * <pre>灵感来源：匪警电话110</pre>
	 * </blockquote>
	 */
	ERROR_110	(110, 	"请求错误"),
	
	/**
	 *	 缺少必选参数：MISSING_119
	 * <blockquote>
	 * <pre>解决方案：调用失败，检查请求参数，补足请求缺失的参数之后再重新尝试</pre>
	 * <pre>Code描述：缺少必选参数。属于技术性错误，请调用方技术同学重新核实接口：必须传的参数信息，然后重新尝试</pre>
	 * <pre>灵感来源：火警电话119</pre>
	 * </blockquote>
	 */
	MISSING_119	(119, 	"缺少参数"),
	
	/**
	 * 	非法的参数：WARN_120
	 * <blockquote>
	 * <pre>解决方案：调用失败，调用接口前，请先检查参数的有效性。</pre>
	 * <pre>Code描述：参数值非法、无效，请调用方技术同学检查参数：
	 * 	类型格式、非法不合规、长度大小越界值超出限制等。调用前，请自行校验。</pre>
	 * <pre>灵感来源：医疗救援电话120</pre>
	 * </blockquote>
	 */
	WARN_120	(120, 	"参数非法"), 
	
	/**
	 * 	业务处理失败：FAIL_122
	 * <blockquote>
	 * <pre>解决方案：本次失败，换个方式重新尝试</pre>
	 * <pre>Code描述：业务验证未通过，可能的场景：
	 * 	用户操作错误、用户的账号问题、业务场景的逻辑校验等，
	 * 	注意，这是正常的业务提示响应，换个方式重新尝试，一般无需技术性修复。</pre>
	 * <pre>灵感来源：群众交通事故报警电话122</pre>
	 * </blockquote>
	 */
	FAIL_122	(122, 	"执行失败"), 
	
	
	/**
	 * 	未知的错误：UNKNOWN_112112
	 * <blockquote>
	 * <pre>解决方案：人工核实</pre>
	 * <pre>【注意：如果调用方是合作商户：请务必人工核实结果后再处理】</pre>
	 * <pre>Code描述：接口被调用的服务方无法定位具体的错误原因，是一种未知的异常错误，请调用方人工核实，不要根据这个code判断结果。</pre>
	 * <pre>灵感来源：通用紧急求救热线112语音提示，当手机拨打112时，通话是一段录音：“你好！匪警请拨110，火警请拨119，医疗急救请拨120，交通事故请拨122......”</pre>
	 * </blockquote>
	 */
	UNKNOWN_112112	(112112, 	"未知错误"),
	
	/**
	 * 服务不可用：SOS_191519
	 * <blockquote>
	 * <pre>解决方案：本次失败，也可以稍后重试，如果有可能的话，请联系服务方工作人员紧急处理</pre>
	 * <pre>Code描述：服务暂不可用。有三种情况：网关自身的未知错误、业务系统不可用、服务人工维护临时关闭</pre>
	 * <pre>灵感来源：国际摩尔斯电码救难信号SOS，S、O、S在26个英文字母中的顺序分别为19、15、19，所以191519是另一种隐晦的传递和表达求救讯息的符号。</pre>
	 * </blockquote>
	 */
	SOS_191519	(191519, 	"服务不可用"),
	
	
	;

	private Integer code;

	private String msg;
	
	private CodeEnum() {
	}

	private CodeEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer code() {
		return code;
	}

	public String msg() {
		return msg;
	}

}
