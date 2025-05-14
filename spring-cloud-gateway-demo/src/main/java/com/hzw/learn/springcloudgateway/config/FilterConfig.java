package com.hzw.learn.springcloudgateway.config;

import com.hzw.learn.springcloudgateway.filter.Hzw2GatewayFilterFactory;
import com.hzw.learn.springcloudgateway.filter.HzwGatewayFilterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName FilterConfig
 * @Description TODO
 * @Author houzw
 * @Date 2024/7/8
 **/
//@Configuration(proxyBeanMethods = false)
public class FilterConfig {

    Logger log = LoggerFactory.getLogger(FilterConfig.class);
    @Bean
    HzwGatewayFilterFactory hzwGatewayFilterFactory(){

        return new HzwGatewayFilterFactory();
    }

    @Bean
    Hzw2GatewayFilterFactory hzw2GatewayFilterFactory(){

        return new Hzw2GatewayFilterFactory();
    }

//    @Bean
    public ServiceInstanceListSupplier whoamiServiceInstances() {

        Flux<List<ServiceInstance>> aa = Flux.just(Arrays.asList(
                new DefaultServiceInstance("hzw-1", "whoamiservice", "localhost", 8003, false),
                new DefaultServiceInstance("hzw-2", "whoamiservice", "localhost", 8004, false)
        ));
        return new ServiceInstanceListSupplier() {
            @Override
            public String getServiceId() {
                return "whoamiservice";
            }

            @Override
            public Flux<List<ServiceInstance>> get() {
                log.info("======getInstanceList:{}",this.getServiceId());
                return aa;
            }
        };
    }
}

