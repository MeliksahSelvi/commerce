package com.commerce.order.service.adapters.order.messaging.publisher;

import com.commerce.order.service.common.messaging.kafka.helper.KafkaHelper;
import com.commerce.order.service.common.messaging.kafka.model.InventoryRequestKafkaModel;
import com.commerce.order.service.common.messaging.kafka.producer.KafkaProducer;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.order.port.json.JsonPort;
import com.commerce.order.service.order.port.messaging.output.InventoryRequestMessagePublisher;
import com.commerce.order.service.outbox.model.InventoryOutbox;
import com.commerce.order.service.outbox.model.InventoryOutboxPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.BiConsumer;

/**
 * @Author mselvi
 * @Created 06.03.2024
 */

@Component
public class InventoryRequestKafkaPublisher implements InventoryRequestMessagePublisher {

    private static final Logger logger = LoggerFactory.getLogger(InventoryRequestKafkaPublisher.class);
    private final KafkaProducer kafkaProducer;
    private final JsonPort jsonPort;
    private final KafkaHelper kafkaHelper;

    @Value("${order-service.inventory-request-topic-name}")
    private String inventoryRequestTopicName;

    public InventoryRequestKafkaPublisher(KafkaProducer kafkaProducer, JsonPort jsonPort, KafkaHelper kafkaHelper) {
        this.kafkaProducer = kafkaProducer;
        this.jsonPort = jsonPort;
        this.kafkaHelper = kafkaHelper;
    }

    @Override
    public void publish(InventoryOutbox inventoryOutbox, BiConsumer<InventoryOutbox, OutboxStatus> outboxCallback) {
        InventoryOutboxPayload payload = kafkaHelper.getPayload(inventoryOutbox.getPayload(), InventoryOutboxPayload.class);
        UUID sagaId = inventoryOutbox.getSagaId();
        Long orderId = payload.orderId();

        logger.info("Received InventoryOutbox for order id: {} and saga id: {}", orderId, sagaId);

        try {
            InventoryRequestKafkaModel kafkaModel = new InventoryRequestKafkaModel(sagaId, payload);
            kafkaProducer.send(inventoryRequestTopicName, sagaId.toString(), jsonPort.convertDataToJson(kafkaModel),
                    kafkaHelper.getKafkaCallback(kafkaModel, inventoryOutbox, outboxCallback, orderId));

            logger.info("InventoryRequestKafkaModel sent to Kafka for order id: {} and saga id: {}", orderId, sagaId);
        } catch (Exception e) {
            logger.error("Error while sending InventoryRequestKafkaModel to kafka with order id: {} and saga id: {} error: {}", orderId, sagaId, e.getMessage());
        }
    }
}
