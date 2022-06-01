package com.jeemodel.bean.helper;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Rootfive 	@date:   2021年5月28日
 * @Description:JSON构建工具
 */
public class JeeModelToStringBuilder extends ToStringBuilder{

	public JeeModelToStringBuilder(final Object object) {
		super(object, JeeModelToStringJSONStyle.JSON_STYLE, null);
	}

}
