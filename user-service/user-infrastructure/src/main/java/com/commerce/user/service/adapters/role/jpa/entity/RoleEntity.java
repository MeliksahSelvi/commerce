package com.commerce.user.service.adapters.role.jpa.entity;

import com.commerce.user.service.common.model.AbstractEntity;
import com.commerce.user.service.common.valueobject.RoleType;
import com.commerce.user.service.role.entity.Role;
import jakarta.persistence.*;

/**
 * @Author mselvi
 * @Created 01.04.2024
 */

@Entity
@Table(name = "ROLE")
public class RoleEntity extends AbstractEntity {

    @Column(name = "ROLE_TYPE")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column(name = "PERMISSIONS")
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
