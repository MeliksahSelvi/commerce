package com.commerce.user.service.user.port.jpa;

import com.commerce.user.service.user.entity.User;
import com.commerce.user.service.user.usecase.UserDelete;
import com.commerce.user.service.user.usecase.UserRetrieve;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

public interface UserDataPort {

    Optional<User> findByEmail(String email);

    User save(User user);

    void deleteById(UserDelete userDelete);

    Optional<User> findById(UserRetrieve userRetrieve);
}
