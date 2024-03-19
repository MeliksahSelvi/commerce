package com.commerce.notification.service.notification.entity;

import com.commerce.notification.service.common.model.BaseEntity;
import com.commerce.notification.service.common.valueobject.VerificationCode;

import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

public class TwoFactorVerification extends BaseEntity {

    private final Long userId;
    private VerificationCode code;
    private LocalDateTime expirationDate;

    private TwoFactorVerification(Builder builder) {
        setId(builder.id);
        this.userId = builder.userId;
        this.code = builder.code;
        this.expirationDate = builder.expirationDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getUserId() {
        return userId;
    }

    public VerificationCode getCode() {
        return code;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public static final class Builder {
        private Long id;
        private Long userId;
        private VerificationCode code;
        private LocalDateTime expirationDate;

        private Builder() {
        }

        public Builder id(Long val) {
            this.id = val;
            return this;
        }

        public Builder userId(Long val) {
            this.userId = val;
            return this;
        }

        public Builder code(VerificationCode val) {
            this.code = val;
            return this;
        }

        public Builder expirationDate(LocalDateTime val) {
            this.expirationDate = val;
            return this;
        }

        public TwoFactorVerification build() {
            return new TwoFactorVerification(this);
        }
    }
}
