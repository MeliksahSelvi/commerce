package com.commerce.payment.service.outbox.scheduler;

import com.commerce.payment.service.common.DomainComponent;
import com.commerce.payment.service.common.outbox.OutboxScheduler;
import com.commerce.payment.service.common.outbox.OutboxStatus;
import com.commerce.payment.service.outbox.model.OrderOutbox;
import com.commerce.payment.service.outbox.port.jpa.OrderOutboxDataPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

@DomainComponent
public class OrderOutboxCleanerScheduler implements OutboxScheduler {

    private static final Logger logger = LoggerFactory.getLogger(OrderOutboxCleanerScheduler.class);
    private final OrderOutboxDataPort dataPort;

    public OrderOutboxCleanerScheduler(OrderOutboxDataPort dataPort) {
        this.dataPort = dataPort;
    }

    @Override
    @Scheduled(cron = "@midnight")
    public void processOutboxMessage() {
        Optional<List<OrderOutbox>> toBeProcessedOutboxMessages = dataPort.findByOutboxStatus(OutboxStatus.COMPLETED);

        toBeProcessedOutboxMessages.ifPresent(orderOutboxMessages -> {
            logger.info("Received {} OrderOutbox for clean-up", orderOutboxMessages.size());

            dataPort.deleteByOutboxStatus(OutboxStatus.COMPLETED);
            logger.info("{} OrderOutbox deleted!", orderOutboxMessages.size());
        });
    }
}
