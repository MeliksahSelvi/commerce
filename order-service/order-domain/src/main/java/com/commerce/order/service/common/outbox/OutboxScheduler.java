package com.commerce.order.service.common.outbox;

/**
 * @Author mselvi
 * @Created 02.03.2024
 */

public interface OutboxScheduler {
    void processOutboxMessage();
}
