package com.hzw.learn.springcloudgatewayv3;

import org.springframework.cloud.gateway.config.HttpClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.netty.http.client.HttpClient;

@Configuration
public class HzwGatewayConfig {

    /*  
    自定义修改HttpClient 的HttpClientCustomizer
        用于@link org.springframework.cloud.gateway.config.GatewayAutoConfiguration $NettyConfiguration 自动配置
        构建 @link org.springframework.cloud.gateway.config.HttpClientFactory 对Httpclient进行自定义配置
    */
    @Bean
    public HttpClientCustomizer httpClientCustomizer() {
        return new HttpClientCustomizer() {
            @Override
            public HttpClient customize(HttpClient httpClient) {
                HttpClient hc = httpClient.protocol(reactor.netty.http.HttpProtocol.H2C); // 修改protocol支持为H2C，注意会导致HTTP/1.1失效
                // HttpClient hc = httpClient.protocol(reactor.netty.http.HttpProtocol.H2C, reactor.netty.http.HttpProtocol.HTTP11); 
                return hc;
            }
        };

    }
}
