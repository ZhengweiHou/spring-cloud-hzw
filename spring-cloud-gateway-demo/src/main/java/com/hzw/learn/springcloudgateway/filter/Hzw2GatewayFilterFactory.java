package com.hzw.learn.springcloudgateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

/**
 * @ClassName Hzw2GatewayFilterFactory
 * @Description TODO
 * @Author houzw
 * @Date 2024/7/8
 **/
public class Hzw2GatewayFilterFactory extends AbstractGatewayFilterFactory<Hzw2GatewayFilterFactory.Config> {

    Logger log = LoggerFactory.getLogger(Hzw2GatewayFilterFactory.class);
    public Hzw2GatewayFilterFactory(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        log.info("{}.{}",this.getClass().getSimpleName(),"apply");
        return new Hzw2GatewayFilter();
    }

    public static class Config{
    }
}
