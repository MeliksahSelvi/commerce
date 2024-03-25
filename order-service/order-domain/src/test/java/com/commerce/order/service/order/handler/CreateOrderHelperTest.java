package com.commerce.order.service.order.handler;

import com.commerce.order.service.adapter.FakeInnerRestAdapter;
import com.commerce.order.service.adapter.FakeJsonAdapter;
import com.commerce.order.service.adapter.helper.FakeSagaHelper;
import com.commerce.order.service.adapter.order.FakeOrderDataAdapter;
import com.commerce.order.service.adapter.outbox.FakeInventoryOutboxDataAdapter;
import com.commerce.order.service.common.exception.OrderDomainException;
import com.commerce.order.service.order.handler.common.CreateOrderCommonData;
import com.commerce.order.service.order.handler.helper.CreateOrderHelper;
import com.commerce.order.service.order.usecase.CreateOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class CreateOrderHelperTest {

    CreateOrderHelper createOrderHelper;
    CreateOrderCommonData createOrderCommonData;

    @BeforeEach
    void setUp() {
        createOrderHelper = new CreateOrderHelper(new FakeInventoryOutboxDataAdapter(), new FakeOrderDataAdapter(),
                new FakeSagaHelper(), new FakeJsonAdapter(), new FakeInnerRestAdapter());

        createOrderCommonData = new CreateOrderCommonData();
    }

    @Test
    void should_create() {
        //given
        var createOrder = new CreateOrder(1L, createOrderCommonData.correctCost(),
                createOrderCommonData.buildOrderItemsCorrect(), createOrderCommonData.buildAddress());

        //when
        var order = createOrderHelper.createOrder(createOrder);

        //then
        assertEquals(createOrder.customerId(), order.getCustomerId());
        assertEquals(createOrder.cost(), order.getCost());
        assertTrue(createOrderCommonData.addressEqualToAnother(createOrder.deliveryAddress(), order.getDeliveryAddress()));
        assertTrue(createOrderCommonData.orderItemsEqualToAnother(createOrder.items(), order.getItems()));
        assertNull(order.getId());
    }

    @Test
    void should_create_fail_when_cost_is_null() {
        //given
        var createOrder = new CreateOrder(1L, null,
                createOrderCommonData.buildOrderItemsCorrect(), createOrderCommonData.buildAddress());

        //when
        //then
        var orderDomainException = assertThrows(OrderDomainException.class, () -> createOrderHelper.createOrder(createOrder));
        assertEquals("Cost can't be zero and must be greater than zero!", orderDomainException.getMessage());
    }

    @Test
    void should_create_fail_when_cost_is_not_greater_than_zero() {
        //given
        var createOrder = new CreateOrder(1L, createOrderCommonData.costNotGreaterThanZero(),
                createOrderCommonData.buildOrderItemsCorrect(), createOrderCommonData.buildAddress());

        //when
        //then
        var orderDomainException = assertThrows(OrderDomainException.class, () -> createOrderHelper.createOrder(createOrder));
        assertEquals("Cost can't be zero and must be greater than zero!", orderDomainException.getMessage());
    }

    @Test
    void should_create_fail_when_order_item_price_not_greater_than_zero() {
        //given
        var createOrder = new CreateOrder(1L, createOrderCommonData.correctCost(),
                createOrderCommonData.buildOrderItemsWrongWithPriceNotGreaterThanZero(), createOrderCommonData.buildAddress());

        //when
        //then
        var orderDomainException = assertThrows(OrderDomainException.class, () -> createOrderHelper.createOrder(createOrder));
        assertTrue(orderDomainException.getMessage().contains("is not valid for product"));
    }

    @Test
    void should_create_fail_when_order_item_total_price_not_equal_to_multiply_price() {
        //given
        var createOrder = new CreateOrder(1L, createOrderCommonData.correctCost(),
                createOrderCommonData.buildOrderItemsWrongWithTotalPriceNotEqualToMultiplyPrice(), createOrderCommonData.buildAddress());

        //when
        //then
        var orderDomainException = assertThrows(OrderDomainException.class, () -> createOrderHelper.createOrder(createOrder));
        assertTrue(orderDomainException.getMessage().contains("is not valid for product"));
    }

    @Test
    void should_create_fail_when_cost_not_equal_to_items_totalcost() {
        //given
        var createOrder = new CreateOrder(1L, createOrderCommonData.wrongCost(),
                createOrderCommonData.buildOrderItemsCorrect(), createOrderCommonData.buildAddress());

        //when
        //then
        var orderDomainException = assertThrows(OrderDomainException.class, () -> createOrderHelper.createOrder(createOrder));
        assertTrue(orderDomainException.getMessage().contains("not equal Order Items total"));
    }

    @Test
    void should_create_fail_when_customer_not_exist() {
        //given
        var createOrder = new CreateOrder(2L, createOrderCommonData.correctCost(),
                createOrderCommonData.buildOrderItemsCorrect(), createOrderCommonData.buildAddress());

        //when
        //then
        var orderDomainException = assertThrows(OrderDomainException.class, () -> createOrderHelper.createOrder(createOrder));
        assertTrue(orderDomainException.getMessage().contains("Could not find customer"));
    }

}
