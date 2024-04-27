package com.commerce.order.query.service.order.handler;

import com.commerce.order.query.service.common.exception.OrderNotFoundException;
import com.commerce.order.query.service.order.adapters.messaging.adapter.FakeOrderQueryDataAdapter;
import com.commerce.order.query.service.order.usecase.TrackOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

class TrackOrderUseCaseHandlerTest {

    TrackOrderUseCaseHandler trackOrderUseCaseHandler;

    @BeforeEach
    void setUp() {
        trackOrderUseCaseHandler = new TrackOrderUseCaseHandler(new FakeOrderQueryDataAdapter());
    }

    @Test
    void should_track_success() {
        //given
        var trackOrder = new TrackOrder(1L);

        //when
        var order = trackOrderUseCaseHandler.handle(trackOrder);

        //then
        assertEquals(trackOrder.orderId(), order.id());
        assertNotNull(order);
    }

    @Test
    void should_track_fail() {
        //given
        var trackOrder = new TrackOrder(7L);
        var errorMessage="Could not find order with id: 7";

        //when
        //then
        var orderNotFoundException = assertThrows(OrderNotFoundException.class, () -> trackOrderUseCaseHandler.handle(trackOrder));
        assertTrue(orderNotFoundException.getMessage().contains(errorMessage));
    }
}
