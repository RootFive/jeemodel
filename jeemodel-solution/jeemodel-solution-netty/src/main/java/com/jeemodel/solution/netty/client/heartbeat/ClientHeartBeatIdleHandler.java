package com.jeemodel.solution.netty.client.heartbeat;

import com.jeemodel.solution.netty.constants.NettyConstants;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;


/**
 * 空闲状态发送心跳检测消息
 * <p>
 *  用于捕获{@link IdleState#WRITER_IDLE}事件（未在指定时间内向服务器发送数据），
 *  然后向<code>Server</code>端发送一个心跳包。
 * </p>
 * 1）客户端连接服务端
 * 2）在客户端的的ChannelPipeline中加入一个比较特殊的IdleStateHandler，设置一下客户端的写空闲时间，例如5s
 * 3）当客户端的所有ChannelHandler中4s内没有write事件，则会触发userEventTriggered方法
 * 4）我们在客户端的userEventTriggered中对应的触发事件下发送一个心跳包给服务端，检测服务端是否还存活，防止服务端已经宕机，客户端还不知道
 * 5）同样，服务端要对心跳包做出响应，其实给客户端最好的回复就是“不回复”，这样可以服务端的压力，
 * 	 假如有10w个空闲Idle的连接，那么服务端光发送心跳回复，则也是费事的事情，那么怎么才能告诉客户端它还活着呢，其实很简单，
 * 	 因为5s服务端都会收到来自客户端的心跳信息，那么如果10秒内收不到，服务端可以认为客户端挂了，可以close链路
 * 6）加入服务端因为什么因素导致宕机的话，就会关闭所有的链路链接，所以作为客户端要做的事情就是断线重连
 */
public class ClientHeartBeatIdleHandler extends ChannelInboundHandlerAdapter {
	/**
     * 如果就发送心跳命令
     * 利用写通道处于空闲状态发送心跳检测消息
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.WRITER_IDLE) {
                // 向服务端发送心跳
                ctx.writeAndFlush(NettyConstants.CLIENT_HEART_BEAT);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

}