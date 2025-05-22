package com.hzw.learn.test;

import org.junit.Test;

import io.netty.handler.codec.http.HttpMethod;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

public class NettyHttpClientTest {
    @Test
    public void nettyHttpclientTest() {
        ConnectionProvider connectionProvider = ConnectionProvider.builder("http2")
            .maxConnections(100)
            .pendingAcquireMaxCount(-1)
            .build();

        // 配置HTTP/2客户端
        HttpClient httpclient = HttpClient.create(connectionProvider)
            // .protocol(reactor.netty.http.HttpProtocol.H2) // 启用HTTP/2
            .protocol(reactor.netty.http.HttpProtocol.H2C) // 启用HTTP/2
            // .protocol(reactor.netty.http.HttpProtocol.HTTP11)
            .wiretap(true); // 启用日志跟踪（可选） 

        // httpclient
        //     .request(HttpMethod.POST)
        //     .uri("")
        //     .send((req, nettyOutbound) -> {
		//     	return nettyOutbound.send(null));
		//     }).responseConnection((res, connection) -> {

        //     }
    }
}
