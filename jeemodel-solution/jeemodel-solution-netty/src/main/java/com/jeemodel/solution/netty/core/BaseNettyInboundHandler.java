package com.jeemodel.solution.netty.core;

import com.jeemodel.solution.netty.utils.NettyUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Rootfive
 *	Netty学习(六)----Netty基础组件入场介绍
 *	https://blog.csdn.net/qq_39458593/article/details/105641905
 *默认情况下 adapter 会通过 ctx.fireChannelInactive() 方法直接把上一个 handler 的输出结果传递到下一个 handler。
 *
 *	handlerAdded() ：指的是当检测到新连接之后，收到了netty future后的回调，表示在当前的 channel 中，已经成功添加了一个 handler处理器。
 *	channelRegistered()：这个回调方法，表示当前的 channel 的所有的逻辑处理已经和某个 NIO 线程建立了绑定关系， 
 *		在BIO 编程中，accept 到新的连接，然后创建一个线程来处理这条连接的读写，
 *		只不过 Netty 里面是使用了线程池的方式，只需要从线程池里面去抓一个线程绑定在这个 channel 上即可，这里的 NIO 线程通常指的是 NioEventLoop
 *		channelActive()：当 channel 的所有的业务逻辑链准备完毕（也就是说 channel 的 pipeline 中已经添加完所有的 handler）以及绑定好一个 NIO 线程之后，这条连接算是真正激活了，接下来就会回调到此方法。
 *		channelRead()：客户端向服务端发来数据，每次都会回调此方法，表示有数据可读。
 *		channelReadComplete()：服务端每次读完一次完整的数据之后，回调该方法，表示数据读取完毕。
 *		channelInactive(): 表面这条连接已经被关闭了，这条连接在 TCP 层面已经不再是 ESTABLISH 状态了
 *		channelUnregistered(): 既然连接已经被关闭，那么与这条连接绑定的线程就不需要对这条连接负责了，这个回调就表明与这条连接对应的 NIO 线程移除掉对这条连接的处理
 *		handlerRemoved()：最后，我们给这条连接上添加的所有的业务逻辑处理器都给移除掉。
 */
@Slf4j
public abstract class BaseNettyInboundHandler<T> extends SimpleChannelInboundHandler<T> {

	/**
	 * 链接服务器完成后，会触发此方法
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.debug("创建TCP连接：[{}]", ctx.channel().remoteAddress());
		// do something
		ctx.fireChannelActive();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		if (ctx.channel().remoteAddress() !=null) {
			log.debug("断开TCP连接：[{}]", ctx.channel().remoteAddress());
		}
		// do something
		ctx.fireChannelInactive();
	}

	/**
	 * exceptionCaught 事件处理方法由一个ExceptionEvent异常事件调用，这个异常事件起因于Netty的I/O异常或一个处理器实现的内部异常。
	 * 多数情况下，捕捉 到的异常应当被记录下来，并在这个方法中关闭这个channel通道。
	 * 当然处理这种异常情况的方法实现可能因你的实际需求而有所不同，例如，在关闭这个连 接之前你可能会发送一个包含了错误码的响应消息。
	 */

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warn("TCP连接：[{}],出现异常，通道即将关闭", ctx.channel().remoteAddress(), cause);
		// do something
		ctx.close();
		NettyUtils.closeChannel(ctx.channel());
	}
}
