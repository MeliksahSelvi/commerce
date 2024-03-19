package com.commerce.order.service.adapters.order.messaging.publisher;

import com.commerce.kafka.model.InventoryRequestAvroModel;
import com.commerce.kafka.model.OrderInventoryStatus;
import com.commerce.kafka.model.OrderItemPayload;
import com.commerce.kafka.producer.KafkaProducer;
import com.commerce.order.service.common.messaging.kafka.helper.KafkaHelper;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.order.port.messaging.output.InventoryRequestMessagePublisher;
import com.commerce.order.service.outbox.entity.InventoryOutbox;
import com.commerce.order.service.outbox.entity.InventoryOutboxPayload;
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
    private final KafkaProducer<String, InventoryRequestAvroModel> kafkaProducer;
    private final KafkaHelper kafkaHelper;

    @Value("${order-service.inventory-request-topic-name}")
    private String inventoryRequestTopicName;

    public InventoryRequestKafkaPublisher(KafkaProducer<String, InventoryRequestAvroModel> kafkaProducer, KafkaHelper kafkaHelper) {
        this.kafkaProducer = kafkaProducer;
        this.kafkaHelper = kafkaHelper;
    }

    @Override
    public void publish(InventoryOutbox inventoryOutbox, BiConsumer<InventoryOutbox, OutboxStatus> outboxCallback) {
        InventoryOutboxPayload payload = kafkaHelper.getPayload(inventoryOutbox.getPayload(), InventoryOutboxPayload.class);
        UUID sagaId = inventoryOutbox.getSagaId();
        Long orderId = payload.orderId();

        logger.info("Received InventoryOutbox for order id: {} and saga id: {}", orderId, sagaId);

        try {
            InventoryRequestAvroModel avroModel = buildAvroModel(sagaId, payload);
            kafkaProducer.send(inventoryRequestTopicName, sagaId.toString(), avroModel,
                    kafkaHelper.getKafkaCallback(avroModel, inventoryOutbox, outboxCallback, orderId));

            logger.info("InventoryRequestAvroModel sent to Kafka for order id: {} and saga id: {}", orderId, sagaId);
        } catch (Exception e) {
            logger.error("Error while sending InventoryRequestAvroModel to kafka with order id: {} and saga id: {} error: {}", orderId, sagaId, e.getMessage());
        }
    }

    private InventoryRequestAvroModel buildAvroModel(UUID sagaId, InventoryOutboxPayload payload) {
        return InventoryRequestAvroModel.newBuilder()
                .setSagaId(sagaId.toString())
                .setOrderId(payload.orderId())
                .setCustomerId(payload.customerId())
                .setOrderInventoryStatus(OrderInventoryStatus.valueOf(payload.orderInventoryStatus().name()))
                .setCost(payload.cost())
                .setItems(payload.items().stream().map(orderItemPayload -> OrderItemPayload.newBuilder()
                        .setId(orderItemPayload.getId())
                        .setOrderId(orderItemPayload.getOrderId())
                        .setProductId(orderItemPayload.getProductId())
                        .setPrice(orderItemPayload.getPrice())
                        .setQuantity(orderItemPayload.getQuantity())
                        .setTotalPrice(orderItemPayload.getTotalPrice())
                        .build()).toList()
                )
                .build();
    }
}
