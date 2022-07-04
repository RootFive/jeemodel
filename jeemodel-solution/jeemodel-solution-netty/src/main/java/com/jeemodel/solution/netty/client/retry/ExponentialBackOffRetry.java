package com.jeemodel.solution.netty.client.retry;

import java.util.Random;

import lombok.extern.slf4j.Slf4j;

/**
 * 指数补偿， 以在任务失败时通过指数补偿算法进行重试
 * 指数补偿（Exponential backoff）
 * 参考文章：https://zhuanlan.zhihu.com/p/37729147
 * 
 */
@Slf4j
public class ExponentialBackOffRetry implements RetryPolicy {

	private static final Random random = new Random();
	
	/** 断线重连，初始等待时间,秒 */
	private final long baseSleepSecond;
	
	/** 当前最大重试次数 */
	private final int maxRetries;
	/** 最长重试等待休眠时间  */
	private final int maxSleepSecond;


	/**
	 * @param baseSleepSecond  初始等待时间
	 * @param maxRetries	最大重试次数
	 * @param maxSleepSecond	最大等待时间
	 */
	public ExponentialBackOffRetry(int baseSleepSecond, int maxRetries, int maxSleepSecond) {
		this.baseSleepSecond = baseSleepSecond;
		this.maxRetries = maxRetries;
		this.maxSleepSecond = maxSleepSecond;
	}

	/**
	 * 是否允许重连
	 * @return true/false
	 */
	@Override
	public boolean allowRetry(int retryCount) {
		if (retryCount < maxRetries) {
			return true;
		}
		return false;
	}

	/**
	 * 获取重连需要等待的时间
	 * @param retryCount
	 * @return
	 */
	@Override
	public long getSleepTimeMs(int retryCount) {
		if (retryCount < 0) {
			throw new IllegalArgumentException("重试次数必须大于0");
		}
		if (retryCount >= maxRetries) {
			log.debug("重试次数已达上限,重试统计：{}次,最大重试次数：{}", retryCount, maxRetries);
			retryCount = maxRetries;
		}
		
		long sleepMs = baseSleepSecond * Math.max(1, random.nextInt(1 << retryCount));
		if (sleepMs > maxSleepSecond) {
			log.debug("使用默认最大等待时间（{}秒）,否则休眠时间太长（{}秒）", maxSleepSecond, sleepMs);
			sleepMs = maxSleepSecond;
		}
		return sleepMs;
	}
}