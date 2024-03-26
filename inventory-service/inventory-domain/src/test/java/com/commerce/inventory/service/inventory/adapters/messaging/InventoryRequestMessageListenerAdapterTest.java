package com.commerce.inventory.service.inventory.adapters.messaging;

import ch.qos.logback.classic.Level;
import com.commerce.inventory.service.adapter.FakeInventoryCheckingSagaStep;
import com.commerce.inventory.service.adapter.FakeInventoryUpdatingSagaStep;
import com.commerce.inventory.service.common.LoggerTest;
import com.commerce.inventory.service.common.valueobject.Money;
import com.commerce.inventory.service.common.valueobject.OrderInventoryStatus;
import com.commerce.inventory.service.inventory.usecase.InventoryRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class InventoryRequestMessageListenerAdapterTest extends LoggerTest<InventoryRequestMessageListenerAdapter> {

    InventoryRequestMessageListenerAdapter adapter;

    public InventoryRequestMessageListenerAdapterTest() {
        super(InventoryRequestMessageListenerAdapter.class);
    }

    @BeforeEach
    void setUp() {
        adapter = new InventoryRequestMessageListenerAdapter(new FakeInventoryUpdatingSagaStep(), new FakeInventoryCheckingSagaStep());
    }

    @AfterEach
    void cleanUp() {
        destroy();
    }

    @Test
    void should_checking_when_order_inventory_status_checking() {
        //given
        InventoryRequest inventoryRequest = buildInventoryRequest(OrderInventoryStatus.CHECKING);
        String logMessage = "Process action for inventory checking started with InventoryRequest";

        //when
        //then
        assertDoesNotThrow(() -> adapter.checking(inventoryRequest));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_checking_when_order_inventory_status_checking_rollback() {
        //given
        InventoryRequest inventoryRequest = buildInventoryRequest(OrderInventoryStatus.CHECKING_ROLLBACK);
        String logMessage = "Rollback action for inventory checking started with InventoryRequest";

        //when
        //then
        assertDoesNotThrow(() -> adapter.checking(inventoryRequest));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_checking_fail_when_order_inventory_status_updating() {
        //given
        InventoryRequest inventoryRequest = buildInventoryRequest(OrderInventoryStatus.UPDATING);
        String logMessage = "Process action for inventory checking started with InventoryRequest";

        //when
        //then
        assertFalse(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_checking_fail_when_order_inventory_status_updating_rollback() {
        //given
        InventoryRequest inventoryRequest = buildInventoryRequest(OrderInventoryStatus.UPDATING_ROLLBACK);
        String logMessage = "Process action for inventory checking started with InventoryRequest";

        //when
        //then
        assertFalse(memoryApender.contains(logMessage, Level.INFO));
    }


    @Test
    void should_updating_when_order_inventory_status_updating() {
        //given
        InventoryRequest inventoryRequest = buildInventoryRequest(OrderInventoryStatus.UPDATING);
        String logMessage = "Process action for inventory updating started with InventoryRequest";

        //when
        //then
        assertDoesNotThrow(() -> adapter.updating(inventoryRequest));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_updating_when_order_inventory_status_updating_rollback() {
        //given
        InventoryRequest inventoryRequest = buildInventoryRequest(OrderInventoryStatus.UPDATING_ROLLBACK);
        String logMessage = "Rollback action for inventory updating started with InventoryRequest";

        //when
        //then
        assertDoesNotThrow(() -> adapter.updating(inventoryRequest));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_updating_fail_when_order_inventory_status_checking() {
        //given
        InventoryRequest inventoryRequest = buildInventoryRequest(OrderInventoryStatus.CHECKING);
        String logMessage = "Process action for inventory updating started with InventoryRequest";

        //when
        //then
        assertFalse(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_updating_fail_when_order_inventory_status_checking_rollback() {
        //given
        InventoryRequest inventoryRequest = buildInventoryRequest(OrderInventoryStatus.CHECKING_ROLLBACK);
        String logMessage = "Process action for inventory updating started with InventoryRequest";

        //when
        //then
        assertFalse(memoryApender.contains(logMessage, Level.INFO));
    }

    private InventoryRequest buildInventoryRequest(OrderInventoryStatus orderInventoryStatus) {
        return new InventoryRequest(UUID.randomUUID(), 1L, 1L, new Money(BigDecimal.TEN), orderInventoryStatus, Collections.emptyList());
    }
}
