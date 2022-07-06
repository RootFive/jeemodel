package com.jeemodel.unit.manage.system.show;

import com.jeemodel.bean.enums.code.sub.impl.FAILCodeEnum;
import com.jeemodel.bean.exception.base.BaseServiceLogicException;
import com.jeemodel.core.utils.StringUtils;

/**
 * 演示模式异常
 */
public final class DemoException extends BaseServiceLogicException {
	
	private static final long serialVersionUID = 1L;
	
	public DemoException() {
		super(FAILCodeEnum.FAIL, "演示模式不支持！");
	}
	
	public DemoException(String customMsg) {
		super(FAILCodeEnum.FAIL, StringUtils.trimToEmpty(customMsg)+"演示模式不支持！");
	}
}
