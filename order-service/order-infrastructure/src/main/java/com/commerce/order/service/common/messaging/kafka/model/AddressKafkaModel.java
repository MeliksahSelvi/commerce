package com.commerce.order.service.common.messaging.kafka.model;

import com.commerce.order.service.order.entity.Address;

/**
 * @Author mselvi
 * @Created 04.04.2024
 */

public record AddressKafkaModel(Long id, String city, String county, String neighborhood, String street,
                                String postalCode) implements KafkaModel {

    public AddressKafkaModel(Address address) {
        this(address.getId(), address.getCity(), address.getCounty(), address.getNeighborhood(), address.getStreet(), address.getPostalCode());
    }
}
