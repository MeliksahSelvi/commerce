package com.commerce.payment.service.payment.adapters.messaging;

import com.commerce.payment.service.common.DomainComponent;
import com.commerce.payment.service.payment.adapters.messaging.helper.PaymentRequestListenerHelper;
import com.commerce.payment.service.payment.entity.Payment;
import com.commerce.payment.service.payment.port.messaging.input.PaymentRequestMessageListener;
import com.commerce.payment.service.payment.usecase.PaymentRequest;

import java.util.List;

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
    public List<String> completePayment(PaymentRequest paymentRequest) {
        return paymentRequestListenerHelper.completePayment(paymentRequest);
    }

    @Override
    public List<String> cancelPayment(PaymentRequest paymentRequest) {
        return paymentRequestListenerHelper.cancelPayment(paymentRequest);
    }
}
