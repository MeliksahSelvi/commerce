package com.commerce.payment.service.outbox.scheduler;

import com.commerce.payment.service.common.DomainComponent;
import com.commerce.payment.service.common.exception.PaymentDomainException;
import com.commerce.payment.service.common.outbox.OutboxScheduler;
import com.commerce.payment.service.common.outbox.OutboxStatus;
import com.commerce.payment.service.outbox.model.OrderOutbox;
import com.commerce.payment.service.outbox.port.jpa.OrderOutboxDataPort;
import com.commerce.payment.service.payment.port.messaging.output.PaymentResponseMessagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

@DomainComponent
public class OrderOutboxScheduler implements OutboxScheduler {

    private static final Logger logger = LoggerFactory.getLogger(OrderOutboxScheduler.class);
    private final PaymentResponseMessagePublisher publisher;
    private final OrderOutboxDataPort dataPort;

    public OrderOutboxScheduler(PaymentResponseMessagePublisher publisher, OrderOutboxDataPort dataPort) {
        this.publisher = publisher;
        this.dataPort = dataPort;
    }

    @Override
    @Transactional
    @Scheduled(fixedDelayString = "${payment-service.outbox-scheduler-fixed-rate}",
            initialDelayString = "${payment-service.outbox-scheduler-initial-delay}")
    public void processOutboxMessage() {
        Optional<List<OrderOutbox>> toBeProcessedOutboxMessages = dataPort.findByOutboxStatus(OutboxStatus.STARTED);

        toBeProcessedOutboxMessages.ifPresent(orderOutboxMessages -> {
            logger.info("Received {} OrderOutbox with ids: {}, sending to message bus!",
                    orderOutboxMessages.size(),
                    orderOutboxMessages.stream().map(outboxMessage -> outboxMessage.getId().toString())
                            .collect(Collectors.joining(",")));

            orderOutboxMessages.forEach(paymentOutboxMessage -> publisher.publish(paymentOutboxMessage, this::updateOutboxStatus));
            logger.info("{} OrderOutbox sent to message bus!", orderOutboxMessages.size());
        });
    }

    @Transactional
    public void updateOutboxStatus(OrderOutbox orderOutbox, OutboxStatus outboxStatus) {
        orderOutbox.setOutboxStatus(outboxStatus);
        OrderOutbox savedOutbox = dataPort.save(orderOutbox);
        if (savedOutbox == null) {
            throw new PaymentDomainException(String.format("Could not save OrderOutbox with outbox id: %d",
                    orderOutbox.getId()));
        }
        logger.info("OrderOutbox is updated with outbox status: {}", outboxStatus.name());
    }
}
