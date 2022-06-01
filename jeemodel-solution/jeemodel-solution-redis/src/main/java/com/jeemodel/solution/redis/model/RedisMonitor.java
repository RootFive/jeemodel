package com.jeemodel.solution.redis.model;

import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "缓存监控-详情")
@Data
@NoArgsConstructor
public class RedisMonitor {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "redis-info")
	private Properties info;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "当前使用的redis库Key总量", example = "666")
	private Object dbSize;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "redis-commandStats")
	private List<RedisCmdStats> commandStats;
}
