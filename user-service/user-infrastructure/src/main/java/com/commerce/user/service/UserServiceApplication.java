package com.commerce.user.service;

import com.commerce.user.service.common.DomainComponent;
import com.commerce.user.service.common.cache.config.RedisConfigData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */


@EnableConfigurationProperties(value = {
        RedisConfigData.class
})
@EnableJpaRepositories(basePackages = "com.commerce.user.service")
@EntityScan(basePackages = "com.commerce.user.service")
@SpringBootApplication
@ComponentScan(
        basePackages = {
                "com.commerce.user.service"
        },
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {DomainComponent.class})
        }
)
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class);
    }
}
