package com.hzw.learn.openfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName FeignApplication
 * @Description TODO
 * @Author houzw
 * @Date 2022/11/7
 **/
//@EnableDiscoveryClient //？？
//@EnableEurekaClient //启用服务注册客户端
@SpringBootApplication //springboot启动注解
@EnableFeignClients //启用OpenFeign
@ComponentScan("com.hzw.learn.openfeign")
public class OpenFeignApplication {
    public static void main(String[] args) {

        SpringApplication.run(OpenFeignApplication .class, args);

    }
}
