package com.commerce.customer.service;

import com.commerce.customer.service.common.DomainComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @Author mselvi
 * @Created 10.03.2024
 */

@EnableJpaRepositories(basePackages = "com.commerce.customer.service")
@EntityScan(basePackages = "com.commerce.customer.service")
@SpringBootApplication
@ComponentScan(
        basePackages = {
                "com.commerce.customer.service"
        },
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {DomainComponent.class})
        }
)
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class);
    }
}
