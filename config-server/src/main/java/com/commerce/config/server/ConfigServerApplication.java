package com.commerce.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @Author mselvi
 * @Created 12.03.2024
 */

@EnableConfigServer
@SpringBootApplication(scanBasePackages = "com.commerce.config.server")
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
