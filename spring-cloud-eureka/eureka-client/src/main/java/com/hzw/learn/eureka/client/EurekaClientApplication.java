package com.hzw.learn.eureka.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName EurekaClientApplication
 * @Description TODO
 * @Author houzw
 * @Date 2022/11/7
 **/
@SpringBootApplication
@EnableEurekaClient
@ComponentScan("com.hzw.learn.eureka.client")
@RestController
public class EurekaClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
    }


}
