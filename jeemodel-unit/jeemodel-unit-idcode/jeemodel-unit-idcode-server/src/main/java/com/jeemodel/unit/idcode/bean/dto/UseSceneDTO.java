package com.jeemodel.unit.idcode.bean.dto;

import com.jeemodel.bean.annotation.Excel;
import com.jeemodel.bean.dto.BaseDTOCRUDX;
import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 场景标识 的DTO数据传输对象
 * 
 * Entity实体类对象 【com.jeemodel.unit.idcode.entity.bean.UseScene】
 * 
 * @author JeeModel
 * @date 2022-07-04
 */
@ApiModel(description = "场景标识-DTO")
@Data
@EqualsAndHashCode(callSuper = true)
public class UseSceneDTO extends BaseDTOCRUDX {

	@ApiModelProperty(value = "场景标识-名称")
	@Excel(name = "场景标识-名称")
	private String sceneName;

	@ApiModelProperty(value = "场景标识-编码（35进制，最大42874）")
	@Excel(name = "场景标识-编码")
	private String sceneCode;

	@ApiModelProperty(value = "标识-长度（3-6）")
	@Excel(name = "标识-长度")
	private Integer uidLength;

	@ApiModelProperty(value = "标识序列号")
	@Excel(name = "标识序列号")
	private Integer uidSerial;

	@ApiModelProperty(value = "状态（0正常 1停用）")
	@Excel(name = "状态", readConverterExp = "0=正常,1=停用")
	private String status;

	@Override
	public String toString() {
		JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
		builder.append("id", getId());
		builder.append("sceneName", getSceneName());
		builder.append("sceneCode", getSceneCode());
		builder.append("uidLength", getUidLength());
		builder.append("uidSerial", getUidSerial());
		builder.append("remark", getRemark());
		builder.append("createBy", getCreateBy());
		builder.append("createTime", getCreateTime());
		builder.append("status", getStatus());
		builder.append("updateBy", getUpdateBy());
		builder.append("updateTime", getUpdateTime());
		builder.append("delFlag", getDelFlag());
		builder.append("delTime", getDelTime());
		return builder.toString();
	}
}
