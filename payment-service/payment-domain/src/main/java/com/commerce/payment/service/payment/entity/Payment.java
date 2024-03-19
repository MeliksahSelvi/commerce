package com.commerce.payment.service.payment.entity;

import com.commerce.payment.service.common.model.BaseEntity;
import com.commerce.payment.service.common.valueobject.Money;
import com.commerce.payment.service.common.valueobject.PaymentStatus;

import java.util.List;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public class Payment extends BaseEntity {

    private final Long orderId;
    private final Long customerId;
    private final Money cost;
    private PaymentStatus paymentStatus;

    public void validatePayment(List<String> failureMessages) {
        if (cost == null || !cost.isGreaterThanZero()) {
            failureMessages.add("Cost must be greater than zero!");
        }
    }

    public void cancelPayment(List<String> failureMessages) {
        paymentStatus = failureMessages.isEmpty() ? PaymentStatus.CANCELLED : PaymentStatus.FAILED;
    }

    public void initializeStatus(List<String> failureMessages) {
        paymentStatus = failureMessages.isEmpty() ? PaymentStatus.COMPLETED : PaymentStatus.FAILED;
    }

    private Payment(Builder builder) {
        setId(builder.id);
        this.orderId = builder.orderId;
        this.customerId = builder.customerId;
        this.cost = builder.cost;
        this.paymentStatus = builder.paymentstatus;
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

    public Money getCost() {
        return cost;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public static final class Builder {
        private Long id;
        private Long orderId;
        private Long customerId;
        private Money cost;
        private PaymentStatus paymentstatus;

        private Builder() {
        }

        public Builder id(Long val) {
            this.id = val;
            return this;
        }

        public Builder orderId(Long val) {
            this.orderId = val;
            return this;
        }

        public Builder customerId(Long val) {
            this.customerId = val;
            return this;
        }

        public Builder cost(Money val) {
            this.cost = val;
            return this;
        }

        public Builder paymentstatus(PaymentStatus val) {
            this.paymentstatus = val;
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }
}
