package com.commerce.order.service.order.usecase;

import com.commerce.order.service.common.model.UseCase;
import com.commerce.order.service.order.model.OrderItem;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 15.03.2024
 */

public class OrderItemPayload implements UseCase, Serializable {
    private Long id;
    private Long orderId;
    private Long productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;

    public OrderItemPayload() {
    }

    public OrderItemPayload(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.orderId = orderItem.getOrderId();
        this.productId = orderItem.getProductId();
        this.quantity = orderItem.getQuantity().value();
        this.price = orderItem.getPrice().amount();
        this.totalPrice = orderItem.getTotalPrice().amount();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
