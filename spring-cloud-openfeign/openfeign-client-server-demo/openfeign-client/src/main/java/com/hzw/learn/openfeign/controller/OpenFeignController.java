package com.hzw.learn.openfeign.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hzw.learn.openfeign.feignclient.OpenFeignClientService;

/**
 * @ClassName OpenFeignController
 * @Description TODO
 * @Author houzw
 * @Date 2022/11/7
 **/
@RestController
public class OpenFeignController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    OpenFeignClientService openFeignClientService;

//    @RequestMapping(value = "hello",method = RequestMethod.GET)
    @GetMapping(value = "hello") // 同义
    public String helloFeign(@RequestParam(value = "msg") String msg){
        log.info("开始访问openFeign应用");
        String returnMsg = openFeignClientService.eurekaClientHello(msg);
        log.info("接收到openFeign应用返回：{}",returnMsg);
        return returnMsg;
    }
}
