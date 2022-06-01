package com.jeemodel.solution.redis.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import com.jeemodel.base.annotation.HelpService;
import com.jeemodel.solution.redis.model.RedisCmdStats;
import com.jeemodel.solution.redis.model.RedisMonitor;

/**     
 * Redis监控助手
 * @author: Rootfive	2021年8月13日
 */
@HelpService
public class RedisMonitorHelper {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	

	public RedisMonitor getRedisMonitor() {
		Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info());
		Properties commandStats = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
		Object dbSize = redisTemplate.execute((RedisCallback<Object>) connection -> connection.dbSize());

		
		
		RedisMonitor redisMonitor = new RedisMonitor();
		redisMonitor.setInfo(info);
		redisMonitor.setDbSize(dbSize);
		
		List<RedisCmdStats> pieList = new ArrayList<>();
		commandStats.stringPropertyNames().forEach(key -> {
			String property = commandStats.getProperty(key);
			String name = StringUtils.removeStart(key, "cmdstat_");
			String value = StringUtils.substringBetween(property, "calls=", ",usec");
			
			RedisCmdStats redisCmdStats = new RedisCmdStats(name, value);
			
			pieList.add(redisCmdStats);
		});
		
		redisMonitor.setCommandStats(pieList);
		return redisMonitor;
	}
}
