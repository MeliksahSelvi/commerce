package com.commerce.notification.service.adapters.notification.jpa.entity;

import com.commerce.notification.service.common.model.BaseEntity;
import com.commerce.notification.service.notification.model.MemberVerification;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Entity
@Table(name = "MEMBER_VERIFICATION")
public class MemberVerificationEntity extends BaseEntity {

    @Column(name = "USER_ID",nullable = false)
    private Long userId;

    @Column(name = "URL",nullable = false)
    private String url;

    public MemberVerification toModel() {
        return MemberVerification.builder()
                .id(getId())
                .userId(userId)
                .url(url)
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
}
