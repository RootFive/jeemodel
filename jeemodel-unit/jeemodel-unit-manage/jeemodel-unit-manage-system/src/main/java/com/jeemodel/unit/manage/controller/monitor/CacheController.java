package com.jeemodel.unit.manage.controller.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeemodel.bean.http.PongData;
import com.jeemodel.bean.http.PongUtils;
import com.jeemodel.solution.redis.helper.RedisMonitorHelper;
import com.jeemodel.solution.redis.model.RedisMonitor;

/**
 * 缓存监控
 * 
 * @author Rootfive
 */
@RestController
@RequestMapping("/monitor/cache")
public class CacheController {
	
	@Autowired
	private RedisMonitorHelper redisMonitorHelper;

	@PreAuthorize("@ss.hasPermi('monitor:cache:list')")
	@GetMapping()
	public PongData<RedisMonitor> getInfo() {
		RedisMonitor redisMonitor = redisMonitorHelper.getRedisMonitor();
		return PongUtils.okData(redisMonitor);
	}
}
