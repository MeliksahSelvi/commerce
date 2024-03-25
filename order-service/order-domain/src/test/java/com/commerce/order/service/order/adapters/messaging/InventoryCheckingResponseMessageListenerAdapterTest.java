package com.commerce.order.service.order.adapters.messaging;

import ch.qos.logback.classic.Level;
import com.commerce.order.service.adapter.saga.FakeInventoryCheckingRollbackSagaStep;
import com.commerce.order.service.adapter.saga.FakeInventoryCheckingSagaStep;
import com.commerce.order.service.common.LoggerTest;
import com.commerce.order.service.common.valueobject.InventoryStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.order.usecase.InventoryResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class InventoryCheckingResponseMessageListenerAdapterTest extends LoggerTest<InventoryCheckingResponseMessageListenerAdapter> {

    private static final UUID sagaId=UUID.fromString("5bf96862-0c98-41ef-a952-e03d2ded6a6a");

    InventoryCheckingResponseMessageListenerAdapter adapter;

    public InventoryCheckingResponseMessageListenerAdapterTest() {
        super(InventoryCheckingResponseMessageListenerAdapter.class);
    }

    @BeforeEach
    void setUp() {
        adapter = new InventoryCheckingResponseMessageListenerAdapter(
                new FakeInventoryCheckingSagaStep(), new FakeInventoryCheckingRollbackSagaStep());
    }

    @AfterEach
    void cleanUp() {
        destroy();
    }

    @Test
    void should_checking_inventory_status_available() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameter(sagaId,InventoryStatus.AVAILABLE, OrderInventoryStatus.CHECKING);
        var logMessage = "InventoryResponse is available for Inventory checking action";

        //when
        //then
        assertDoesNotThrow(() -> adapter.checking(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_checking_inventory_status_non_available() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameter(sagaId,InventoryStatus.NON_AVAILABLE, OrderInventoryStatus.CHECKING);
        var logMessage = "InventoryResponse is not available for Inventory checking action";

        //when
        //then
        assertDoesNotThrow(() -> adapter.checking(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_checking_rollback_inventory_status_available() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameter(sagaId,InventoryStatus.AVAILABLE, OrderInventoryStatus.CHECKING_ROLLBACK);
        var logMessage = "InventoryResponse is available for Inventory checking rollback action";

        //when
        //then
        assertDoesNotThrow(() -> adapter.checkingRollback(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_checking_rollback_inventory_status_non_available() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameter(sagaId,InventoryStatus.NON_AVAILABLE, OrderInventoryStatus.CHECKING_ROLLBACK);
        var logMessage = "InventoryResponse is not available for Inventory checking rollback action";

        //when
        //then
        assertDoesNotThrow(() -> adapter.checkingRollback(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    private InventoryResponse buildInventoryResponseWithParameter(UUID sagaId,InventoryStatus inventoryStatus, OrderInventoryStatus orderInventoryStatus) {
        return new InventoryResponse(sagaId, 1L, 1L, inventoryStatus, orderInventoryStatus, new ArrayList<>());
    }
}
