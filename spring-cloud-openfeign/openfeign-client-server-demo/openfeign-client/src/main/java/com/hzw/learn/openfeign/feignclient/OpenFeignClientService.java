package com.hzw.learn.openfeign.feignclient;

import com.hzw.learn.openfeign.feignclient.impl.OpenFeignClientServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @ClassName OpenFeignClientService
 * @Description TODO
 * @Author houzw
 * @Date 2022/11/7
 **/
// feign访问openfeign_server服务，和目标项目配置对应：spring.application.name=openfeign_server
//添加FeignClient注解，绑定服务提供者。
@FeignClient(
        /** {@link org.springframework.cloud.openfeign.FeignClientsRegistrar#getName(Map)} 会解析并赋值此处的${unitid:}  **/
        value = "openfeign-server${unitid:}",
        fallback = OpenFeignClientServiceImpl.class,
        decode404 = true
)
public interface OpenFeignClientService {
//    @RequestMapping(value = "hello",method = RequestMethod.GET)
    @GetMapping(value = "hello") // 等价
    String eurekaClientHello(@RequestParam(value = "msg") String msg);
}
