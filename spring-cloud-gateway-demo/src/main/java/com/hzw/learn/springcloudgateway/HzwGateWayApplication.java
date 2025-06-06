package com.hzw.learn.springcloudgateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

/**
 * @ClassName HzwGateWayApplication
 * @Description TODO
 * @Author houzw
 * @Date 2024/6/26
 **/
@SpringBootApplication
//@EnableAutoConfiguration
@EnableDiscoveryClient
@ComponentScan(basePackages = { "com.hzw.learn.springcloudgateway" })
public class HzwGateWayApplication {
    @Autowired
    Environment env;

    public static void main(String[] args) {
        SpringApplication.run(HzwGateWayApplication.class, args);
        
    }
}
