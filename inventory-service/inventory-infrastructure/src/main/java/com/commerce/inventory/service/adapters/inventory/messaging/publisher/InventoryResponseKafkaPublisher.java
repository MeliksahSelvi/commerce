package com.commerce.inventory.service.adapters.inventory.messaging.publisher;

import com.commerce.inventory.service.common.messaging.kafka.helper.KafkaHelper;
import com.commerce.inventory.service.common.outbox.OutboxStatus;
import com.commerce.inventory.service.common.valueobject.OrderInventoryStatus;
import com.commerce.inventory.service.inventory.port.messaging.output.InventoryResponseMessagePublisher;
import com.commerce.inventory.service.outbox.entity.OrderOutbox;
import com.commerce.inventory.service.outbox.entity.OrderOutboxPayload;
import com.commerce.kafka.model.InventoryResponseAvroModel;
import com.commerce.kafka.model.InventoryStatus;
import com.commerce.kafka.producer.KafkaProducer;
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
    private final KafkaProducer<String, InventoryResponseAvroModel> kafkaProducer;
    private final KafkaHelper kafkaHelper;

    @Value("${inventory-service.inventory-response-topic-name}")
    private String inventoryResponseTopicName;

    public InventoryResponseKafkaPublisher(KafkaProducer<String, InventoryResponseAvroModel> kafkaProducer, KafkaHelper kafkaHelper) {
        this.kafkaProducer = kafkaProducer;
        this.kafkaHelper = kafkaHelper;
    }


    @Override
    public void publish(OrderOutbox orderOutbox, BiConsumer<OrderOutbox, OutboxStatus> outboxCallback) {
        OrderOutboxPayload payload = kafkaHelper.getPayload(orderOutbox.getPayload(), OrderOutboxPayload.class);
        UUID sagaId = orderOutbox.getSagaId();
        Long orderId = payload.orderId();

        logger.info("Received OrderOutbox for order id: {} and saga id: {}", orderId, sagaId);

        try {
            InventoryResponseAvroModel kafkaModel = buildAvroModel(sagaId, orderOutbox.getOrderInventoryStatus(), payload);
            kafkaProducer.send(inventoryResponseTopicName, sagaId.toString(), kafkaModel,
                    kafkaHelper.getKafkaCallback(kafkaModel, orderOutbox, outboxCallback, orderId));

            logger.info("InventoryResponseAvroModel sent to Kafka for order id: {} and saga id: {}", orderId, sagaId);
        } catch (Exception e) {
            logger.error("Error while sending InventoryResponseAvroModel to kafka with order id: {} and saga id: {} error: {}",
                    orderId, sagaId, e.getMessage());
        }
    }

    private InventoryResponseAvroModel buildAvroModel(UUID sagaId, OrderInventoryStatus orderInventoryStatus, OrderOutboxPayload payload) {
        return InventoryResponseAvroModel.newBuilder()
                .setSagaId(sagaId.toString())
                .setOrderId(payload.orderId())
                .setCustomerId(payload.customerId())
                .setInventoryStatus(InventoryStatus.valueOf(payload.inventoryStatus().name()))
                .setOrderInventoryStatus(com.commerce.kafka.model.OrderInventoryStatus.valueOf(orderInventoryStatus.name()))
                .setFailureMessages(payload.failureMessages())
                .build();
    }
}
