package com.commerce.order.service.order.adapters.messaging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.commerce.order.service.common.valueobject.InventoryStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.order.adapters.messaging.adapter.FakeInventoryCheckingRollbackSagaStep;
import com.commerce.order.service.order.adapters.messaging.adapter.FakeInventoryCheckingSagaStep;
import com.commerce.order.service.appender.MemoryApender;
import com.commerce.order.service.order.usecase.InventoryResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class InventoryCheckingResponseMessageListenerAdapterTest {

    InventoryCheckingResponseMessageListenerAdapter adapter;
    MemoryApender memoryApender;

    @BeforeEach
    void setUp() {
        adapter = new InventoryCheckingResponseMessageListenerAdapter(
                new FakeInventoryCheckingSagaStep(), new FakeInventoryCheckingRollbackSagaStep());

        Logger logger = (Logger) LoggerFactory.getLogger(adapter.getClass());
        memoryApender = new MemoryApender();
        memoryApender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.INFO);
        logger.addAppender(memoryApender);
        memoryApender.start();
    }

    @AfterEach
    void cleanUp() {
        memoryApender.reset();
        memoryApender.stop();
    }

    @Test
    void should_checking_inventory_status_available() {
        //given
        InventoryResponse inventoryResponse = buildInventoryResponseWithParameter(InventoryStatus.AVAILABLE, OrderInventoryStatus.CHECKING);
        String logMessage = "InventoryResponse is available for Inventory checking action";

        //when
        //then
        assertDoesNotThrow(() -> adapter.checking(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_checking_inventory_status_non_available() {
        //given
        InventoryResponse inventoryResponse = buildInventoryResponseWithParameter(InventoryStatus.NON_AVAILABLE, OrderInventoryStatus.CHECKING);
        String logMessage = "InventoryResponse is not available for Inventory checking action";

        //when
        //then
        assertDoesNotThrow(() -> adapter.checking(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_checking_rollback_inventory_status_available() {
        //given
        InventoryResponse inventoryResponse = buildInventoryResponseWithParameter(InventoryStatus.AVAILABLE, OrderInventoryStatus.CHECKING_ROLLBACK);
        String logMessage = "InventoryResponse is available for Inventory checking rollback action";

        //when
        //then
        assertDoesNotThrow(() -> adapter.checkingRollback(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_checking_rollback_inventory_status_non_available() {
        //given
        InventoryResponse inventoryResponse = buildInventoryResponseWithParameter(InventoryStatus.NON_AVAILABLE, OrderInventoryStatus.CHECKING_ROLLBACK);
        String logMessage = "InventoryResponse is not available for Inventory checking rollback action";

        //when
        //then
        assertDoesNotThrow(() -> adapter.checkingRollback(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    private InventoryResponse buildInventoryResponseWithParameter(InventoryStatus inventoryStatus, OrderInventoryStatus orderInventoryStatus) {
        return new InventoryResponse(UUID.randomUUID(), 1L, 1L, inventoryStatus, orderInventoryStatus, new ArrayList<>());
    }
}
