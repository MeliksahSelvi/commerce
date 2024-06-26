package com.commerce.payment.service.payment.port.messaging.input;

import com.commerce.payment.service.payment.usecase.PaymentRequest;

import java.util.List;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public interface PaymentRequestMessageListener {

    List<String> completePayment(PaymentRequest paymentRequest);

    List<String> cancelPayment(PaymentRequest paymentRequest);
}
