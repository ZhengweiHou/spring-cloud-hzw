package com.hzw.learn.springcloudgateway.ext.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration(proxyBeanMethods = false)
public class HzwNettyextConfiguration {
    Logger log = LoggerFactory.getLogger(HzwNettyextConfiguration.class);
    @Bean
    public IdleConCloseNettyWebServerFactory idleConCloseNettyWebServerFactory(){
        log.info("idleConCloseNettyWebServerFactory");
        return new IdleConCloseNettyWebServerFactory();
    }
}
