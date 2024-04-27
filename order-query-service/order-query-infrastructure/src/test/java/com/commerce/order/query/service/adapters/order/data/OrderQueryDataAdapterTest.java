package com.commerce.order.query.service.adapters.order.data;

import com.commerce.order.query.service.adapters.order.data.document.OrderQueryIndexModel;
import com.commerce.order.query.service.adapters.order.data.repository.OrderQueryDocumentRepository;
import com.commerce.order.query.service.common.valueobject.OrderStatus;
import com.commerce.order.query.service.order.model.OrderQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

@ExtendWith(MockitoExtension.class)
class OrderQueryDataAdapterTest {

    @InjectMocks
    private OrderQueryDataAdapter orderQueryDataAdapter;

    @Mock
    private OrderQueryDocumentRepository repository;

    @Test
    void should_save() {
        //given
        var orderQuery = buildOrderQuery();
        var queryIndexModel = mock(OrderQueryIndexModel.class);
        when(repository.save(any())).thenReturn(queryIndexModel);
        when(queryIndexModel.toModel()).thenReturn(orderQuery);

        //when
        var savedOrderQuery = orderQueryDataAdapter.save(orderQuery);

        //then
        assertEquals(savedOrderQuery.id(), orderQuery.id());
        assertEquals(savedOrderQuery.orderStatus(), orderQuery.orderStatus());
    }


    @Test
    void should_findById() {
        //given
        var orderQuery = buildOrderQuery();
        Long orderId = orderQuery.id();
        var orderEntity = mock(OrderQueryIndexModel.class);
        when(repository.findById(orderId.toString())).thenReturn(Optional.of(orderEntity));
        when(orderEntity.toModel()).thenReturn(orderQuery);

        //when
        var orderQueryOptional = orderQueryDataAdapter.findById(orderId);


        //then
        assertTrue(orderQueryOptional.isPresent());
        assertEquals(orderId,orderQueryOptional.get().id());
    }

    @Test
    void should_findById_empty() {
        //given
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        //when
        var orderQueryOptional = orderQueryDataAdapter.findById(anyLong());

        //then
        assertTrue(orderQueryOptional.isEmpty());
    }

    private OrderQuery buildOrderQuery() {
        return new OrderQuery(1L, OrderStatus.PENDING);
    }
}
