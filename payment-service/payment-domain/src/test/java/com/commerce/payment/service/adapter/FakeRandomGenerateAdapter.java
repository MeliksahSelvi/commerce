package com.commerce.payment.service.adapter;

import com.commerce.payment.service.payment.port.generate.RandomGeneratePort;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

public class FakeRandomGenerateAdapter implements RandomGeneratePort {

    @Override
    public String randomNumericAsString(int charCount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < charCount; i++) {
            sb.append('#');
        }
        return sb.toString();
    }
}
