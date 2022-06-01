package com.jeemodel.bean.dto;

import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rootfive 2020-12-24	 联系方式: QQ群：2236067977  
 * <p>Description: 编辑保存基类</p>
 * Java 代码注释——TODO、FIXME、XXX
 * TODO: + 说明： 标识处有功能代码待编写
 * FIXME: + 说明：标识处代码需要修正
 * XXX：+ 说明：标识处代码待改进
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "编辑保存基类")
public abstract class BaseEditSave {

	@ApiModelProperty(value = "主键ID", example = "1")
	protected Long id;

	@ApiModelProperty(value = "备注", example = "研发测试")
	protected String remark;

	@Override
	public String toString() {
		JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
		builder.append("id", id);
		builder.append("remark", remark);
		return builder.toString();
	}
}