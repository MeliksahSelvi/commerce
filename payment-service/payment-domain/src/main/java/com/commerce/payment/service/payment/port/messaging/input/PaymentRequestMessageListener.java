package com.commerce.payment.service.payment.port.messaging.input;

import com.commerce.payment.service.payment.usecase.PaymentRequest;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public interface PaymentRequestMessageListener {

    void completePayment(PaymentRequest paymentRequest);

    void cancelPayment(PaymentRequest paymentRequest);
}
