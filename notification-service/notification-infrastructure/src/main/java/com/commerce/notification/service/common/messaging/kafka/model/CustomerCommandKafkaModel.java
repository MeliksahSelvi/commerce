package com.commerce.notification.service.common.messaging.kafka.model;

/**
 * @Author mselvi
 * @Created 26.04.2024
 */

public record CustomerCommandKafkaModel(Long id, String firstName, String lastName, String email) implements KafkaModel {
}
