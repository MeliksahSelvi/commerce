package com.commerce.payment.service.adapters.payment.jpa.repository;

import com.commerce.payment.service.adapters.payment.jpa.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Repository
public interface AccountEntityRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByCustomerId(Long customerId);
}
