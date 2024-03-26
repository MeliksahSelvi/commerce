package com.commerce.inventory.service.saga;

import ch.qos.logback.classic.Level;
import com.commerce.inventory.service.adapter.FakeInventoryCheckingHelper;
import com.commerce.inventory.service.adapter.FakeInventoryUpdatingHelper;
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class InventoryUpdatingSagaStepTest extends LoggerTest<InventoryUpdatingSagaStep> {

    InventoryUpdatingSagaStep inventoryUpdatingSagaStep;

    public InventoryUpdatingSagaStepTest() {
        super(InventoryUpdatingSagaStep.class);
    }

    @BeforeEach
    void setUp() {
        inventoryUpdatingSagaStep = new InventoryUpdatingSagaStep(new FakeInventoryUpdatingHelper());
    }

    @AfterEach
    @Override
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_process(){
        //given
        InventoryRequest inventoryRequest = buildInventoryRequest(OrderInventoryStatus.UPDATING);
        String logMessage="Inventory updating process step started with InventoryRequest";

        //when
        //then
        assertDoesNotThrow(() -> inventoryUpdatingSagaStep.process(inventoryRequest));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_rollback(){
        //given
        InventoryRequest inventoryRequest = buildInventoryRequest(OrderInventoryStatus.UPDATING_ROLLBACK);
        String logMessage="Inventory updating rollback step started with InventoryRequest";

        //when
        //then
        assertDoesNotThrow(() -> inventoryUpdatingSagaStep.rollback(inventoryRequest));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    private InventoryRequest buildInventoryRequest(OrderInventoryStatus orderInventoryStatus) {
        return new InventoryRequest(UUID.randomUUID(), 1L, 1L, new Money(BigDecimal.TEN), orderInventoryStatus, Collections.emptyList());
    }
}
