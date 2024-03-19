package com.commerce.notification.service.common.valueobject;

import com.commerce.notification.service.common.exception.NotificationDomainException;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

public record VerificationCode(String code) {

    private static final Integer codeLength = 10;

    public VerificationCode {
        if (!codeLength.equals(code.length())) {
            throw new NotificationDomainException("Verification code length must be 10 character");
        }
    }
}
