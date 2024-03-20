package com.commerce.payment.service;

import com.commerce.kafka.config.KafkaConfigData;
import com.commerce.kafka.config.KafkaConsumerConfigData;
import com.commerce.kafka.config.KafkaProducerConfigData;
import com.commerce.payment.service.common.DomainComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

@EnableConfigurationProperties(value = {
        KafkaConfigData.class,
        KafkaConsumerConfigData.class,
        KafkaProducerConfigData.class
})
@EnableJpaRepositories(basePackages = "com.commerce.payment.service")
@EntityScan(basePackages = "com.commerce.payment.service")
@SpringBootApplication
@ComponentScan(
        basePackages = {
                "com.commerce.payment.service",
                "com.commerce.kafka"
        },
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {DomainComponent.class})
        }
)
public class PaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class);
    }
}
