package com.commerce.payment.service.adapters.payment.messaging.publisher;

import com.commerce.payment.service.common.messaging.kafka.model.CustomerCommandKafkaModel;
import com.commerce.payment.service.common.messaging.kafka.producer.KafkaProducerWithoutCallback;
import com.commerce.payment.service.customer.usecase.CustomerInfo;
import com.commerce.payment.service.payment.port.json.JsonPort;
import com.commerce.payment.service.payment.port.messaging.output.CustomerCommandMessagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author mselvi
 * @Created 26.04.2024
 */

@Component
public class CustomerCommandKafkaPublisher implements CustomerCommandMessagePublisher {

    private static final Logger logger = LoggerFactory.getLogger(CustomerCommandKafkaPublisher.class);
    private final KafkaProducerWithoutCallback kafkaProducer;
    private final JsonPort jsonPort;

    @Value("${payment-service.customer-command-topic-name}")
    private String customerCommandTopicName;

    public CustomerCommandKafkaPublisher(KafkaProducerWithoutCallback kafkaProducer, JsonPort jsonPort) {
        this.kafkaProducer = kafkaProducer;
        this.jsonPort = jsonPort;
    }

    @Override
    public void publish(CustomerInfo customerInfo) {
        Long customerId = customerInfo.id();

        try {
            CustomerCommandKafkaModel kafkaModel = new CustomerCommandKafkaModel(customerInfo);
            kafkaProducer.send(customerCommandTopicName, customerId.toString(), jsonPort.convertDataToJson(kafkaModel));

            logger.info("CustomerInfo sent to Kafka for customer id id: {}", customerId);
        } catch (Exception e) {
            logger.error("Error while sending CustomerInfo to kafka with customer id: {}  error: {}", customerId, e.getMessage());
        }
    }

}
