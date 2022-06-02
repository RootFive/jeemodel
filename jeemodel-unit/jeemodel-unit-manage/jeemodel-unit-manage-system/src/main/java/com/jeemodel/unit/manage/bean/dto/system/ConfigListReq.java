package com.jeemodel.unit.manage.bean.dto.system;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.jeemodel.bean.annotation.Excel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Rootfive
 *
 */
@ApiModel(description = "参数配置分页请求DTO")
@Data
@EqualsAndHashCode(callSuper=false)
public class ConfigListReq{
	
	@NotBlank(message = "参数名称不能为空")
	@Size(min = 0, max = 100, message = "参数名称不能超过100个字符")
	@Excel(name = "参数名称")
	private String configName;

	@NotBlank(message = "参数键名长度不能为空")
	@Size(min = 0, max = 100, message = "参数键名长度不能超过100个字符")
	@Excel(name = "参数键名")
	private String configKey;

	@Excel(name = "系统内置", readConverterExp = "Y=是,N=否")
	private String configType;
	
	@ApiModelProperty(value = "创建时间-开始")
	private LocalDateTime beginTime;
	
	@ApiModelProperty(value = "创建时间-结束")
	private LocalDateTime endTime;
	
	
	/** 其他参数 */
	private Map<String, Object> params;


	public Map<String, Object> getParams() {
		if (params == null) {
			params = new HashMap<>();
		}
		return params;
	}
	
}
