package com.commerce.notification.service;

import com.commerce.notification.service.common.DomainComponent;
import com.commerce.notification.service.common.messaging.kafka.config.KafkaConfigData;
import com.commerce.notification.service.common.messaging.kafka.config.KafkaConsumerConfigData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@EnableConfigurationProperties(value = {
        KafkaConfigData.class,
        KafkaConsumerConfigData.class,
})
@EnableJpaRepositories(basePackages = "com.commerce.notification.service")
@EntityScan(basePackages = "com.commerce.notification.service")
@SpringBootApplication
@ComponentScan(
        basePackages = {
                "com.commerce.notification.service",
        },
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {DomainComponent.class})
        }
)
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}
