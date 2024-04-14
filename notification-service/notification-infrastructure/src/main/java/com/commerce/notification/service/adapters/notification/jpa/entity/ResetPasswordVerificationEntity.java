package com.commerce.notification.service.adapters.notification.jpa.entity;

import com.commerce.notification.service.common.model.AbstractEntity;
import com.commerce.notification.service.notification.entity.ResetPasswordVerification;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Entity
@Table(name = "RESET_PASSWORD_VERIFICATION")
public class ResetPasswordVerificationEntity extends AbstractEntity {

    @Column(name = "USER_ID",nullable = false)
    private Long userId;

    @Column(name = "URL",nullable = false)
    private String url;

    @Column(name = "EXPIRATION_DATE",nullable = false)
    private LocalDateTime expirationDate;

    public ResetPasswordVerification toModel() {
        return ResetPasswordVerification.builder()
                .id(getId())
                .userId(userId)
                .url(url)
                .expirationDate(expirationDate)
                .build();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}
