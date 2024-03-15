package com.commerce.payment.service.payment.port.messaging.output;

import com.commerce.payment.service.common.outbox.OutboxStatus;
import com.commerce.payment.service.outbox.entity.OrderOutbox;

import java.util.function.BiConsumer;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public interface PaymentResponseMessagePublisher {

    void publish(OrderOutbox orderOutbox, BiConsumer<OrderOutbox, OutboxStatus> outboxCallback);
}
