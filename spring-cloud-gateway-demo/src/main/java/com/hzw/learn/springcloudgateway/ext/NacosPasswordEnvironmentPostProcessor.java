package com.hzw.learn.springcloudgateway.ext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @ClassName NacosPasswordEnvironmentPostProcessor
 * @Description TODO
 * @Author houzw
 * @Date 2024/9/13
 **/
@Deprecated
public class NacosPasswordEnvironmentPostProcessor implements EnvironmentPostProcessor {

    Logger logger = LoggerFactory.getLogger(getClass());

    private static final String NACOS_PASSWORD_ENV_PROPERTY_SOURCES_NAME = "nacosPwdEnvPropertySources_hzw";

    private static final String[] defaultPwdKeys = {
            "nacos.config.password",
            "spring.cloud.nacos.config.*password",
            "spring.cloud.nacos.descovery.*password",
            "h.z.w.pwd" // TODO test
    };

    private Set<Pattern> keysPattern;
    private Base64.Decoder decode;

    public NacosPasswordEnvironmentPostProcessor(){
        super();
        /* 初始化key正则列表 */
        keysPattern = Arrays.stream(defaultPwdKeys)
                .map(k -> Pattern.compile("^" + k + "$"))
                .collect(Collectors.toSet());
        decode = Base64.getDecoder();
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        HashMap<String, Object> overrideProperties = new HashMap<String, Object>();

        MutablePropertySources originPropertySources = environment.getPropertySources();

        // 需提前移除掉覆盖的配置，否则下文从enviroment.getProperty(key)会有影响
        originPropertySources.remove(NACOS_PASSWORD_ENV_PROPERTY_SOURCES_NAME);


        // 判断application是bootstrap还是正常的
        originPropertySources
            .stream()
            .filter(s -> !s.getName().equals(NACOS_PASSWORD_ENV_PROPERTY_SOURCES_NAME)) // 解码后的propertySource不处理
            .filter(s -> s instanceof MapPropertySource)
            .forEach(s ->{
                Arrays.stream(
                        ((MapPropertySource) s).getPropertyNames()
                ).filter(
                        key -> keysPattern.stream().anyMatch(p -> p.matcher(key).matches())
                ).filter(
                        key -> environment.getProperty(key) != null
                ).peek(
                        key -> {
                            logger.info("Decrypt password for name:[{}], key:[{}]",s.getName(), key);
                            System.out.println("Decrypt password for [" + key + "]");
                        }
                ).collect(Collectors.toMap(
                        key -> key,
                        key -> new String(decode.decode(environment.getProperty(key))) // TODO 可能会多次获取到同一个key的情况
                )).forEach((k,v) -> {
                    overrideProperties.put(k,v);
                });
            });


        if (originPropertySources.size() > 0){
            originPropertySources.addFirst(new MapPropertySource(NACOS_PASSWORD_ENV_PROPERTY_SOURCES_NAME, overrideProperties));
        }
    }
}
