package com.hzw.learn.springcloudgatewayv3.filter;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.RouteToRequestUrlFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class HzwGlobleFilter implements GlobalFilter, Ordered{
    Logger log = LoggerFactory.getLogger(HzwGlobleFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        HttpHeaders headers = exchange.getRequest().getHeaders();
        // 遍历打印headers
        for (String key : headers.keySet()) {
            String value = headers.getFirst(key);
            log.info("===>key:{},value:{}",key,value);
        }

        URI uri = exchange.getRequiredAttribute(GATEWAY_REQUEST_URL_ATTR);
        String uristr = uri.toString();
        String path = uri.getPath();
        String host = uri.getHost();
        log.info("===>host:{},path:{},uristr:{}",host,path,uristr);

        return chain.filter(exchange);
    }

	@Override
	public int getOrder() {
		// return Ordered.HIGHEST_PRECEDENCE + 900;
        return RouteToRequestUrlFilter.ROUTE_TO_URL_FILTER_ORDER + 1;
	}
    
}
