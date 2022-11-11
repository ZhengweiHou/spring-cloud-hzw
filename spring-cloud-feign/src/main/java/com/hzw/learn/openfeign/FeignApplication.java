package com.hzw.learn.openfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName FeignApplication
 * @Description TODO
 * @Author houzw
 * @Date 2022/11/7
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan("com.hzw.learn.feign")
public class FeignApplication {
    public static void main(String[] args) {

        SpringApplication.run(FeignApplication .class, args);

    }
}
