package com.commerce.notification.service.adapters.notification.jpa.repository;

import com.commerce.notification.service.adapters.notification.jpa.entity.MemberVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Repository
public interface MemberVerificationEntityRepository extends JpaRepository<MemberVerificationEntity, Long> {
}
