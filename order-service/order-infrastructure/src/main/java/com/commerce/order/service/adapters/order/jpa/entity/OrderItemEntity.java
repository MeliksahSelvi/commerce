package com.commerce.order.service.adapters.order.jpa.entity;

import com.commerce.order.service.common.model.AbstractEntity;
import com.commerce.order.service.common.valueobject.Money;
import com.commerce.order.service.common.valueobject.Quantity;
import com.commerce.order.service.order.entity.OrderItem;
import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 05.03.2024
 */

@Entity
@Table(name = "ORDER_ITEM")
public class OrderItemEntity extends AbstractEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID",nullable = false)
    private OrderEntity order;

    @Column(name = "PRODUCT_ID",nullable = false)
    private Long productId;

    @Column(name = "QUANTITY",nullable = false)
    private int quantity;

    @Column(name = "PRICE",nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Column(name = "TOTAL_PRICE",nullable = false, precision = 15, scale = 2)
    private BigDecimal totalPrice;

    public OrderItem toModel() {
        return OrderItem.builder()
                .id(getId())
                .orderId(order.getId())
                .productId(productId)
                .quantity(new Quantity(quantity))
                .price(new Money(price))
                .totalPrice(new Money(totalPrice))
                .build();
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
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
