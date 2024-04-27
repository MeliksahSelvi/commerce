package com.commerce.order.service.outbox.scheduler;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.outbox.OutboxScheduler;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.outbox.model.InventoryOutbox;
import com.commerce.order.service.outbox.port.jpa.InventoryOutboxDataPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 04.03.2024
 */

@DomainComponent
public class InventoryOutboxCleanerScheduler implements OutboxScheduler {

    private static final Logger logger = LoggerFactory.getLogger(InventoryOutboxScheduler.class);
    private final InventoryOutboxDataPort dataPort;

    public InventoryOutboxCleanerScheduler(InventoryOutboxDataPort dataPort) {
        this.dataPort = dataPort;
    }

    @Override
    @Scheduled(cron = "@midnight")
    public void processOutboxMessage() {
        Optional<List<InventoryOutbox>> toBeProcessedOutboxMessages = dataPort.
                findByOutboxStatusAndSagaStatuses(OutboxStatus.COMPLETED,
                        SagaStatus.PAYING,
                        SagaStatus.SUCCEEDED,
                        SagaStatus.CANCELLED);

        Optional<List<InventoryOutbox>> toBeTerminatedOutboxMessages = dataPort.
                findByOutboxStatusAndSagaStatusAndOrderInventoryStatus(OutboxStatus.COMPLETED,
                        SagaStatus.CANCELLING,
                        OrderInventoryStatus.CHECKING_ROLLBACK);

        toBeProcessedOutboxMessages.ifPresent(inventoryOutboxMessages -> {
            logger.info("Received {} InventoryOutboxMessage for clean-up", inventoryOutboxMessages.size());

            dataPort.deleteByOutboxStatusAndSagaStatuses(
                    OutboxStatus.COMPLETED,
                    SagaStatus.PAYING,
                    SagaStatus.SUCCEEDED,
                    SagaStatus.CANCELLED);
            logger.info("{} InventoryOutboxMessage deleted!", inventoryOutboxMessages.size());
        });

        toBeTerminatedOutboxMessages.ifPresent(inventoryOutboxMessages -> {
            logger.info("Received {} InventoryOutboxMessage for clean-up", inventoryOutboxMessages.size());

            dataPort.deleteByOutboxStatusAndSagaStatusAndOrderInventoryStatus(
                    OutboxStatus.COMPLETED,
                    SagaStatus.CANCELLING,
                    OrderInventoryStatus.CHECKING_ROLLBACK);
            logger.info("{} InventoryOutboxMessage deleted!", inventoryOutboxMessages.size());
        });
    }
}
