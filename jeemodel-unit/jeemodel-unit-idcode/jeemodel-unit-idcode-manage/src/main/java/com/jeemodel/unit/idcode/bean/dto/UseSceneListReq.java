package com.jeemodel.unit.idcode.bean.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 场景标识-分页请求
 * 
 * Entity实体类对象 【com.jeemodel.unit.idcode.bean.entity.UseScene】
 * 
 * @author JeeModel
 * @date 2022-07-04
 */
@ApiModel(description = "场景标识-分页请求")
@Data
@EqualsAndHashCode(callSuper = true)
public class UseSceneListReq extends UseSceneDTO {

	@ApiModelProperty(value = "其他参数")
	private Map<String, Object> params;

	public Map<String, Object> getParams() {
		if (params == null) {
			params = new HashMap<>();
		}
		return params;
	}

	@ApiModelProperty(value = "场景标识-名称， like模糊查询：【value like 查询值 】")
	private String sceneName;

	@ApiModelProperty(value = "场景标识-编码（35进制，最大42874）， 等于查询EQ 【value = 查询值】")
	private String sceneCode;

	@ApiModelProperty(value = "标识-长度（3-6）， 等于查询EQ 【value = 查询值】")
	private Integer uidLength;

	@ApiModelProperty(value = "标识序列号， between区间查询-开始值 【查询值 <=  value】")
	private Long beginUidSerial;

	@ApiModelProperty(value = "标识序列号， between区间查询-结束值：【value <= 查询值】")
	private Long endUidSerial;

	@ApiModelProperty(value = "创建者， 等于查询EQ 【value = 查询值】")
	private String createBy;

	@ApiModelProperty(value = "状态（0正常 1停用）， 等于查询EQ 【value = 查询值】")
	private String status;

	@ApiModelProperty(value = "更新者， 等于查询EQ 【value = 查询值】")
	private String updateBy;

	@ApiModelProperty(value = "更新时间， between区间查询-开始值 【查询值 <=  value】")
	private Date beginUpdateTime;

	@ApiModelProperty(value = "更新时间， between区间查询-结束值：【value <= 查询值】")
	private Date endUpdateTime;

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
