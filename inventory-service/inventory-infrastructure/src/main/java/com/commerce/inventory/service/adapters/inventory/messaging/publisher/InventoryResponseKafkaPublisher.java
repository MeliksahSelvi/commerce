package com.commerce.inventory.service.adapters.inventory.messaging.publisher;

import com.commerce.inventory.service.common.messaging.kafka.helper.KafkaHelper;
import com.commerce.inventory.service.common.messaging.kafka.model.InventoryResponseKafkaModel;
import com.commerce.inventory.service.common.messaging.kafka.producer.KafkaProducer;
import com.commerce.inventory.service.common.outbox.OutboxStatus;
import com.commerce.inventory.service.inventory.port.json.JsonPort;
import com.commerce.inventory.service.inventory.port.messaging.output.InventoryResponseMessagePublisher;
import com.commerce.inventory.service.outbox.model.OrderOutbox;
import com.commerce.inventory.service.outbox.model.OrderOutboxPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.BiConsumer;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

@Component
public class InventoryResponseKafkaPublisher implements InventoryResponseMessagePublisher {

    private static final Logger logger = LoggerFactory.getLogger(InventoryResponseKafkaPublisher.class);
    private final KafkaProducer kafkaProducer;
    private final KafkaHelper kafkaHelper;
    private final JsonPort jsonPort;

    @Value("${inventory-service.inventory-response-topic-name}")
    private String inventoryResponseTopicName;

    public InventoryResponseKafkaPublisher(KafkaProducer kafkaProducer, KafkaHelper kafkaHelper, JsonPort jsonPort) {
        this.kafkaProducer = kafkaProducer;
        this.kafkaHelper = kafkaHelper;
        this.jsonPort = jsonPort;
    }


    @Override
    public void publish(OrderOutbox orderOutbox, BiConsumer<OrderOutbox, OutboxStatus> outboxCallback) {
        OrderOutboxPayload payload = kafkaHelper.getPayload(orderOutbox.getPayload(), OrderOutboxPayload.class);
        UUID sagaId = orderOutbox.getSagaId();
        Long orderId = payload.orderId();

        logger.info("Received OrderOutbox for order id: {} and saga id: {}", orderId, sagaId);

        try {
            InventoryResponseKafkaModel kafkaModel = new InventoryResponseKafkaModel(sagaId, payload);
            kafkaProducer.send(inventoryResponseTopicName, sagaId.toString(), jsonPort.convertDataToJson(kafkaModel),
                    kafkaHelper.getKafkaCallback(kafkaModel, orderOutbox, outboxCallback, orderId));

            logger.info("InventoryResponseKafkaModel sent to Kafka for order id: {} and saga id: {}", orderId, sagaId);
        } catch (Exception e) {
            logger.error("Error while sending InventoryResponseKafkaModel to kafka with order id: {} and saga id: {} error: {}",
                    orderId, sagaId, e.getMessage());
        }
    }
}
