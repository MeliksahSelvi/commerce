package com.commerce.payment.service.outbox.entity;

import com.commerce.payment.service.common.model.BaseEntity;
import com.commerce.payment.service.common.outbox.OutboxStatus;
import com.commerce.payment.service.common.valueobject.PaymentStatus;

import java.util.UUID;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public class OrderOutbox extends BaseEntity {

    private final UUID sagaId;
    private final String payload;
    private PaymentStatus paymentStatus;
    private OutboxStatus outboxStatus;

    private OrderOutbox(Builder builder) {
        this.setId(builder.id);
        this.sagaId = builder.sagaId;
        this.payload = builder.payload;
        this.paymentStatus=builder.paymentStatus;
        this.outboxStatus = builder.outboxStatus;
    }

    public static Builder builder() {
        return new Builder();
    }

    public UUID getSagaId() {
        return sagaId;
    }

    public String getPayload() {
        return payload;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public OutboxStatus getOutboxStatus() {
        return outboxStatus;
    }

    public void setOutboxStatus(OutboxStatus outboxStatus) {
        this.outboxStatus = outboxStatus;
    }

    public static class Builder {
        private Long id;
        private UUID sagaId;
        private String payload;
        private PaymentStatus paymentStatus;
        private OutboxStatus outboxStatus;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder sagaId(UUID val) {
            sagaId = val;
            return this;
        }

        public Builder payload(String val) {
            payload = val;
            return this;
        }

        public Builder paymentStatus(PaymentStatus val) {
            paymentStatus = val;
            return this;
        }

        public Builder outboxStatus(OutboxStatus val) {
            outboxStatus = val;
            return this;
        }

        public OrderOutbox build() {
            return new OrderOutbox(this);
        }

    }
}
