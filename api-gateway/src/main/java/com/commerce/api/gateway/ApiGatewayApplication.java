package com.commerce.api.gateway;

import com.commerce.api.gateway.config.GatewayConfigData;
import com.commerce.api.gateway.cache.config.RedisConfigData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author mselvi
 * @Created 28.03.2024
 */

@EnableConfigurationProperties(value = {
        GatewayConfigData.class,
        RedisConfigData.class
})
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
