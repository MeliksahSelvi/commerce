package com.commerce.order.service.adapters.order.messaging.listener;

import com.commerce.kafka.consumer.KafkaConsumer;
import com.commerce.kafka.model.InventoryResponseAvroModel;
import com.commerce.order.service.common.valueobject.InventoryStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
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
public class InventoryResponseKafkaListener implements KafkaConsumer<InventoryResponseAvroModel> {

    private static final Logger logger = LoggerFactory.getLogger(InventoryResponseKafkaListener.class);
    private final InventoryCheckingResponseMessageListener inventoryCheckingResponseMessageListener;
    private final InventoryUpdatingResponseMessageListener inventoryUpdatingResponseMessageListener;

    public InventoryResponseKafkaListener(InventoryCheckingResponseMessageListener inventoryCheckingResponseMessageListener,
                                          InventoryUpdatingResponseMessageListener inventoryUpdatingResponseMessageListener) {
        this.inventoryCheckingResponseMessageListener = inventoryCheckingResponseMessageListener;
        this.inventoryUpdatingResponseMessageListener = inventoryUpdatingResponseMessageListener;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.inventory-consumer-group-id}",
            topics = "${order-service.inventory-response-topic-name}")
    public void receive(@Payload List<InventoryResponseAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        logger.info("{} number of messages received with keys:{}, partitions:{} and offsets:{}",
                messages.size(), keys, partitions, offsets);

        for (InventoryResponseAvroModel message : messages) {

            try {
                InventoryResponse inventoryResponse = buildInventoryResponse(message);
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

    private InventoryResponse buildInventoryResponse(InventoryResponseAvroModel avroModel) {
        return new InventoryResponse(UUID.fromString(avroModel.getSagaId()), avroModel.getOrderId(), avroModel.getCustomerId(),
                InventoryStatus.valueOf(avroModel.getInventoryStatus().name()), OrderInventoryStatus.valueOf(avroModel.getOrderInventoryStatus().name()),
                avroModel.getFailureMessages());
    }
}
