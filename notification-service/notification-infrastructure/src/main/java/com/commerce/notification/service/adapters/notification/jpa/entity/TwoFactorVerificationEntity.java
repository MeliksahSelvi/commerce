package com.commerce.notification.service.adapters.notification.jpa.entity;

import com.commerce.notification.service.common.model.AbstractEntity;
import com.commerce.notification.service.common.valueobject.VerificationCode;
import com.commerce.notification.service.notification.entity.TwoFactorVerification;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Entity
@Table(name = "TWO_FACTOR_VERIFICATION")
public class TwoFactorVerificationEntity extends AbstractEntity {

    @Column(name = "USER_ID",nullable = false)
    private Long userId;

    @Column(name = "CODE", length = 10, nullable = false)
    private String code;

    @Column(name = "EXPIRATION_DATE", nullable = false)
    private LocalDateTime expirationDate;

    public TwoFactorVerification toModel() {
        return TwoFactorVerification.builder()
                .id(getId())
                .userId(userId)
                .code(new VerificationCode(code))
                .expirationDate(expirationDate)
                .build();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}
