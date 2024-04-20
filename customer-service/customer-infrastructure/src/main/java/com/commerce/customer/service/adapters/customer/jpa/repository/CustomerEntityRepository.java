package com.commerce.customer.service.adapters.customer.jpa.repository;

import com.commerce.customer.service.adapters.customer.jpa.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 10.03.2024
 */

@Repository
public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByEmailOrIdentityNo(String email, String identityNo);
}
