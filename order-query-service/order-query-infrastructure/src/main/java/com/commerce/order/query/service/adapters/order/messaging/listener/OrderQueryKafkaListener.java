package com.commerce.order.query.service.adapters.order.messaging.listener;

import com.commerce.order.query.service.common.exception.OrderQueryInfraException;
import com.commerce.order.query.service.common.messaging.kafka.consumer.KafkaConsumer;
import com.commerce.order.query.service.common.messaging.kafka.model.OrderQueryKafkaModel;
import com.commerce.order.query.service.order.model.OrderQuery;
import com.commerce.order.query.service.order.port.messaging.input.OrderQueryMessageListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

@Component
public class OrderQueryKafkaListener implements KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderQueryKafkaListener.class);
    private final OrderQueryMessageListener orderQueryMessageListener;
    private final ObjectMapper objectMapper;

    public OrderQueryKafkaListener(OrderQueryMessageListener orderQueryMessageListener, ObjectMapper objectMapper) {
        this.orderQueryMessageListener = orderQueryMessageListener;
        this.objectMapper = objectMapper;
    }


    @Override
    @KafkaListener(id = "${kafka-consumer-config.order-query-consumer-group-id}",
            topics = "${order-query-service.order-query-topic-name}")
    public void receive(@Payload List<String> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        logger.info("{} number of messages received with keys:{}, partitions:{} and offsets:{}",
                messages.size(), keys, partitions, offsets);

        for (String message : messages) {
            try {
                OrderQueryKafkaModel kafkaModel = exractDataFromJson(message);
                OrderQuery orderQuery = buildModel(kafkaModel);
                logger.info("Order received from queue by orderId: {}", orderQuery.id());
                orderQueryMessageListener.processMessage(orderQuery);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

    }

    private OrderQueryKafkaModel exractDataFromJson(String strAsJson) {
        try {
            return objectMapper.readValue(strAsJson, OrderQueryKafkaModel.class);
        } catch (JsonProcessingException e) {
            throw new OrderQueryInfraException("Could not read NotificationRequestKafkaModel object", e);
        }
    }

    private OrderQuery buildModel(OrderQueryKafkaModel kafkaModel) {
        return new OrderQuery(kafkaModel.id(), kafkaModel.orderStatus());
    }
}
