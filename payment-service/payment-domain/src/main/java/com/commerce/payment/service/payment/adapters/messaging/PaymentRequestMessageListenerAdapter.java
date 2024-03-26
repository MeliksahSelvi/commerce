package com.commerce.payment.service.payment.adapters.messaging;

import com.commerce.payment.service.common.DomainComponent;
import com.commerce.payment.service.payment.adapters.messaging.helper.PaymentRequestListenerHelper;
import com.commerce.payment.service.payment.port.messaging.input.PaymentRequestMessageListener;
import com.commerce.payment.service.payment.usecase.PaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

@DomainComponent
public class PaymentRequestMessageListenerAdapter implements PaymentRequestMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(PaymentRequestMessageListenerAdapter.class);

    private final PaymentRequestListenerHelper paymentRequestListenerHelper;

    public PaymentRequestMessageListenerAdapter(PaymentRequestListenerHelper paymentRequestListenerHelper) {
        this.paymentRequestListenerHelper = paymentRequestListenerHelper;
    }

    @Override
    public void completePayment(PaymentRequest paymentRequest) {
        logger.info("Payment complete action started with PaymentRequest");
        paymentRequestListenerHelper.completePayment(paymentRequest);
    }

    @Override
    public void cancelPayment(PaymentRequest paymentRequest) {
        logger.info("Payment cancel action started with PaymentRequest");
        paymentRequestListenerHelper.cancelPayment(paymentRequest);
    }
}
