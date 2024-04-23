package com.commerce.user.service.user.handler.adapter;

import com.commerce.user.service.user.entity.User;
import com.commerce.user.service.user.port.jpa.UserDataPort;
import com.commerce.user.service.user.usecase.UserDelete;
import com.commerce.user.service.user.usecase.UserRetrieve;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 23.04.2024
 */

public class FakeUserDataAdapter implements UserDataPort {

    private static final Long EXIST_USER_ID = 1L;
    private static final Long EXIST_ROLE_ID = 1L;
    private static final String ALREADY_EXIST_EMAIL = "already.exist@gmail.com";

    @Override
    public Optional<User> findByEmail(String email) {
        if (ALREADY_EXIST_EMAIL != email) {
            return Optional.empty();
        }
        return Optional.of(User.builder()
                .id(EXIST_USER_ID)
                .roleId(EXIST_ROLE_ID)
                .email(ALREADY_EXIST_EMAIL)
                .customerId(1L)
                .password("$2a$10$VhHLhVweA2NFyMXK.k8ZaeNPslMKZISckaSRpg7j9.I85kR101F1i")
                .build());
    }

    @Override
    public User save(User user) {
        return User.builder()
                .id(EXIST_USER_ID)
                .email(user.getEmail())
                .password(user.getPassword())
                .roleId(user.getRoleId())
                .customerId(user.getCustomerId())
                .build();
    }

    @Override
    public void deleteById(UserDelete userDelete) {

    }

    @Override
    public Optional<User> findById(UserRetrieve userRetrieve) {
        if (EXIST_USER_ID !=userRetrieve.userId()){
            return Optional.empty();
        }
        return Optional.of(User.builder()
                .id(EXIST_USER_ID)
                .roleId(EXIST_ROLE_ID)
                .email(ALREADY_EXIST_EMAIL)
                .customerId(1L)
                .password("$2a$10$VhHLhVweA2NFyMXK.k8ZaeNPslMKZISckaSRpg7j9.I85kR101F1i")
                .build());
    }
}
