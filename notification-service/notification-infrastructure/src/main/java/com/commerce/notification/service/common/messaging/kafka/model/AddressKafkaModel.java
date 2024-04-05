package com.commerce.notification.service.common.messaging.kafka.model;

/**
 * @Author mselvi
 * @Created 04.04.2024
 */

public record AddressKafkaModel(Long id, String city, String county, String neighborhood, String street,
                                String postalCode) implements KafkaModel {

}
