package com.jeemodel.bean.exception.base;

import com.jeemodel.bean.enums.code.sub.ISubCodeServiceLogic;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>业务逻辑异常</p>
 * <blockquote>
 * 		<pre>中文名称:业务逻辑</pre>
 * 		<pre>英文名称:service logic;SL。</pre>
 * </blockquote>
 * @author Rootfive
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseServiceLogicException extends BaseJeeModelException {

	private static final long serialVersionUID = 1L;

	/**
	 * 空构造方法，避免反序列化问题，禁止在程序中使用
	 */
	@Deprecated
	public BaseServiceLogicException() {
	}

	// ===============
	// 上面是基础构造 XXX
	// 下面是拓展构造 XXX
	// ===============

	/**
	 * 构造方法：不记录栈异常, 性能最高
	 * @param subCode
	 */
	public BaseServiceLogicException(ISubCodeServiceLogic subCodeServiceLogic) {
		super(subCodeServiceLogic, subCodeServiceLogic.getExplain());
	}

	/**
	 * 构造方法：不记录栈异常, 性能最高
	 * @param subCode
	 * @param customMsg
	 */
	public BaseServiceLogicException(ISubCodeServiceLogic subCodeServiceLogic, String template, Object... args) {
		super(subCodeServiceLogic, getCustomMsg(subCodeServiceLogic, template, args));
	}

	/**
	 * 构造方法：可以记录栈异常，生成栈追踪信息
	 * @param subCode
	 * @param writableStackTrace 表示是否生成栈追踪信息
	 */
	public BaseServiceLogicException(ISubCodeServiceLogic subCodeServiceLogic, boolean writableStackTrace,
			String template, Object... args) {
		super(subCodeServiceLogic, getCustomMsg(subCodeServiceLogic, template, args), writableStackTrace);
	}

}
