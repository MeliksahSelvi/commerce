package com.commerce.order.service.order.handler;

import com.commerce.order.service.common.exception.OrderNotFoundException;
import com.commerce.order.service.order.handler.adapter.FakeOrderDataAdapter;
import com.commerce.order.service.order.usecase.TrackOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class TrackOrderUseCaseHandlerTest {

    TrackOrderUseCaseHandler trackOrderUseCaseHandler;

    @BeforeEach
    void setUp() {
        trackOrderUseCaseHandler = new TrackOrderUseCaseHandler(new FakeOrderDataAdapter());
    }

    @Test
    void should_track_success() {
        //given
        var trackOrder = new TrackOrder(1L);

        //when
        var order = trackOrderUseCaseHandler.handle(trackOrder);

        //then
        assertEquals(trackOrder.orderId(), order.getId());
        assertNotNull(order);
    }

    @Test
    void should_track_fail() {
        //given
        var trackOrder = new TrackOrder(2L);

        //when
        //then
        OrderNotFoundException orderNotFoundException = assertThrows(OrderNotFoundException.class, () -> trackOrderUseCaseHandler.handle(trackOrder));
        assertTrue(orderNotFoundException.getMessage().contains("Could not find order with"));
    }
}
