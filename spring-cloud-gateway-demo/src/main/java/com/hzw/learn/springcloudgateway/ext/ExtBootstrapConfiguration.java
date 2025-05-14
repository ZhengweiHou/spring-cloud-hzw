package com.hzw.learn.springcloudgateway.ext;

import com.alibaba.cloud.nacos.NacosConfigBootstrapConfiguration;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.bootstrap.encrypt.EnvironmentDecryptApplicationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import static org.springframework.cloud.bootstrap.encrypt.EnvironmentDecryptApplicationInitializer.ENCRYPTED_PROPERTY_PREFIX;

/**
 * @ClassName ExtBootstrapConfiguration
 * @Description 由META-INFO/spring.factories加载自{@link org.springframework.cloud.bootstrap.BootstrapConfiguration}，作用域仅限定于spring-cloud的bootstrap加载阶段
 * @Author houzw
 * @Date 2024/9/18
 **/
@Configuration(proxyBeanMethods = false)
public class ExtBootstrapConfiguration {
    Logger logger = LoggerFactory.getLogger(ExtBootstrapConfiguration.class);

    /**
     * 实例化TextEncryptor，用于{@link EnvironmentDecryptApplicationInitializer}对enviroment中的密码字段进行解密处理
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public TextEncryptor cipherEncryptor(){
        // TODO 调整为参数可配置的密码加密方式
        return new Base64Encryptor();
    }

    /**
     * 用于覆盖{@link NacosConfigBootstrapConfiguration}对NacosConfigManager的初始化
     * 覆盖原因：springCloud启动bootstrap期间的nacos配置加载动作处于EnvironmentDecryptApplicationInitializer之前，无法提前对nacos的密码进行提前解密
     *   此处覆盖NacosConfigManager的初始化，手动介入对nacos的密码配置进行解密处理
     * @param nacosConfigProperties
     * @param cipherEncryptor 密码加密机
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public NacosConfigManager nacosConfigManager(
            NacosConfigProperties nacosConfigProperties,
            TextEncryptor cipherEncryptor
    ) {
        String nacosPwd = nacosConfigProperties.getPassword();
        nacosConfigProperties.setPassword(decrypt(nacosPwd, cipherEncryptor));
        return new NacosConfigManager(nacosConfigProperties);
    }

    private String decrypt(String original, TextEncryptor encryptor) {
            if (!original.startsWith(ENCRYPTED_PROPERTY_PREFIX)) {
                return original;
            }
            String value = original.substring(ENCRYPTED_PROPERTY_PREFIX.length());

        try {
            value = encryptor.decrypt(value);
            if (logger.isDebugEnabled()) {
                logger.debug("Decrypted nacos password");
            }
            return value;
        }
        catch (Exception e) {
            String message = "Cannot decrypt nacos password";
            if (logger.isDebugEnabled()) {
                logger.warn(message, e);
            }
            else {
                logger.warn(message);
            }
            throw new IllegalStateException(message, e);
        }
    }




    }
