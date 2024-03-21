package com.commerce.shipping.service.adapters.shipping.jpa;

import com.commerce.shipping.service.adapters.shipping.jpa.entity.ShippingEntity;
import com.commerce.shipping.service.adapters.shipping.jpa.repository.ShippingEntityRepository;
import com.commerce.shipping.service.common.valueobject.DeliveryStatus;
import com.commerce.shipping.service.common.valueobject.Money;
import com.commerce.shipping.service.common.valueobject.Quantity;
import com.commerce.shipping.service.shipping.entity.Address;
import com.commerce.shipping.service.shipping.entity.OrderItem;
import com.commerce.shipping.service.shipping.entity.Shipping;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */


@ExtendWith(MockitoExtension.class)
class ShippingDataAdapterTest {

    @InjectMocks
    private ShippingDataAdapter adapter;

    @Mock
    private ShippingEntityRepository repository;

    @Test
    void should_save() {
        //given
        var shipping=buildShipping();
        ShippingEntity shippingEntity = mock(ShippingEntity.class);
        when(shippingEntity.toModel()).thenReturn(shipping);
        when(repository.save(any())).thenReturn(shippingEntity);

        //when
        Shipping savedShipping = adapter.save(shipping);

        //then
        assertNotNull(savedShipping);
        assertEquals(savedShipping.getId(),shipping.getId());
        assertEquals(savedShipping.getCustomerId(),shipping.getCustomerId());
        assertEquals(savedShipping.getOrderId(),shipping.getOrderId());
        assertEquals(savedShipping.getItems(),shipping.getItems());
        assertEquals(savedShipping.getAddress(),shipping.getAddress());
        assertEquals(savedShipping.getDeliveryStatus(),shipping.getDeliveryStatus());
    }

    @Test
    void should_findByOrderId() {
        //given
        var shipping = buildShipping();
        var shippingEntity = mock(ShippingEntity.class);
        when(repository.findByOrderId(any())).thenReturn(Optional.of(shippingEntity));
        when(shippingEntity.toModel()).thenReturn(shipping);

        //when
        var shippingOptional = adapter.findByOrderId(shipping.getOrderId());

        //then
        assertTrue(shippingOptional.isPresent());
        assertEquals(shipping.getOrderId(), shippingOptional.get().getOrderId());
    }

    @Test
    void should_findByOrderId_empty() {
        //given
        when(repository.findByOrderId(any())).thenReturn(Optional.empty());

        //when
        var shippingOptional = adapter.findByOrderId(any());

        //then
        assertTrue(shippingOptional.isEmpty());
    }

    @Test
    void should_findByOrderIdAndDeliveryStatus() {
        //given
        var shipping = buildShipping();
        var shippingEntity = mock(ShippingEntity.class);
        when(repository.findByOrderIdAndDeliveryStatus(any(),any())).thenReturn(Optional.of(shippingEntity));
        when(shippingEntity.toModel()).thenReturn(shipping);

        //when
        var shippingOptional = adapter.findByOrderIdAndDeliveryStatus(shipping.getOrderId(),shipping.getDeliveryStatus());

        //then
        assertTrue(shippingOptional.isPresent());
        assertEquals(shipping.getOrderId(), shippingOptional.get().getOrderId());
        assertEquals(shipping.getDeliveryStatus(), shippingOptional.get().getDeliveryStatus());
    }

    @Test
    void should_findByOrderIdAndDeliveryStatus_empty() {
        //given
        when(repository.findByOrderIdAndDeliveryStatus(any(),any())).thenReturn(Optional.empty());

        //when
        var shippingOptional = adapter.findByOrderIdAndDeliveryStatus(1L,DeliveryStatus.APPROVED);

        //then
        assertTrue(shippingOptional.isEmpty());
    }

    private Shipping buildShipping() {
        return Shipping.builder()
                .id(1L)
                .orderId(1L)
                .customerId(1L)
                .address(
                        Address.builder()
                                .id(1L)
                                .city("city")
                                .county("county")
                                .neighborhood("neigborhood")
                                .street("street")
                                .postalCode("postalCode")
                                .build()
                )
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
                .deliveryStatus(DeliveryStatus.APPROVED)
                .build();
    }
}
