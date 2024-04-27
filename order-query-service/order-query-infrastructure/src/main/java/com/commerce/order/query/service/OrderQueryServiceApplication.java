package com.commerce.order.query.service;

import com.commerce.order.query.service.common.DomainComponent;
import com.commerce.order.query.service.common.messaging.kafka.config.KafkaConfigData;
import com.commerce.order.query.service.common.messaging.kafka.config.KafkaConsumerConfigData;
import com.commerce.order.query.service.common.search.elastic.config.ElasticsearchConfigData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */


@EnableConfigurationProperties(value = {
        KafkaConfigData.class,
        KafkaConsumerConfigData.class,
        ElasticsearchConfigData.class
})
@EnableElasticsearchRepositories(basePackages = "com.commerce.order.query.service")
@EntityScan(basePackages = "com.commerce.order.query.service")
@SpringBootApplication
@ComponentScan(
        basePackages = {
                "com.commerce.order.query.service",
        },
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {DomainComponent.class})
        }
)
public class OrderQueryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderQueryServiceApplication.class, args);
    }
}
