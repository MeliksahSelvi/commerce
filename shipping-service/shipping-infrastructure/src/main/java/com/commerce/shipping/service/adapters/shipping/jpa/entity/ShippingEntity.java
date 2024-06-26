package com.commerce.shipping.service.adapters.shipping.jpa.entity;

import com.commerce.shipping.service.common.model.BaseEntity;
import com.commerce.shipping.service.common.valueobject.DeliveryStatus;
import com.commerce.shipping.service.shipping.model.Shipping;
import jakarta.persistence.*;

import java.util.List;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

@Entity
@Table(name = "SHIPPING")
public class ShippingEntity extends BaseEntity {

    @Column(name = "ORDER_ID",nullable = false)
    private Long orderId;

    @Column(name = "CUSTOMER_ID",nullable = false)
    private Long customerId;

    @OneToOne(mappedBy = "shipping", cascade = CascadeType.ALL)
    private AddressEntity address;

    @OneToMany(mappedBy = "shipping", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<OrderItemEntity> items;

    @Column(name = "DELIVERY_STATUS",nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    public Shipping toModel(){
        return Shipping.builder()
                .id(getId())
                .orderId(orderId)
                .customerId(customerId)
                .address(address.toModel())
                .deliveryStatus(deliveryStatus)
                .items(items.stream().map(OrderItemEntity::toModel).toList())
                .build();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public List<OrderItemEntity> getItems() {
        return items;
    }

    public void setItems(List<OrderItemEntity> items) {
        this.items = items;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
