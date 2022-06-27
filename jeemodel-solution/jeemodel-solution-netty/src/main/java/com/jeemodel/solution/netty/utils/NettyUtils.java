package com.jeemodel.solution.netty.utils;

import java.net.SocketAddress;

import com.jeemodel.core.utils.BlankUtils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Rootfive
 */
@Slf4j
public class NettyUtils {
	/**
	 * 获取Channel的远程IP地址
	 * @param channel
	 * @return
	 */
	public static String parseRemoteAddr(final Channel channel) {
		if (null == channel) {
			return "";
		}
		SocketAddress remote = channel.remoteAddress();
		final String addr = BlankUtils.isNotNull(remote) ? remote.toString() : "";
		if (addr.length() > 0) {
			int index = addr.lastIndexOf("/");
			if (index >= 0) {
				return addr.substring(index + 1);
			}
			return addr;
		}
		return "";
	}

	/**
	 * 关闭远程Channel链接
	 * @param channel
	 */
	public static void closeChannel(Channel channel) {
//		final String addrRemote = parseRemoteAddr(channel);
		channel.close().addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
			    /*
			     *  参考文章：https://www.cnblogs.com/549294286/p/10579616.html
			     *  
			     * 1 取消异步操作    
			     * 	boolean cancel(boolean mayInterruptIfRunning);
			     * 
			     * 2 异步操作是否取消	 
			     * boolean isCancelled();
			     * 
			     * 3 异步操作是否完成，正常终止、异常、取消都是完成
			     * boolean isDone();
			     * 
			     * 4 阻塞直到取得异步操作结果
			     * V get() throws InterruptedException, ExecutionException;
			     *  
			     * 5 同上，但最长阻塞时间为timeout			
			     * V get(long timeout, TimeUnit unit)				
			     *   
			     * 6 异步操作完成且正常终止
			     * boolean isSuccess();
			     * 
			     * 7 异步操作是否可以取消
			     * boolean isCancellable();
			     * 
			     * 8 异步操作失败的原因
			     * Throwable cause();
			     * 
			     *  9 添加一个监听者，异步操作完成时回调，类比javascript的回调函数
			     *  Future<V> addListener(GenericFutureListener<? extends Future<? super V>> listener);
			     *  Future<V> removeListener(GenericFutureListener<? extends Future<? super V>> listener);
			     *  
			     *  10 阻塞直到异步操作完成
			     *  Future<V> await() throws InterruptedException;
			     *  
			     *  11 同上，但异步操作失败时抛出异常
			     *  Future<V> sync() throws InterruptedException;
			     *  
			     *  12 非阻塞地返回异步结果，如果尚未完成返回null
			     *  V getNow();
			     */
				log.info("关闭远程通道链接[{}] 操作完成且正常终止: {}", channel.remoteAddress(), future.isSuccess());
			}
		});
	}
}
