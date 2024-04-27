package com.commerce.shipping.service.adapters.shipping.jpa;

import com.commerce.shipping.service.adapters.shipping.jpa.entity.AddressEntity;
import com.commerce.shipping.service.adapters.shipping.jpa.entity.OrderItemEntity;
import com.commerce.shipping.service.adapters.shipping.jpa.entity.ShippingEntity;
import com.commerce.shipping.service.adapters.shipping.jpa.repository.ShippingEntityRepository;
import com.commerce.shipping.service.common.valueobject.DeliveryStatus;
import com.commerce.shipping.service.shipping.model.Address;
import com.commerce.shipping.service.shipping.model.OrderItem;
import com.commerce.shipping.service.shipping.model.Shipping;
import com.commerce.shipping.service.shipping.port.jpa.ShippingDataPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

@Service
public class ShippingDataAdapter implements ShippingDataPort {

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
        shippingEntity.getAddress().setOrderId(shipping.getOrderId());
        shippingEntity.getItems().forEach(orderItemEntity -> {
            orderItemEntity.setShipping(shippingEntity);
            orderItemEntity.setOrderId(shippingEntity.getOrderId());
        });
        return shippingEntityRepository.save(shippingEntity).toModel();
    }

    private AddressEntity buildAddressEntity(Address address) {
        var addressEntity = new AddressEntity();
        addressEntity.setId(address.getId());
        addressEntity.setCity(address.getCity());
        addressEntity.setCounty(address.getCounty());
        addressEntity.setNeighborhood(address.getNeighborhood());
        addressEntity.setStreet(address.getStreet());
        addressEntity.setPostalCode(address.getPostalCode());
        return addressEntity;
    }

    private OrderItemEntity buildOrderItemEntity(OrderItem orderItem) {
        var orderItemEntity = new OrderItemEntity();
        orderItemEntity.setId(orderItem.getId());
        orderItemEntity.setOrderId(orderItem.getOrderId());
        orderItemEntity.setProductId(orderItem.getProductId());
        orderItemEntity.setQuantity(orderItem.getQuantity().value());
        orderItemEntity.setPrice(orderItem.getPrice().amount());
        orderItemEntity.setTotalPrice(orderItem.getTotalPrice().amount());
        return orderItemEntity;
    }

    @Override
    public Optional<Shipping> findByOrderId(Long orderId) {
        return shippingEntityRepository.findByOrderId(orderId).map(ShippingEntity::toModel);
    }

    @Override
    public Optional<Shipping> findByOrderIdAndDeliveryStatus(Long orderId, DeliveryStatus deliveryStatus) {
        return shippingEntityRepository.findByOrderIdAndDeliveryStatus(orderId, deliveryStatus).map(ShippingEntity::toModel);
    }
}
