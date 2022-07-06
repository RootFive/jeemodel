package com.jeemodel.solution.netty.client.invoke;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import lombok.Data;

@Data
public class ResponseFuture<T> {

	/** 回声 */
	private final String echo;

	/** 超时毫秒树 */
	private final long timeoutMillis;

	private final Semaphore semaphore;

	private final InvokeCallback<T> invokeCallback;

	// AtomicBoolean，在这个Boolean值的变化的时候不允许在之间插入，保持操作的原子性。
	private final AtomicBoolean executeCallbackOnlyOnce = new AtomicBoolean(false);
	private final AtomicBoolean semaphoreReleaseOnlyOnce = new AtomicBoolean(false);

	private final CountDownLatch countDownLatch = new CountDownLatch(1);

	private volatile Throwable cause;

	private volatile T proto;

	private volatile boolean isSendStateOk;

	public ResponseFuture(String echo, long timeoutMillis, InvokeCallback<T> invokeCallback, Semaphore semaphore) {
		this.echo = echo;
		this.timeoutMillis = timeoutMillis;
		this.invokeCallback = invokeCallback;
		this.semaphore = semaphore;
		this.isSendStateOk = false;
	}

	/**
	 * 流量控制时，释放获取的信号量
	 */
	public void release() {
		if (this.semaphore != null) {
			/*
			 * AtomicBoolean，在这个Boolean值的变化的时候不允许在之间插入，保持操作的原子性。 方法和举例：compareAndSet(boolean
			 * expect, boolean update)。 这个方法主要两个作用 比较AtomicBoolean和expect的值，如果一致，执行方法内的语句。
			 * 其实就是一个if语句把AtomicBoolean的值设成update
			 * 比较最要的是这两件事是一气呵成的，这连个动作之间不会被打断，任何内部或者外部的语句都不可能在两个动作之间运行。为多线程的控制提供了解决的方案。
			 */
			if (semaphoreReleaseOnlyOnce.compareAndSet(false, true)) {
				this.semaphore.release();
			}
		}
	}

	public void executeInvokeCallback() {
		if (invokeCallback != null) {
			if (executeCallbackOnlyOnce.compareAndSet(false, true)) {
				invokeCallback.operationComplete(this);
			}
		}
	}

	/**
	 * 等待响应
	 * @param timeoutMillis
	 * @return
	 * @throws InterruptedException
	 */
	public T waitResponse(final long timeoutMillis) throws InterruptedException {
		this.countDownLatch.await(timeoutMillis, TimeUnit.MILLISECONDS);
		return this.proto;
	}

	/**
	 * 同步请求时，释放阻塞的CountDown
	 *
	 * @param sdkProtoDTO
	 */
	public void putResponse(T proto) {
		this.proto = proto;
		this.countDownLatch.countDown();
	}

	public String getRqid() {
		return echo;
	}

	public long getTimeoutMillis() {
		return timeoutMillis;
	}

	public Semaphore getSemaphore() {
		return semaphore;
	}

	public InvokeCallback<T> getInvokeCallback() {
		return invokeCallback;
	}

	public AtomicBoolean getExecuteCallbackOnlyOnce() {
		return executeCallbackOnlyOnce;
	}

	public AtomicBoolean getSemaphoreReleaseOnlyOnce() {
		return semaphoreReleaseOnlyOnce;
	}

	public CountDownLatch getCountDownLatch() {
		return countDownLatch;
	}





	@Override
	public String toString() {
		JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
		if (echo != null)
			builder.append("echo", echo);
		builder.append("timeoutMillis", timeoutMillis);
		if (semaphore != null)
			builder.append("semaphore", semaphore);
		if (invokeCallback != null)
			builder.append("invokeCallback", invokeCallback);
		if (executeCallbackOnlyOnce != null)
			builder.append("executeCallbackOnlyOnce", executeCallbackOnlyOnce);
		if (semaphoreReleaseOnlyOnce != null)
			builder.append("semaphoreReleaseOnlyOnce", semaphoreReleaseOnlyOnce);
		if (countDownLatch != null)
			builder.append("countDownLatch", countDownLatch);
		if (cause != null)
			builder.append("cause", cause);
		if (proto != null)
			builder.append("proto", proto);
		builder.append("isSendStateOk", isSendStateOk);
		return builder.toString();
	}
}
