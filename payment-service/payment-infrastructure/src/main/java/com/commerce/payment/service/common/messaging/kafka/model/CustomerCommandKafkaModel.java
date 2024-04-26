package com.commerce.payment.service.common.messaging.kafka.model;

import com.commerce.payment.service.customer.usecase.CustomerInfo;

/**
 * @Author mselvi
 * @Created 26.04.2024
 */

public record CustomerCommandKafkaModel(Long id, String firstName, String lastName, String email) implements KafkaModel {

    public CustomerCommandKafkaModel(CustomerInfo customerInfo) {
        this(customerInfo.id(),customerInfo.firstName(),customerInfo.lastName(),customerInfo.email());
    }
}