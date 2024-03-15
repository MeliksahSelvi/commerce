package com.commerce.customer.service.customer.entity;

import com.commerce.customer.service.common.model.BaseEntity;

/**
 * @Author mselvi
 * @Created 10.03.2024
 */

public class Customer extends BaseEntity {

    private String firstName;
    private String lastName;
    private final String identityNo;
    private String email;
    private String password;

    public static Builder builder() {
        return new Builder();
    }

    private Customer(Builder builder) {
        setId(builder.id);
        firstName = builder.firstName;
        lastName = builder.lastName;
        identityNo = builder.identityNo;
        email = builder.email;
        password = builder.password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public static final class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private String identityNo;
        private String email;
        private String password;

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

        public Builder identityNo(String val) {
            identityNo = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
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
