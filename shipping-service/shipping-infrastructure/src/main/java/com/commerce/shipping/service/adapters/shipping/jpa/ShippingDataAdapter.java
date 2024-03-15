package com.commerce.shipping.service.adapters.shipping.jpa;

import com.commerce.shipping.service.adapters.shipping.jpa.entity.AddressEntity;
import com.commerce.shipping.service.adapters.shipping.jpa.entity.OrderItemEntity;
import com.commerce.shipping.service.adapters.shipping.jpa.entity.ShippingEntity;
import com.commerce.shipping.service.adapters.shipping.jpa.repository.ShippingEntityRepository;
import com.commerce.shipping.service.common.valueobject.DeliveryStatus;
import com.commerce.shipping.service.shipping.entity.OrderItem;
import com.commerce.shipping.service.shipping.entity.Shipping;
import com.commerce.shipping.service.shipping.port.jpa.ShippingDataPort;
import com.commerce.shipping.service.shipping.usecase.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

@Service
public class ShippingDataAdapter implements ShippingDataPort {

    private static final Logger logger = LoggerFactory.getLogger(ShippingDataAdapter.class);
    private final ShippingEntityRepository shippingEntityRepository;

    public ShippingDataAdapter(ShippingEntityRepository shippingEntityRepository) {
        this.shippingEntityRepository = shippingEntityRepository;
    }

    @Override
    public Shipping save(Shipping shipping) {
        var shippingEntity = new ShippingEntity();
        shippingEntity.setId(shipping.getId());
        shippingEntity.setOrderId(shipping.getOrderId());
        shippingEntity.setCustomerId(shipping.getCustomerId());
        shippingEntity.setAddress(buildAddressEntity(shipping.getAddress()));
        shippingEntity.setDeliveryStatus(shipping.getDeliveryStatus());
        shippingEntity.setItems(shipping.getItems().stream().map(this::buildOrderItemEntity).toList());
        shippingEntity.getAddress().setShipping(shippingEntity);
        shippingEntity.getItems().forEach(orderItemEntity -> {
            orderItemEntity.setShipping(shippingEntity);
            orderItemEntity.setOrderId(shippingEntity.getOrderId());
        });
        //todo debug for assocation between entities correctly created
        return shippingEntityRepository.save(shippingEntity).toModel();
    }

    public AddressEntity buildAddressEntity(Address address) {
        var addressEntity = new AddressEntity();
        addressEntity.setCity(address.city());
        addressEntity.setCounty(address.county());
        addressEntity.setNeighborhood(address.neighborhood());
        addressEntity.setStreet(address.street());
        addressEntity.setPostalCode(address.postalCode());
        return addressEntity;
    }

    public OrderItemEntity buildOrderItemEntity(OrderItem orderItem) {
        var orderItemEntity = new OrderItemEntity();
        orderItemEntity.setOrderId(orderItem.getOrderId());
        orderItemEntity.setProductId(orderItem.getProductId());
        orderItemEntity.setQuantity(orderItem.getQuantity().value());
        orderItemEntity.setPrice(orderItem.getPrice().amount());
        orderItemEntity.setTotalPrice(orderItem.getTotalPrice().amount());
        return orderItemEntity;
    }

    @Override
    public Optional<Shipping> findByOrderIdAndDeliveryStatus(Long orderId, DeliveryStatus deliveryStatus) {
        return shippingEntityRepository.findByOrderIdAndDeliveryStatus(orderId, deliveryStatus).map(ShippingEntity::toModel);
    }
}
