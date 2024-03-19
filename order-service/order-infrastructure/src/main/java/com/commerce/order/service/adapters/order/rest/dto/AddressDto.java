package com.commerce.order.service.adapters.order.rest.dto;

import com.commerce.order.service.order.entity.Address;
import jakarta.validation.constraints.NotEmpty;

/**
 * @Author mselvi
 * @Created 05.03.2024
 */

public record AddressDto(@NotEmpty String city, @NotEmpty String county, @NotEmpty String neighborhood,
                         @NotEmpty String street, String postalCode) {

    public Address toModel() {
        return Address.builder()
                .city(city)
                .county(county)
                .neighborhood(neighborhood)
                .street(street)
                .postalCode(postalCode)
                .build();
    }
}
