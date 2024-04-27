package com.commerce.order.service.outbox.scheduler;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.outbox.OutboxScheduler;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.outbox.model.PaymentOutbox;
import com.commerce.order.service.outbox.port.jpa.PaymentOutboxDataPort;
import com.commerce.order.service.order.port.messaging.output.PaymentRequestMessagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author mselvi
 * @Created 02.03.2024
 */

@DomainComponent
public class PaymentOutboxScheduler implements OutboxScheduler {

    private static final Logger logger = LoggerFactory.getLogger(PaymentOutboxScheduler.class);
    private final PaymentOutboxDataPort paymentOutboxDataPort;
    private final PaymentRequestMessagePublisher publisher;

    public PaymentOutboxScheduler(PaymentOutboxDataPort paymentOutboxDataPort, PaymentRequestMessagePublisher publisher) {
        this.paymentOutboxDataPort = paymentOutboxDataPort;
        this.publisher = publisher;
    }

    @Override
    @Transactional
    @Scheduled(fixedDelayString = "${order-service.outbox-scheduler-fixed-rate}",
            initialDelayString = "${order-service.outbox-scheduler-initial-delay}")
    public void processOutboxMessage() {
        Optional<List<PaymentOutbox>> toBeProcessedOutboxMessages = paymentOutboxDataPort
                .findByOutboxStatusAndSagaStatuses(OutboxStatus.STARTED, SagaStatus.PAYING, SagaStatus.CANCELLING);

        toBeProcessedOutboxMessages.ifPresent(paymentOutboxMessages -> {
            logger.info("Received {} PaymentOutbox with ids: {}, sending to message bus!",
                    paymentOutboxMessages.size(),
                    paymentOutboxMessages.stream().map(outboxMessage -> outboxMessage.getId().toString())
                            .collect(Collectors.joining(",")));

            paymentOutboxMessages.forEach(paymentOutboxMessage -> publisher.publish(paymentOutboxMessage, this::updateOutboxStatus));
            logger.info("{} PaymentOutbox sent to message bus!", paymentOutboxMessages.size());
        });
    }

    @Transactional
    public void updateOutboxStatus(PaymentOutbox paymentOutbox, OutboxStatus outboxStatus) {
        paymentOutbox.setOutboxStatus(outboxStatus);
        paymentOutboxDataPort.save(paymentOutbox);
        logger.info("PaymentOutbox is updated with outbox status: {}", outboxStatus.name());
    }
}
