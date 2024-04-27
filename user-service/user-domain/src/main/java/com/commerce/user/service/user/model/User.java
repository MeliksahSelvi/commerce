package com.commerce.user.service.user.model;

import com.commerce.user.service.common.model.BaseModel;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

public class User extends BaseModel {

    private String email;
    private String password;
    private Long customerId;
    private Long roleId;

    private User(Builder builder) {
        setId(builder.id);
        email = builder.email;
        password = builder.password;
        customerId = builder.customerId;
        roleId=builder.roleId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getEmail() {
        return email;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getPassword() {
        return password;
    }

    public Long getRoleId() {
        return roleId;
    }

    public static final class Builder {
        private Long id;
        private String email;
        private String password;
        private Long customerId;
        private Long roleId;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder customerId(Long val) {
            customerId = val;
            return this;
        }

        public Builder roleId(Long val) {
            roleId = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
