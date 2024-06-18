package com.hzw.learn.openfeign.feignclient.impl;

import com.hzw.learn.openfeign.feignclient.OpenFeignClientService;

/**
 * @ClassName OpenFeignClientServiceImpl
 * @Description TODO
 * @Author houzw
 * @Date 2022/11/16
 **/
public class OpenFeignClientServiceImpl implements OpenFeignClientService {
    public String eurekaClientHello(String msg) {
        return "你要访问的服务错误了";
    }
}
