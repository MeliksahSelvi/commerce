package com.commerce.notification.service.adapters.customer.jpa.repository;

import com.commerce.notification.service.adapters.customer.jpa.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author mselvi
 * @Created 26.04.2024
 */

@Repository
public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, Long> {
}
