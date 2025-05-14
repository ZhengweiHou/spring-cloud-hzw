package com.hzw.learn.openfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName OpenFeignClientApplication
 * @Description TODO
 * @Author houzw
 * @Date 2022/11/7
 **/
@SpringBootApplication
@EnableDiscoveryClient(autoRegister = true) // 是否开启服务注册
@EnableFeignClients //启用OpenFeign
@ComponentScan("com.hzw.learn.openfeign")
public class OpenFeignClientApplication {
    public static void main(String[] args) {

        SpringApplication.run(OpenFeignClientApplication .class, args);

    }
}
