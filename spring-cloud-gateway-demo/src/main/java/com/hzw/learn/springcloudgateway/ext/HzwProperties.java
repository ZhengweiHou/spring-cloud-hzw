package com.hzw.learn.springcloudgateway.ext;

import com.google.gson.Gson;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @ClassName HzwProperties
 * @Description TODO
 * @Author houzw
 * @Date 2024/9/14
 **/
@Component
@ConfigurationProperties("h.z.w")
public class HzwProperties implements EnvironmentAware {

    private Environment environment;
    private String pwd;

    private List<String> ccc;

    private Map<String,String> ddd;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
        System.out.println("========hzw.pwd:" + pwd);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public List<String> getCcc() {
        return ccc;
    }

    public void setCcc(List<String> ccc) {
        System.out.println(new Gson().toJson(ccc));
        this.ccc = ccc;
    }

    public void setDdd(Map<String, String> ddd) {
        System.out.println(new Gson().toJson(ddd));
        this.ddd = ddd;
    }
}
