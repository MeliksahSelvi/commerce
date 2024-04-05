package com.commerce.inventory.service.adapters.inventory.messaging.listener;

import com.commerce.inventory.service.common.messaging.kafka.consumer.KafkaConsumer;
import com.commerce.inventory.service.common.messaging.kafka.model.InventoryRequestKafkaModel;
import com.commerce.inventory.service.common.valueobject.Money;
import com.commerce.inventory.service.common.valueobject.Quantity;
import com.commerce.inventory.service.inventory.port.json.JsonPort;
import com.commerce.inventory.service.inventory.port.messaging.input.InventoryRequestMessageListener;
import com.commerce.inventory.service.inventory.usecase.InventoryRequest;
import com.commerce.inventory.service.inventory.usecase.OrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

@Component
public class InventoryRequestKafkaListener implements KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(InventoryRequestKafkaListener.class);
    private final InventoryRequestMessageListener inventoryRequestMessageListener;
    private final JsonPort jsonPort;

    public InventoryRequestKafkaListener(InventoryRequestMessageListener inventoryRequestMessageListener, JsonPort jsonPort) {
        this.inventoryRequestMessageListener = inventoryRequestMessageListener;
        this.jsonPort = jsonPort;
    }


    @Override
    @KafkaListener(id = "${kafka-consumer-config.inventory-consumer-group-id}",
            topics = "${inventory-service.inventory-request-topic-name}")
    public void receive(@Payload List<String> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        logger.info("{} number of messages received with keys:{}, partitions:{} and offsets:{}",
                messages.size(), keys, partitions, offsets);

        for (String message : messages) {

            try {
                InventoryRequestKafkaModel kafkaModel = jsonPort.exractDataFromJson(message, InventoryRequestKafkaModel.class);
                InventoryRequest inventoryRequest = buildInventoryRequest(kafkaModel);
                switch (inventoryRequest.orderInventoryStatus()) {
                    case CHECKING, CHECKING_ROLLBACK -> {
                        logger.info("Inventory checking step for order id: {}", inventoryRequest.orderId());
                        inventoryRequestMessageListener.checking(inventoryRequest);
                    }
                    case UPDATING, UPDATING_ROLLBACK -> {
                        logger.info("Inventory updating step for order id: {}", inventoryRequest.orderId());
                        inventoryRequestMessageListener.updating(inventoryRequest);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    private InventoryRequest buildInventoryRequest(InventoryRequestKafkaModel kafkaModel) {
        return new InventoryRequest(UUID.fromString(kafkaModel.sagaId()), kafkaModel.orderId(), kafkaModel.customerId(),
                new Money(kafkaModel.cost()), kafkaModel.orderInventoryStatus(),
                kafkaModel.items().stream().map(payload -> new OrderItem(payload.id(), payload.orderId(), payload.productId(),
                        new Quantity(payload.quantity()), new Money(payload.price()), new Money(payload.totalPrice()))).toList());
    }
}
