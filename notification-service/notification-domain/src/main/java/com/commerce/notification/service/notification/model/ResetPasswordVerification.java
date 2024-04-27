package com.commerce.notification.service.notification.model;

import com.commerce.notification.service.common.model.BaseModel;

import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

public class ResetPasswordVerification extends BaseModel {

    private final Long userId;
    private String url;
    private LocalDateTime expirationDate;

    private ResetPasswordVerification(Builder builder) {
        setId(builder.id);
        this.userId = builder.userId;
        this.url = builder.url;
        this.expirationDate = builder.expirationDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getUserId() {
        return userId;
    }

    public String getUrl() {
        return url;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public static final class Builder {
        private Long id;
        private Long userId;
        private String url;
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

        public Builder url(String val) {
            this.url = val;
            return this;
        }

        public Builder expirationDate(LocalDateTime val) {
            this.expirationDate = val;
            return this;
        }

        public ResetPasswordVerification build() {
            return new ResetPasswordVerification(this);
        }
    }
}
