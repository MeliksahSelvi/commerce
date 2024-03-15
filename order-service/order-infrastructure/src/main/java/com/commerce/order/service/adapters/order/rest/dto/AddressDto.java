package com.commerce.order.service.adapters.order.rest.dto;

import com.commerce.order.service.common.valueobject.Address;
import jakarta.validation.constraints.NotEmpty;

/**
 * @Author mselvi
 * @Created 05.03.2024
 */

public record AddressDto(@NotEmpty String city, @NotEmpty String county, @NotEmpty String neighborhood,
                         @NotEmpty String street, String postalCode) {

    public AddressDto(Address address) {
        this(address.city(), address.county(), address.neighborhood(), address.street(), address.postalCode());
    }

    public Address toModel() {
        return new Address(city, county, neighborhood, street, postalCode);
    }
}
