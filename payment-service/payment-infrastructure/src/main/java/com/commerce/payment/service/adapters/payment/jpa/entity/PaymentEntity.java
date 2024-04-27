package com.commerce.payment.service.adapters.payment.jpa.entity;

import com.commerce.payment.service.common.model.BaseEntity;
import com.commerce.payment.service.common.valueobject.Money;
import com.commerce.payment.service.common.valueobject.PaymentStatus;
import com.commerce.payment.service.payment.model.Payment;
import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Entity
@Table(name = "PAYMENT")
public class PaymentEntity extends BaseEntity {

    @Column(name = "ORDER_ID",nullable = false)
    private Long orderId;

    @Column(name = "CUSTOMER_ID",nullable = false)
    private Long customerId;

    @Column(name = "COST",nullable = false, precision = 15, scale = 2)
    private BigDecimal cost;

    @Column(name = "PAYMENT_STATUS",nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    public Payment toModel() {
        return Payment.builder()
                .id(getId())
                .orderId(orderId)
                .customerId(customerId)
                .cost(new Money(cost))
                .paymentstatus(paymentStatus)
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

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
