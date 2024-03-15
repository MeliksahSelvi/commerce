package com.commerce.payment.service.adapters.payment.jpa.repository;

import com.commerce.payment.service.adapters.payment.jpa.entity.AccountActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Repository
public interface AccountActivityEntityRepository extends JpaRepository<AccountActivityEntity, Long> {
}
