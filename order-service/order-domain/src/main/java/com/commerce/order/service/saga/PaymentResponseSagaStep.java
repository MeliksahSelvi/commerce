package com.commerce.order.service.saga;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.saga.SagaStep;
import com.commerce.order.service.order.usecase.PaymentResponse;
import com.commerce.order.service.saga.helper.PaymentResponseHelper;


/**
 * @Author mselvi
 * @Created 05.03.2024
 */

@DomainComponent
public class PaymentResponseSagaStep implements SagaStep<PaymentResponse> {

    private final PaymentResponseHelper paymentResponseHelper;

    public PaymentResponseSagaStep(PaymentResponseHelper paymentResponseHelper) {
        this.paymentResponseHelper = paymentResponseHelper;
    }

    @Override
    public void process(PaymentResponse useCase) {
        paymentResponseHelper.process(useCase);
    }

    @Override
    public void rollback(PaymentResponse useCase) {
        paymentResponseHelper.rollback(useCase);
    }
}
