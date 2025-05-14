package com.hzw.learn.springcloudgateway.filter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @ClassName HzwGatewayFilter
 * @Description TODO
 * @Author houzw
 * @Date 2024/7/15
 **/
public class HzwGatewayFilter implements GatewayFilter, Ordered {
    Logger log = LoggerFactory.getLogger(HzwGatewayFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        /** 1. 获取请求体body ByteBuf */
        Mono<ByteBuf> bufMono = DataBufferUtils.join(request.getBody())
                .flatMap(dataBuffer -> {
                    try {
                        DataBufferUtils.retain(dataBuffer);
                        return Mono.just(this.getByteBuf(dataBuffer));
                    } finally {
                        DataBufferUtils.release(dataBuffer);
                    }
                });

        /** 2. 获取并解析处理req body信息*/
        Mono<String> monoResp = bufMono.flatMap(buf -> {
            // 读取body
            byte[] bodys;
            try {
                int canread = buf.readableBytes();
                bodys = new byte[canread];
                buf.readBytes(bodys);
            } finally {
                if (buf.refCnt() > 0) {
                    buf.release(); // 释放 ByteBuf 的引用，否则netty中这段内存无法被gc
                }
            }

            // 模拟请求处理
            String bodyStr = new String(bodys);

            // 使用sink异步等待响应，以避免epoll线程堵塞
            Mono<String> sink = Mono.create(monoSink -> {
                new Thread(() ->{
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    monoSink.success("gw recived client msg:" + bodyStr);
                }).start();

            });
            return sink;
        });

        // 3. 返回响应信息
        return monoResp.flatMap(respStr -> {
            log.info("respStr:{}", respStr);
            return response.writeWith(
                    Mono.just(wrap(respStr.getBytes(), response))
            );
        });

    }

    @Override
    public int getOrder() {
//        return 0;
        return Integer.MAX_VALUE - 10;
    }


    protected DataBuffer wrap(byte[] byteBuf, ServerHttpResponse response) {
        DataBufferFactory bufferFactory = response.bufferFactory();
        if (bufferFactory instanceof NettyDataBufferFactory) {
            NettyDataBufferFactory factory = (NettyDataBufferFactory)bufferFactory;
            return factory.wrap(byteBuf);
        } else if (bufferFactory instanceof DefaultDataBufferFactory) {
            DataBuffer buffer = ((DefaultDataBufferFactory)bufferFactory).allocateBuffer();
            buffer.write(byteBuf);
            return buffer;
        } else {
            throw new IllegalArgumentException("Unkown DataBufferFactory type " + bufferFactory.getClass());
        }
    }

    protected ByteBuf getByteBuf(DataBuffer dataBuffer) {
        if (dataBuffer instanceof NettyDataBuffer) {
            NettyDataBuffer buffer = (NettyDataBuffer)dataBuffer;
            return buffer.getNativeBuffer();
        } else if (dataBuffer instanceof DefaultDataBuffer) {
            DefaultDataBuffer buffer = (DefaultDataBuffer)dataBuffer;
            return Unpooled.wrappedBuffer(buffer.getNativeBuffer());
        } else {
            throw new IllegalArgumentException("Unable to handle DataBuffer of type " + dataBuffer.getClass());
        }
    }



}
