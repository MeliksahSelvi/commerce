package com.commerce.inventory.service.outbox.scheduler;

import com.commerce.inventory.service.common.DomainComponent;
import com.commerce.inventory.service.common.outbox.OutboxScheduler;
import com.commerce.inventory.service.common.outbox.OutboxStatus;
import com.commerce.inventory.service.outbox.entity.OrderOutbox;
import com.commerce.inventory.service.outbox.port.jpa.OrderOutboxDataPort;
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

        toBeProcessedOutboxMessages.ifPresent(inventoryOutboxMessages -> {
            logger.info("Received {} InventoryOutboxMessage for clean-up", inventoryOutboxMessages.size());

            dataPort.deleteByOutboxStatus(OutboxStatus.COMPLETED);
            logger.info("{} InventoryOutboxMessage deleted!", inventoryOutboxMessages.size());
        });
    }
}
