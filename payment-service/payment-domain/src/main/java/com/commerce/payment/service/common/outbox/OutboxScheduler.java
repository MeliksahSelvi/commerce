package com.commerce.payment.service.common.outbox;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public interface OutboxScheduler {
    void processOutboxMessage();
}
