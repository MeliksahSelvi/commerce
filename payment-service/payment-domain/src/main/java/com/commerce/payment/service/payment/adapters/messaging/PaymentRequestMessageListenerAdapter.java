package com.commerce.payment.service.payment.adapters.messaging;

import com.commerce.payment.service.common.DomainComponent;
import com.commerce.payment.service.payment.adapters.messaging.helper.PaymentRequestListenerHelper;
import com.commerce.payment.service.payment.port.messaging.input.PaymentRequestMessageListener;
import com.commerce.payment.service.payment.usecase.PaymentRequest;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

@DomainComponent
public class PaymentRequestMessageListenerAdapter implements PaymentRequestMessageListener {

    private final PaymentRequestListenerHelper paymentRequestListenerHelper;

    public PaymentRequestMessageListenerAdapter(PaymentRequestListenerHelper paymentRequestListenerHelper) {
        this.paymentRequestListenerHelper = paymentRequestListenerHelper;
    }

    @Override
    public void completePayment(PaymentRequest paymentRequest) {
        paymentRequestListenerHelper.completePayment(paymentRequest);
    }

    @Override
    public void cancelPayment(PaymentRequest paymentRequest) {
        paymentRequestListenerHelper.cancelPayment(paymentRequest);
    }
}
