package com.commerce.inventory.service.adapters.inventory.messaging.listener;

import com.commerce.inventory.service.common.valueobject.Money;
import com.commerce.inventory.service.common.valueobject.OrderInventoryStatus;
import com.commerce.inventory.service.common.valueobject.Quantity;
import com.commerce.inventory.service.inventory.port.messaging.input.InventoryRequestMessageListener;
import com.commerce.inventory.service.inventory.usecase.InventoryRequest;
import com.commerce.inventory.service.inventory.usecase.OrderItem;
import com.commerce.kafka.consumer.KafkaConsumer;
import com.commerce.kafka.model.InventoryRequestAvroModel;
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
public class InventoryRequestKafkaListener implements KafkaConsumer<InventoryRequestAvroModel> {

    private static final Logger logger = LoggerFactory.getLogger(InventoryRequestKafkaListener.class);
    private final InventoryRequestMessageListener inventoryRequestMessageListener;

    public InventoryRequestKafkaListener(InventoryRequestMessageListener inventoryRequestMessageListener) {
        this.inventoryRequestMessageListener = inventoryRequestMessageListener;
    }


    @Override
    @KafkaListener(id = "${kafka-consumer-config.inventory-consumer-group-id}",
            topics = "${inventory-service.inventory-request-topic-name}")
    public void receive(@Payload List<InventoryRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        logger.info("{} number of messages received with keys:{}, partitions:{} and offsets:{}",
                messages.size(), keys, partitions, offsets);

        for (InventoryRequestAvroModel message : messages) {

            try {
                InventoryRequest inventoryRequest = buildInventoryRequest(message);
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

    private InventoryRequest buildInventoryRequest(InventoryRequestAvroModel avroModel) {
        return new InventoryRequest(UUID.fromString(avroModel.getSagaId()), avroModel.getOrderId(), avroModel.getCustomerId(),
                new Money(avroModel.getCost()), OrderInventoryStatus.valueOf(avroModel.getOrderInventoryStatus().name()),
                avroModel.getItems().stream().map(payload -> new OrderItem(payload.getId(), payload.getOrderId(), payload.getProductId(),
                        new Quantity(payload.getQuantity()), new Money(payload.getPrice()), new Money(payload.getTotalPrice()))).toList());
    }
}
