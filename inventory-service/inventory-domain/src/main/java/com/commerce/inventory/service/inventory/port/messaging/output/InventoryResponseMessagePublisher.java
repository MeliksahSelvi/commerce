package com.commerce.inventory.service.inventory.port.messaging.output;

import com.commerce.inventory.service.common.outbox.OutboxStatus;
import com.commerce.inventory.service.outbox.model.OrderOutbox;

import java.util.function.BiConsumer;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public interface InventoryResponseMessagePublisher {

    void publish(OrderOutbox orderOutbox, BiConsumer<OrderOutbox, OutboxStatus> outboxCallback);
}
