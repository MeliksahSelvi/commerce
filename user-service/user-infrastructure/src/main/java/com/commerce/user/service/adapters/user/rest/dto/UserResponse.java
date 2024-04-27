package com.commerce.user.service.adapters.user.rest.dto;

import com.commerce.user.service.user.model.User;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

public record UserResponse(Long id, String email, Long customerId, Long roleId) {

    public UserResponse(User user) {
        this(user.getId(), user.getEmail(), user.getCustomerId(), user.getRoleId());
    }
}
