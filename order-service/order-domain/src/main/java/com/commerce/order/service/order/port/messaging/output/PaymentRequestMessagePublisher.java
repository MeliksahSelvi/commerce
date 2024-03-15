package com.commerce.order.service.order.port.messaging.output;

import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.outbox.entity.PaymentOutbox;

import java.util.function.BiConsumer;

/**
 * @Author mselvi
 * @Created 02.03.2024
 */

public interface PaymentRequestMessagePublisher {

    void publish(PaymentOutbox paymentOutbox,
                 BiConsumer<PaymentOutbox, OutboxStatus> outboxCallback);
}
