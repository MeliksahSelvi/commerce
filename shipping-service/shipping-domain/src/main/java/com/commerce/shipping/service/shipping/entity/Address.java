package com.commerce.shipping.service.shipping.entity;

import com.commerce.shipping.service.common.model.BaseEntity;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

public class Address extends BaseEntity {
    private String city;
    private String county;
    private String neighborhood;
    private String street;
    private String postalCode;

    private Address(Builder builder) {
        setId(builder.id);
        this.city = builder.city;
        this.county = builder.county;
        this.neighborhood = builder.neighborhood;
        this.street = builder.street;
        this.postalCode = builder.postalCode;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getCity() {
        return city;
    }

    public String getCounty() {
        return county;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public static final class Builder {
        private Long id;
        private String city;
        private String county;
        private String neighborhood;
        private String street;
        private String postalCode;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder city(String val) {
            city = val;
            return this;
        }

        public Builder county(String val) {
            county = val;
            return this;
        }

        public Builder neighborhood(String val) {
            neighborhood = val;
            return this;
        }

        public Builder street(String val) {
            street = val;
            return this;
        }

        public Builder postalCode(String val) {
            postalCode = val;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}
