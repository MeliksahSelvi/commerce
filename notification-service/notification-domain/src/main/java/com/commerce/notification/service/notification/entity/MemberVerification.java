package com.commerce.notification.service.notification.entity;

import com.commerce.notification.service.common.model.BaseEntity;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

public class MemberVerification extends BaseEntity {

    private final Long userId;
    private String url;

    private MemberVerification(Builder builder) {
        setId(builder.id);
        this.userId = builder.userId;
        this.url = builder.url;
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

    public static final class Builder {
        private Long id;
        private Long userId;
        private String url;

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

        public MemberVerification build() {
            return new MemberVerification(this);
        }
    }
}
