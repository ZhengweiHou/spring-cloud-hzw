package com.hzw.learn.springcloudgateway.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName ApplicationListenerTest
 * @Description TODO
 * @Author houzw
 * @Date 2024/9/24
 **/
@Component
public class ApplicationListenerTest implements ApplicationListener<ApplicationEvent> {
    Logger logger = LoggerFactory.getLogger("=event=");

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        logger.info(event.getClass().getSimpleName());
    }
}
