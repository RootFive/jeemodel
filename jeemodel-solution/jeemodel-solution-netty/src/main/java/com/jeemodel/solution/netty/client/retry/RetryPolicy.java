package com.jeemodel.solution.netty.client.retry;

/**
 * 重试策略
 * @author Rootfive
 */
public interface RetryPolicy {

    /**
     * 是否允许重连
     * @return true/false
     */
    boolean allowRetry(int retryCount);

    /**
     * 获取重连需要等待的时间
     * @param retryCount
     * @return
     */
    long getSleepTimeMs(int retryCount);
}