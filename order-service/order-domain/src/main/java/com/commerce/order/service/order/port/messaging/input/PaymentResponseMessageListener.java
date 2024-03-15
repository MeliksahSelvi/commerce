package com.commerce.order.service.order.port.messaging.input;

import com.commerce.order.service.order.usecase.PaymentResponse;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

public interface PaymentResponseMessageListener {

    void paymentCompleted(PaymentResponse paymentResponse);

    void paymentCancelled(PaymentResponse paymentResponse);
}
