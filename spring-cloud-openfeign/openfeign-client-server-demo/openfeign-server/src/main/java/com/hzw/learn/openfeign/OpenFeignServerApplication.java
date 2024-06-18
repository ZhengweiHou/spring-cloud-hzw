package com.hzw.learn.openfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName OpenFeignServerApplication
 * @Description TODO
 * @Author houzw
 * @Date 2022/11/7
 **/
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.hzw.learn.openfeign")
public class OpenFeignServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenFeignServerApplication .class, args);
    }
}
