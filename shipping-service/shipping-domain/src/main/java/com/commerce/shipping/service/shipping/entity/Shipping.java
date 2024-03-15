package com.commerce.shipping.service.shipping.entity;

import com.commerce.shipping.service.common.model.BaseEntity;
import com.commerce.shipping.service.common.valueobject.DeliveryStatus;
import com.commerce.shipping.service.shipping.usecase.Address;

import java.util.List;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

public class Shipping extends BaseEntity {

    private Long orderId;
    private final Long customerId;
    private final Address address;
    private final List<OrderItem> items;
    private DeliveryStatus deliveryStatus;

    public void initializeShipping(){
        deliveryStatus=DeliveryStatus.APPROVED;
        associateShippingToOrderItems();
    }

    private void associateShippingToOrderItems() {
        for (OrderItem orderItem : items) {
            orderItem.initializeOrderItem(getId());
        }
    }

    private Shipping(Builder builder) {
        this.setId(builder.id);
        this.orderId = builder.orderId;
        this.customerId = builder.customerId;
        this.address = builder.address;
        this.items = builder.items;
        this.deliveryStatus=builder.deliveryStatus;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Address getAddress() {
        return address;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public static class Builder {
        private Long id;
        private Long orderId;
        private Long customerId;
        private Address address;
        private List<OrderItem> items;
        private DeliveryStatus deliveryStatus;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder orderId(Long val) {
            orderId = val;
            return this;
        }

        public Builder customerId(Long val) {
            customerId = val;
            return this;
        }

        public Builder address(Address val) {
            address = val;
            return this;
        }

        public Builder items(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder deliveryStatus(DeliveryStatus val) {
            deliveryStatus = val;
            return this;
        }

        public Shipping build() {
            return new Shipping(this);
        }
    }
}
