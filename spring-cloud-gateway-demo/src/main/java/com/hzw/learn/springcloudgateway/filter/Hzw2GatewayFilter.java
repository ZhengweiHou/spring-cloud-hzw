package com.hzw.learn.springcloudgateway.filter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.TimeoutException;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.springframework.cloud.gateway.support.RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

/**
 * @ClassName Hzw2GatewayFilter
 * @Description TODO
 * @Author houzw
 * @Date 2024/7/15
 **/
public class Hzw2GatewayFilter implements GatewayFilter, Ordered {
    Logger log = LoggerFactory.getLogger(Hzw2GatewayFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);

        Flux<String> responseFlux = exchange.getRequest().getBody().flatMap(body -> {
            int canread = body.readableByteCount();
            log.info("=========body_length:{}", canread);
            return Flux.just(this.getByteBuf(body));
        }).flatMap(bb -> {
            return Flux.just("helloword");
        });

        Duration responseTimeout = this.getResponseTimeout(route);
        if (responseTimeout != null) {
            responseFlux = responseFlux
                    .timeout(
                            responseTimeout,
                            Mono.error(new TimeoutException("Hzw2GatewayFilter Response took longer than timeout: " + responseTimeout))
                    )
                    .onErrorMap(
                            TimeoutException.class,
                            th -> new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT, th.getMessage(), th)
                    );
        }

        ServerHttpResponse resp = exchange.getResponse();

        return resp.writeWith(
                responseFlux.flatMap(result ->{
                    return Flux.just(wrap(result.getBytes(),resp));
                }));
//        return resp.writeWith(Flux.just(wrap("11111111".getBytes(),resp)));

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


    private Duration getResponseTimeout(Route route) {
        Object responseTimeoutAttr = route.getMetadata().get(RESPONSE_TIMEOUT_ATTR);
        Long responseTimeout = null;
        if (responseTimeoutAttr != null) {
            if (responseTimeoutAttr instanceof Number) {
                responseTimeout = ((Number) responseTimeoutAttr).longValue();
            }
            else {
                responseTimeout = Long.valueOf(responseTimeoutAttr.toString());
            }
        }
        return responseTimeout != null ? Duration.ofMillis(responseTimeout)
//                : properties.getResponseTimeout();
                :   Duration.of(30, ChronoUnit.SECONDS); // 默认超时时间
    }

}
