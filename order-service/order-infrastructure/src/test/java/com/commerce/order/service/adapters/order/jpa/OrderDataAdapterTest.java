package com.commerce.order.service.adapters.order.jpa;

import com.commerce.order.service.adapters.order.jpa.entity.OrderEntity;
import com.commerce.order.service.adapters.order.jpa.repository.OrderEntityRepository;
import com.commerce.order.service.common.valueobject.Money;
import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.common.valueobject.Quantity;
import com.commerce.order.service.order.entity.Address;
import com.commerce.order.service.order.entity.Order;
import com.commerce.order.service.order.entity.OrderItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

@ExtendWith(MockitoExtension.class)
class OrderDataAdapterTest {

    @InjectMocks
    private OrderDataAdapter orderDataAdapter;

    @Mock
    private OrderEntityRepository repository;

    @Test
    void should_save() {
        //given
        var order = buildOrder();
        OrderEntity orderEntity = mock(OrderEntity.class);
        when(repository.save(any())).thenReturn(orderEntity);
        when(orderEntity.toModel()).thenReturn(order);

        //when
        Order savedOrder = orderDataAdapter.save(order);

        //then
        assertEquals(savedOrder.getId(), order.getId());
        assertEquals(savedOrder.getCustomerId(), order.getCustomerId());
        assertEquals(savedOrder.getOrderStatus(), order.getOrderStatus());
        assertEquals(savedOrder.getCost(), order.getCost());
        assertEquals(savedOrder.getDeliveryAddress(), order.getDeliveryAddress());
        assertEquals(savedOrder.getItems().size(), order.getItems().size());
    }


    @Test
    void should_findById() {
        //given
        Order order = buildOrder();
        Long orderId = order.getId();
        OrderEntity orderEntity = mock(OrderEntity.class);
        when(repository.findById(orderId)).thenReturn(Optional.of(orderEntity));
        when(orderEntity.toModel()).thenReturn(order);

        //when
        Optional<Order> orderOptional = orderDataAdapter.findById(orderId);


        //then
        assertTrue(orderOptional.isPresent());
        assertEquals(orderId,orderOptional.get().getId());
    }

    @Test
    void should_findById_empty() {
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        Optional<Order> orderOptional = orderDataAdapter.findById(anyLong());

        //then
        assertTrue(orderOptional.isEmpty());
    }

    private Order buildOrder() {
        return Order.builder()
                .id(1L)
                .customerId(1L)
                .orderStatus(OrderStatus.APPROVED)
                .deliveryAddress(
                        Address.builder()
                                .id(1L)
                                .city("city")
                                .county("county")
                                .neighborhood("neigborhood")
                                .street("street")
                                .postalCode("postalCode")
                                .build()
                )
                .cost(new Money(BigDecimal.valueOf(100.00)))
                .items(List.of(
                        OrderItem.builder()
                                .id(1L)
                                .orderId(1L)
                                .productId(1L)
                                .quantity(new Quantity(5))
                                .price(new Money(BigDecimal.valueOf(20.00)))
                                .totalPrice(new Money(BigDecimal.valueOf(100.00)))
                                .build()
                ))
                .failureMessages(Collections.EMPTY_LIST)
                .build();
    }
}
