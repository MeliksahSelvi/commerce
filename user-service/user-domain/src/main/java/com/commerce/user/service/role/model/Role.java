package com.commerce.user.service.role.model;

import com.commerce.user.service.common.model.BaseModel;
import com.commerce.user.service.common.valueobject.RoleType;

/**
 * @Author mselvi
 * @Created 01.04.2024
 */

public class Role extends BaseModel {

    private RoleType roleType;
    private String permissions;

    public RoleType getRoleType() {
        return roleType;
    }

    public String getPermissions() {
        return permissions;
    }

    public static Builder builder(){
        return new Builder();
    }

    private Role(Builder builder) {
        setId(builder.id);
        roleType = builder.roleType;
        permissions = builder.permissions;
    }

    public static final class Builder {
        private Long id;
        private RoleType roleType;
        private String permissions;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder roleType(RoleType val) {
            roleType = val;
            return this;
        }

        public Builder permissions(String val) {
            permissions = val;
            return this;
        }

        public Role build() {
            return new Role(this);
        }
    }
}
