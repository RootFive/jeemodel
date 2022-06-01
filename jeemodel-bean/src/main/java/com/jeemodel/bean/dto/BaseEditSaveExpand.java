package com.jeemodel.bean.dto;

import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Rootfive 2020-12-24	 联系方式: QQ群：2236067977  
 * <p>Description: 编辑保存-拓展基类</p>
 */
@ApiModel(description = "编辑保存-拓展基类")
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseEditSaveExpand extends BaseEditSave {

	@ApiModelProperty(value = "停用状态:0正常,1停用", example = "0")
	protected  String status;

	@Override
	public String toString() {
		JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
		builder.append("id", id);
		builder.append("remark", remark);
		builder.append("status", status);
		return builder.toString();
	}
}