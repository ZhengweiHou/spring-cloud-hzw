package com.hzw.learn.openfeign.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName OpenFeignServerController
 * @Description TODO
 * @Author houzw
 * @Date 2022/11/7
 **/
@RestController
public class OpenFeignServerController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Value("${server.port}")
    String serverPort;

    @Value("${spring.application.name}")
    String serverName;

    @RequestMapping("hello")
    public String hello(@RequestParam(value = "msg") String msg){
        return "Hello " + msg + ", I'm " + serverName + ":" + serverPort;
    }
}
