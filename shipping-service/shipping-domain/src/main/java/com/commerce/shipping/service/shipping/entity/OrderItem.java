package com.commerce.shipping.service.shipping.entity;

import com.commerce.shipping.service.common.model.BaseEntity;
import com.commerce.shipping.service.common.valueobject.Money;
import com.commerce.shipping.service.common.valueobject.Quantity;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

public class OrderItem extends BaseEntity {

    private Long shippingId;
    private final Long orderId;
    private final Long productId;
    private final Quantity quantity;
    private final Money price;
    private final Money totalPrice;

    public void initializeOrderItem(Long shippingId) {
        this.shippingId = shippingId;
    }

    private OrderItem(Builder builder) {
        this.setId(builder.id);
        this.shippingId=builder.shippingId;
        this.orderId = builder.orderId;
        this.productId = builder.productId;
        this.quantity = builder.quantity;
        this.price = builder.price;
        this.totalPrice = builder.totalPrice;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getShippingId() {
        return shippingId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Money getPrice() {
        return price;
    }

    public Money getTotalPrice() {
        return totalPrice;
    }

    public static final class Builder {
        private Long id;
        private Long shippingId;
        private Long orderId;
        private Long productId;
        private Quantity quantity;
        private Money price;
        private Money totalPrice;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder shippingId(Long val) {
            shippingId = val;
            return this;
        }

        public Builder orderId(Long val) {
            orderId = val;
            return this;
        }

        public Builder productId(Long val) {
            productId = val;
            return this;
        }

        public Builder quantity(Quantity val) {
            quantity = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder totalPrice(Money val) {
            totalPrice = val;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
