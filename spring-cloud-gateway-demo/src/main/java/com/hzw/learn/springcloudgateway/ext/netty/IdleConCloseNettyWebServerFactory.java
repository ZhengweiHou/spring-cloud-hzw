package com.hzw.learn.springcloudgateway.ext.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class IdleConCloseNettyWebServerFactory implements WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {
    Logger log = LoggerFactory.getLogger(getClass()); 

    @Override
    public void customize(NettyReactiveWebServerFactory factory) {
        /*
        // 添加自定义的服务器配置
        factory.addServerCustomizers(httpserver ->
            httpserver.tcpConfiguration(tcpserver->
                // 配置TCP服务器
                tcpserver.bootstrap(bootstrap ->
                    // 设置子处理器
                    bootstrap.childHandler(
                        new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline pip = ch.pipeline();
                                // 添加空闲状态处理器，10秒内无读写操作则触发
                                pip.addLast(new IdleStateHandler(0, 0, 10, TimeUnit.SECONDS));
                                // 添加自定义的空闲连接关闭处理器
                                pip.addLast(new IdleConCloseHandler());
                            }
                        }
                    )
                )
            )
        );
        */
        factory.addServerCustomizers(httpserver ->
            httpserver.tcpConfiguration(tcpserver->
                // 配置TCP服务器
                tcpserver.doOnConnection(connection ->
                    // 设置子处理器
                    connection.addHandler(
                        new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                InetSocketAddress remote = (InetSocketAddress) ch.remoteAddress();
                                log.info("con from t:[{}:{}]", remote.getAddress().getHostAddress(), remote.getPort());

                                ChannelPipeline pip = ch.pipeline();
                                // 添加空闲状态处理器，无读写操作则触发
                                pip.addLast(new IdleStateHandler(0, 0, 5, TimeUnit.SECONDS));
                                // 添加自定义的空闲连接关闭处理器
                                pip.addLast(new IdleConCloseHandler());
                            }
                        }
                    )
                )
            )
        );

    }

}
