package com.jeemodel.unit.manage.bean.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeemodel.bean.annotation.Excel;
import com.jeemodel.bean.helper.JeeModelToStringBuilder;
import com.jeemodel.data.entity.BaseEntityCRUD;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 参数配置表 manage_config
 * 
 */
@TableName("manage_config")
@Data
@EqualsAndHashCode(callSuper=false)
public class Config extends BaseEntityCRUD {
	
	private static final long serialVersionUID = 1L;

	/** 参数名称 */
	@NotBlank(message = "参数名称不能为空")
	@Size(min = 0, max = 100, message = "参数名称不能超过100个字符")
	@Excel(name = "参数名称")
	private String configName;

	@NotBlank(message = "参数键名长度不能为空")
	@Size(min = 0, max = 100, message = "参数键名长度不能超过100个字符")
	/** 参数键名 */
	@Excel(name = "参数键名")
	private String configKey;

	/** 参数键值 */
	@NotBlank(message = "参数键值不能为空")
	@Size(min = 0, max = 500, message = "参数键值长度不能超过500个字符")
	@Excel(name = "参数键值")
	private String configValue;

	/** 系统内置（Y是 N否） */
	@Excel(name = "系统内置", readConverterExp = "Y=是,N=否")
	private String configType;

	@Override
	public String toString() {
		JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
		builder.append("configName", configName);
		builder.append("configKey", configKey);
		builder.append("configValue", configValue);
		builder.append("configType", configType);
		builder.append("status", status);
		builder.append("updateTime", updateTime);
		builder.append("updateBy", updateBy);
		builder.append("createBy", createBy);
		builder.append("id", id);
		builder.append("remark", remark);
		builder.append("createTime", createTime);
		return builder.toString();
	}
	
	
	

}
