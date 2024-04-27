package com.commerce.payment.service.outbox.port.jpa;

import com.commerce.payment.service.common.outbox.OutboxStatus;
import com.commerce.payment.service.outbox.model.OrderOutbox;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public interface OrderOutboxDataPort {

    OrderOutbox save(OrderOutbox orderOutbox);

    Optional<List<OrderOutbox>> findByOutboxStatus(OutboxStatus outboxStatus);

    void deleteByOutboxStatus(OutboxStatus outboxStatus);
}
