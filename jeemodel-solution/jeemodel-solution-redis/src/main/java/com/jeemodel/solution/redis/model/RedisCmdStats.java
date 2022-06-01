package com.jeemodel.solution.redis.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "Redis缓存命令执行详情")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisCmdStats {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "redis命令,比如：get、set、ttl、ping等", example = "get")
	private String name;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "redis命令的执行次数", example = "666")
	private String value;
}
