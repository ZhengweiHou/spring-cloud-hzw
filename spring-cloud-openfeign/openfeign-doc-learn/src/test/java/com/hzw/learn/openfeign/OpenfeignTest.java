package com.hzw.learn.openfeign;

import com.hzw.learn.openfeign.feignclient.StoreClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName OpenfeignTest
 * @Description TODO
 * @Author houzw
 * @Date 2022/12/9
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class OpenfeignTest {

    @Autowired
    StoreClient storeClient;

    @Test
    public void test1(){
        System.out.println("");
//        storeClient.getStores();
    }
}
