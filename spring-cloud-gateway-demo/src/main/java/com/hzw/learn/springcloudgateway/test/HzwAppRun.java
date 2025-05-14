package com.hzw.learn.springcloudgateway.test;

import com.hzw.learn.springcloudgateway.ext.HzwProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @ClassName HzwAppRun
 * @Description TODO
 * @Author houzw
 * @Date 2024/9/14
 **/
@Component
public class HzwAppRun implements ApplicationRunner {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    HzwProperties hzwProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("========={}",hzwProperties.getPwd());
    }
}
