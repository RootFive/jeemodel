package com.jeemodel.unit.idcode.bean.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.jeemodel.bean.dto.BaseEditSaveExpand;
import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 场景标识 的编辑保存对象，属于DTO
 * 
 * Entity实体类对象 【com.jeemodel.unit.idcode.bean.entity.UseScene】
 * 
 * @author JeeModel
 * @date 2022-07-04
 */
@ApiModel(description = "场景标识-编辑保存")
@Data
@EqualsAndHashCode(callSuper = true)
public class UseSceneEditSave extends BaseEditSaveExpand {

	@ApiModelProperty(value = "场景标识-名称")
	@NotEmpty
	private String sceneName;

	@ApiModelProperty(value = "标识-长度（3-6）")
	@Max(6)
	@Min(3)
	private Integer uidLength;

	@ApiModelProperty(value = "标识序列号")
	@Min(0)
	private Integer uidSerial;

	@ApiModelProperty(value = "状态（0正常 1停用）")
	private String status;

	@Override
	public String toString() {
		JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
		builder.append("id", getId());
		builder.append("sceneName", getSceneName());
		builder.append("uidLength", getUidLength());
		builder.append("uidSerial", getUidSerial());
		builder.append("status", getStatus());
		return builder.toString();
	}
}
