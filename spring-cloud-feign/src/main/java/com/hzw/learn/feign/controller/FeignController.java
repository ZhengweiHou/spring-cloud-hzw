package com.hzw.learn.feign.controller;

import com.hzw.learn.feign.feignclient.EurekaClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName FeignController
 * @Description TODO
 * @Author houzw
 * @Date 2022/11/7
 **/
@RestController
public class FeignController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    EurekaClientService eurekaClientService;

    @RequestMapping(value = "hello",method = RequestMethod.GET)
    public String helloFeign(@RequestParam(value = "msg") String msg){
        log.info("开始访问eurekaClient应用");
        String returnMsg = eurekaClientService.eurekaClientHello(msg);
        log.info("接收到eurekaClient应用返回：{}",returnMsg);
        return returnMsg;
    }
}
