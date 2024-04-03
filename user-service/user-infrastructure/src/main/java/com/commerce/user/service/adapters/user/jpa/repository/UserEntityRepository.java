package com.commerce.user.service.adapters.user.jpa.repository;

import com.commerce.user.service.adapters.user.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
}
