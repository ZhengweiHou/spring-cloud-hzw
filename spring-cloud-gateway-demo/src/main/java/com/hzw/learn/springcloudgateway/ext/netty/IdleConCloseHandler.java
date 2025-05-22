package com.hzw.learn.springcloudgateway.ext.netty;


import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class IdleConCloseHandler extends ChannelInboundHandlerAdapter {
   static Logger log = LoggerFactory.getLogger(IdleConCloseHandler.class); 

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("userEventTriggered");
        if(evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;

            if(event.state() == IdleState.ALL_IDLE){
                InetSocketAddress remote = (InetSocketAddress) ctx.channel().remoteAddress();
                log.info("长时间未读写，关闭连接 t:[{}:{}]", remote.getAddress().getHostAddress(), remote.getPort());
                ctx.close();
            }
        }else {
            super.userEventTriggered(ctx, evt);
        } 
    }
}
