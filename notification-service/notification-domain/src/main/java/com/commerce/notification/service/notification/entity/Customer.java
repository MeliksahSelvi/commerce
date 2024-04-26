package com.commerce.notification.service.notification.entity;

import com.commerce.notification.service.common.model.BaseEntity;

/**
 * @Author mselvi
 * @Created 26.04.2024
 */

public class Customer extends BaseEntity {

    private String firstName;
    private String lastName;
    private String email;

    public static Builder builder() {
        return new Builder();
    }

    private Customer(Builder builder) {
        setId(builder.id);
        firstName = builder.firstName;
        lastName = builder.lastName;
        email = builder.email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public static final class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;

        private Builder() {

        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder firstName(String val) {
            firstName = val;
            return this;
        }

        public Builder lastName(String val) {
            lastName = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }
}
