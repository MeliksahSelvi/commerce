package com.commerce.user.service.adapters.role.jpa.entity;

import com.commerce.user.service.common.model.BaseEntity;
import com.commerce.user.service.common.valueobject.RoleType;
import com.commerce.user.service.role.model.Role;
import jakarta.persistence.*;

/**
 * @Author mselvi
 * @Created 01.04.2024
 */

@Entity
@Table(name = "ROLE")
public class RoleEntity extends BaseEntity {

    @Column(name = "ROLE_TYPE",nullable = false,unique = true)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column(name = "PERMISSIONS",nullable = false)
    private String permissions;

    public Role toModel(){
        return Role.builder()
                .id(super.getId())
                .roleType(roleType)
                .permissions(permissions)
                .build();
    }
    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
