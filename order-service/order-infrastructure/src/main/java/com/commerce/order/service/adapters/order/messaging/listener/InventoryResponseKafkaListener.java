package com.commerce.order.service.adapters.order.messaging.listener;

import com.commerce.order.service.common.messaging.kafka.consumer.KafkaConsumer;
import com.commerce.order.service.common.messaging.kafka.model.InventoryResponseKafkaModel;
import com.commerce.order.service.order.port.json.JsonPort;
import com.commerce.order.service.order.port.messaging.input.InventoryCheckingResponseMessageListener;
import com.commerce.order.service.order.port.messaging.input.InventoryUpdatingResponseMessageListener;
import com.commerce.order.service.order.usecase.InventoryResponse;
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
 * @Created 06.03.2024
 */

@Component
public class InventoryResponseKafkaListener implements KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(InventoryResponseKafkaListener.class);
    private final InventoryCheckingResponseMessageListener inventoryCheckingResponseMessageListener;
    private final InventoryUpdatingResponseMessageListener inventoryUpdatingResponseMessageListener;
    private final JsonPort jsonPort;

    public InventoryResponseKafkaListener(InventoryCheckingResponseMessageListener inventoryCheckingResponseMessageListener,
                                          InventoryUpdatingResponseMessageListener inventoryUpdatingResponseMessageListener, JsonPort jsonPort) {
        this.inventoryCheckingResponseMessageListener = inventoryCheckingResponseMessageListener;
        this.inventoryUpdatingResponseMessageListener = inventoryUpdatingResponseMessageListener;
        this.jsonPort = jsonPort;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.inventory-consumer-group-id}",
            topics = "${order-service.inventory-response-topic-name}")
    public void receive(@Payload List<String> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        logger.info("{} number of messages received with keys:{}, partitions:{} and offsets:{}",
                messages.size(), keys, partitions, offsets);


        for (String message : messages) {
            try {
                InventoryResponseKafkaModel kafkaModel = jsonPort.exractDataFromJson(message, InventoryResponseKafkaModel.class);
                InventoryResponse inventoryResponse = buildInventoryResponse(kafkaModel);
                switch (inventoryResponse.orderInventoryStatus()) {
                    case CHECKING -> {
                        logger.info("InventoryResponse sent to checking action");
                        inventoryCheckingResponseMessageListener.checking(inventoryResponse);
                    }
                    case CHECKING_ROLLBACK -> {
                        logger.info("InventoryResponse sent to checking rollback action");
                        inventoryCheckingResponseMessageListener.checkingRollback(inventoryResponse);
                    }
                    case UPDATING -> {
                        logger.info("InventoryResponse sent to updating action");
                        inventoryUpdatingResponseMessageListener.updating(inventoryResponse);
                    }
                    case UPDATING_ROLLBACK -> {
                        logger.info("InventoryResponse sent to updating rollback action");
                        inventoryUpdatingResponseMessageListener.updatingRollback(inventoryResponse);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

    }

    private InventoryResponse buildInventoryResponse(InventoryResponseKafkaModel kafkaModel) {
        return new InventoryResponse(UUID.fromString(kafkaModel.sagaId()), kafkaModel.orderId(), kafkaModel.customerId(),
                kafkaModel.inventoryStatus(), kafkaModel.orderInventoryStatus(), kafkaModel.failureMessages());
    }
}
