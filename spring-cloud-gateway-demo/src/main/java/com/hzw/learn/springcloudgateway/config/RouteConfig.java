package com.hzw.learn.springcloudgateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName RouteConfig
 * @Description TODO
 * @Author houzw
 * @Date 2024/7/1
 **/
// @Configuration
public class RouteConfig {
    @Bean
    public RouteLocator h1Routes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(p -> p
                        .path("/mvntest")
                        .or()
                        .header("name","hzw")
                        .filters(f -> f.addRequestHeader("Hello","World"))
                        .uri("http://localhost:8888")
                )
//                .route("nacos_des_test1",p -> p
//                        .path("/openfeign-client/**")
//                        .uri("lb://openfeign-client")
//                )
                .build();
    }

}