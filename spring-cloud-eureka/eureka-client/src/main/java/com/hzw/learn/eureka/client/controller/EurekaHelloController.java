package com.hzw.learn.eureka.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName EurekaHelloController
 * @Description TODO
 * @Author houzw
 * @Date 2022/11/7
 **/
@RestController
public class EurekaHelloController {

    @Value("${server.port}")
    String serverPort;

    @Value("${spring.application.name}")
    String serverName;

    @RequestMapping("hello")
    public String hello(@RequestParam(value = "msg") String msg){
        return "Hello " + msg + ", I'm " + serverName + ":" + serverPort;
    }

}
