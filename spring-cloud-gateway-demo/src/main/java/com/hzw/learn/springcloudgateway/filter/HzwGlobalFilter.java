package com.hzw.learn.springcloudgateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @ClassName HzwGlobalFilter
 * @Description 编写自定义的 全局过滤器
 * @Author houzw
 * @Date 2024/7/1
 **/
@Component
public class HzwGlobalFilter implements GlobalFilter {
    Logger log = LoggerFactory.getLogger(HzwGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest req = exchange.getRequest();
        String method = req.getMethod().name();
        log.info("Method:{}", method);


//        req.getHeaders().forEach((k,v) -> {
//            log.info("k:{}, v:{}",k,v);
//        });

//        exchange.getRequest().getBody().subscribe(body -> {
//            log.info("============");
//            String bodystr = new String(body.asByteBuffer().array());
//            log.info("body:{}",bodystr);
//        });

//        return Mono.empty();
        return chain.filter(exchange);
    }
}
