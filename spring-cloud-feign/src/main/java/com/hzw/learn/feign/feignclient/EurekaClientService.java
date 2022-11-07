package com.hzw.learn.feign.feignclient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName FeignClientService
 * @Description TODO
 * @Author houzw
 * @Date 2022/11/7
 **/
// feign访问eureka-client服务，和目标项目配置对应：spring.application.name=eureka-client
@FeignClient(value = "eureka-client")
public interface EurekaClientService {
    @RequestMapping(value = "hello",method = RequestMethod.GET)
    String eurekaClientHello(@RequestParam(value = "msg") String msg);
}
