package com.commerce.user.service.user.port.jpa;

import com.commerce.user.service.user.entity.User;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

public interface UserDataPort {

    Optional<User> findByEmail(String email);

    User save(User user);
}
