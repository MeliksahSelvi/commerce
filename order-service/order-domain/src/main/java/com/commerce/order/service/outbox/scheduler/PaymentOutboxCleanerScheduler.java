package com.commerce.order.service.outbox.scheduler;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.outbox.OutboxScheduler;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.outbox.model.PaymentOutbox;
import com.commerce.order.service.outbox.port.jpa.PaymentOutboxDataPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 02.03.2024
 */

@DomainComponent
public class PaymentOutboxCleanerScheduler implements OutboxScheduler {

    private static final Logger logger = LoggerFactory.getLogger(PaymentOutboxCleanerScheduler.class);
    private final PaymentOutboxDataPort paymentOutboxDataPort;

    public PaymentOutboxCleanerScheduler(PaymentOutboxDataPort paymentOutboxDataPort) {
        this.paymentOutboxDataPort = paymentOutboxDataPort;
    }

    @Override
    @Scheduled(cron = "@midnight")
    public void processOutboxMessage() {
        Optional<List<PaymentOutbox>> toBeProcessedOutboxMessages = paymentOutboxDataPort.
                findByOutboxStatusAndSagaStatuses(OutboxStatus.COMPLETED,
                        SagaStatus.PROCESSING,
                        SagaStatus.CANCELLED);

        toBeProcessedOutboxMessages.ifPresent(paymentOutboxMessages -> {
            logger.info("Received {} PaymentOutboxMessage for clean-up", paymentOutboxMessages.size());

            paymentOutboxDataPort.deleteByOutboxStatusAndSagaStatuses(
                    OutboxStatus.COMPLETED,
                    SagaStatus.PROCESSING,
                    SagaStatus.CANCELLED);
            logger.info("{} PaymentOutboxMessage deleted!", paymentOutboxMessages.size());
        });
    }
}
