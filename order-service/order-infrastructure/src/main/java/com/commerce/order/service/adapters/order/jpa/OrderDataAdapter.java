package com.commerce.order.service.adapters.order.jpa;

import com.commerce.order.service.adapters.order.jpa.entity.AddressEntity;
import com.commerce.order.service.adapters.order.jpa.entity.OrderEntity;
import com.commerce.order.service.adapters.order.jpa.entity.OrderItemEntity;
import com.commerce.order.service.adapters.order.jpa.repository.OrderEntityRepository;
import com.commerce.order.service.common.valueobject.Address;
import com.commerce.order.service.order.entity.Order;
import com.commerce.order.service.order.entity.OrderItem;
import com.commerce.order.service.order.port.jpa.OrderDataPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 05.03.2024
 */

@Service
public class OrderDataAdapter implements OrderDataPort {

    private static final Logger logger = LoggerFactory.getLogger(OrderDataAdapter.class);
    private final OrderEntityRepository orderEntityRepository;

    public OrderDataAdapter(OrderEntityRepository orderEntityRepository) {
        this.orderEntityRepository = orderEntityRepository;
    }

    @Override
    public Order save(Order order) {
        var orderEntity = new OrderEntity();
        orderEntity.setCustomerId(order.getCustomerId());
        orderEntity.setAddress(buildAddressEntity(order.getDeliveryAddress()));
        orderEntity.setOrderStatus(order.getOrderStatus());
        orderEntity.setCost(order.getCost().amount());
        orderEntity.setItems(order.getItems().stream().map(this::buildOrderItemEntity).toList());
        orderEntity.setFailureMessages(order.getFailureMessages()!=null ?String.join(",", order.getFailureMessages()):"");
        orderEntity.getAddress().setOrder(orderEntity);
        orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));
        return orderEntityRepository.save(orderEntity).toModel();
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
        orderItemEntity.setProductId(orderItem.getProductId());
        orderItemEntity.setQuantity(orderItem.getQuantity().value());
        orderItemEntity.setPrice(orderItem.getPrice().amount());
        orderItemEntity.setTotalPrice(orderItem.getTotalPrice().amount());
        return orderItemEntity;
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        return orderEntityRepository.findById(orderId).map(OrderEntity::toModel);
    }
}
