package com.commerce.inventory.service.saga;

import com.commerce.inventory.service.adapter.FakeJsonAdapter;
import com.commerce.inventory.service.adapter.FakeOrderOutboxDataAdapter;
import com.commerce.inventory.service.adapter.FakeProductCacheAdapter;
import com.commerce.inventory.service.adapter.FakeProductDataAdapter;
import com.commerce.inventory.service.common.valueobject.Money;
import com.commerce.inventory.service.common.valueobject.OrderInventoryStatus;
import com.commerce.inventory.service.common.valueobject.Quantity;
import com.commerce.inventory.service.inventory.usecase.InventoryRequest;
import com.commerce.inventory.service.inventory.usecase.OrderItem;
import com.commerce.inventory.service.saga.helper.InventoryCheckingHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class InventoryCheckingHelperTest {

    InventoryCheckingHelper inventoryCheckingHelper;

    @BeforeEach
    void setUp() {
        inventoryCheckingHelper = new InventoryCheckingHelper(new FakeOrderOutboxDataAdapter(),
                new FakeProductCacheAdapter(), new FakeProductDataAdapter(), new FakeJsonAdapter());
    }

    @Test
    void should_process() {
        //given
        var inventoryRequest = buildInventoryRequest(1L, 1L);

        //when
        var failureMessages = inventoryCheckingHelper.process(inventoryRequest);

        //then
        assertTrue(failureMessages.isEmpty());
    }

    @Test
    void should_process_fail_when_product_not_exist() {
        //given
        var inventoryRequest = buildInventoryRequest(1L, 2L);
        var errorMessage = String.format("Product could not find by productId: %d", 2L);

        //when
        var failureMessages = inventoryCheckingHelper.process(inventoryRequest);

        //then
        assertTrue(failureMessagesIncludeThatMessage(failureMessages, errorMessage));
    }

    @Test
    void should_process_fail_when_product_not_available() {
        //given
        var inventoryRequest = buildInventoryRequest(1L, 3L);
        var errorMessage = String.format("Product not available situation by productId: %d", 3L);

        //when
        var failureMessages = inventoryCheckingHelper.process(inventoryRequest);

        //then
        assertTrue(failureMessagesIncludeThatMessage(failureMessages, errorMessage));
    }

    @Test
    void should_process_fail_when_product_price_not_equal_to_order_item_price() {
        //given
        var inventoryRequest = buildInventoryRequest(1L, 4L);
        var errorMessage = String.format("Product price not equal to price that you gave by productId: %d", 4L);

        //when
        var failureMessages = inventoryCheckingHelper.process(inventoryRequest);

        //then
        assertTrue(failureMessagesIncludeThatMessage(failureMessages, errorMessage));
    }
    @Test
    void should_process_fail_when_product_quantity_not_enough() {
        //given
        var inventoryRequest = buildInventoryRequest(1L, 5L);
        var errorMessage = String.format("Product quantity not enough for your wanting count by product Id %d", 5L);

        //when
        var failureMessages = inventoryCheckingHelper.process(inventoryRequest);

        //then
        assertTrue(failureMessagesIncludeThatMessage(failureMessages, errorMessage));
    }

    @Test
    void should_rollback() {
        //given
        var inventoryRequest = buildInventoryRequest(1L, 1L);

        //when
        var failureMessages = inventoryCheckingHelper.rollback(inventoryRequest);

        //then
        assertTrue(failureMessages.isEmpty());
    }

    @Test
    void should_rollback_fail_when_product_not_exist() {
        //given
        var inventoryRequest = buildInventoryRequest(1L, 2L);
        var errorMessage = String.format("Product could not be found in cache by product id: %d and order id: %d", 2L, inventoryRequest.orderId());

        //when
        var failureMessages = inventoryCheckingHelper.rollback(inventoryRequest);

        //then
        assertTrue(failureMessagesIncludeThatMessage(failureMessages, errorMessage));
    }

    private InventoryRequest buildInventoryRequest(Long orderId, Long productId) {
        return new InventoryRequest(UUID.randomUUID(), orderId, 1L, new Money(BigDecimal.valueOf(17)), OrderInventoryStatus.CHECKING,
                List.of(
                        new OrderItem(1L, orderId, productId, new Quantity(10), new Money(BigDecimal.ONE), new Money(BigDecimal.TEN))
                ));
    }

    private boolean failureMessagesIncludeThatMessage(List<String> failureMessages, String thatMessage) {
        return failureMessages.stream().anyMatch(failureMessage -> failureMessage.contains(thatMessage));
    }
}
