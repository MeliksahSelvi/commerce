package com.commerce.order.service.order.port.messaging.output;

import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.outbox.model.InventoryOutbox;

import java.util.function.BiConsumer;

/**
 * @Author mselvi
 * @Created 04.03.2024
 */

public interface InventoryRequestMessagePublisher {

    void publish(InventoryOutbox inventoryOutbox,
                 BiConsumer<InventoryOutbox, OutboxStatus> outboxCallback);
}
