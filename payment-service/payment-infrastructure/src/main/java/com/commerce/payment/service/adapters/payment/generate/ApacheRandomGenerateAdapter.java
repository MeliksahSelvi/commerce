package com.commerce.payment.service.adapters.payment.generate;

import com.commerce.payment.service.payment.port.generate.RandomGeneratePort;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

/**
 * @Author mselvi
 * @Created 12.03.2024
 */

@Service
public class ApacheRandomGenerateAdapter implements RandomGeneratePort {

    @Override
    public String randomNumericAsString(int charCount) {
        return RandomStringUtils.randomNumeric(charCount);
    }
}
