package com.commerce.user.service.adapters.user.jpa.entity;

import com.commerce.user.service.common.model.BaseEntity;
import com.commerce.user.service.user.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

@Entity
@Table(name = "\"user\"")
public class UserEntity extends BaseEntity {

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "CUSTOMER_ID", unique = true)
    private Long customerId;

    @Column(name = "ROLE_ID",nullable = false)
    private Long roleId;

    public User toModel() {
        return User.builder()
                .id(super.getId())
                .email(email)
                .password(password)
                .customerId(customerId)
                .roleId(roleId)
                .build();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
