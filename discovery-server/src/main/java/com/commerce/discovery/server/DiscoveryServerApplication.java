package com.commerce.discovery.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author mselvi
 * @Created 19.03.2024
 */

@EnableEurekaServer
@SpringBootApplication(scanBasePackages = "com.commerce.discovery.server")
public class DiscoveryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class);
    }
}
