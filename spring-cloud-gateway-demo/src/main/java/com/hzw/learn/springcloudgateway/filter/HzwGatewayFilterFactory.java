package com.hzw.learn.springcloudgateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

/**
 * @ClassName HzwGatewayFilterFactory
 * @Description TODO
 * @Author houzw
 * @Date 2024/7/8
 **/
public class HzwGatewayFilterFactory extends AbstractGatewayFilterFactory<HzwGatewayFilterFactory.Config> {

    Logger log = LoggerFactory.getLogger(HzwGatewayFilterFactory.class);
    public HzwGatewayFilterFactory(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        log.info("{}.{}",this.getClass().getSimpleName(),"apply");
        return new HzwGatewayFilter();
    }

    public static class Config{
    }
}
