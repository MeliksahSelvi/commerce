package com.commerce.order.service.adapters.order.messaging.publisher;

import com.commerce.order.service.common.messaging.kafka.model.OrderQueryKafkaModel;
import com.commerce.order.service.common.messaging.kafka.producer.KafkaProducerWithoutCallback;
import com.commerce.order.service.order.port.json.JsonPort;
import com.commerce.order.service.order.port.messaging.output.OrderQueryMessagePublisher;
import com.commerce.order.service.order.usecase.OrderQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

@Component
public class OrderQueryKafkaPublisher implements OrderQueryMessagePublisher {

    private static final Logger logger = LoggerFactory.getLogger(OrderQueryKafkaPublisher.class);
    private final KafkaProducerWithoutCallback kafkaProducer;
    private final JsonPort jsonPort;

    @Value("${order-service.order-query-topic-name}")
    private String orderQueryTopicName;

    public OrderQueryKafkaPublisher(KafkaProducerWithoutCallback kafkaProducer, JsonPort jsonPort) {
        this.kafkaProducer = kafkaProducer;
        this.jsonPort = jsonPort;
    }

    @Override
    public void publish(OrderQuery orderQuery) {
        Long orderId = orderQuery.id();
        logger.info("Received OrderQuery for order id: {}", orderId);

        try {
            OrderQueryKafkaModel kafkaModel = new OrderQueryKafkaModel(orderQuery);
            kafkaProducer.send(orderQueryTopicName, orderId.toString(), jsonPort.convertDataToJson(kafkaModel));

            logger.info("OrderQuery sent to Kafka for order id: {}", orderId);
        } catch (Exception e) {
            logger.error("Error while sending OrderQuery to kafka with order id: {}  error: {}", orderId, e.getMessage());
        }
    }
}
