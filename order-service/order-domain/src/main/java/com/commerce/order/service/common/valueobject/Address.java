package com.commerce.order.service.common.valueobject;

/**
 * @Author mselvi
 * @Created 01.03.2024
 */

public record Address(Long id, String city, String county, String neighborhood, String street, String postalCode) {
}
