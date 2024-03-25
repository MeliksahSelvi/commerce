package com.commerce.order.service.saga;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.saga.SagaStep;
import com.commerce.order.service.order.usecase.PaymentResponse;
import com.commerce.order.service.saga.helper.PaymentResponseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Author mselvi
 * @Created 05.03.2024
 */

@DomainComponent
public class PaymentResponseSagaStep implements SagaStep<PaymentResponse> {

    private static final Logger logger= LoggerFactory.getLogger(PaymentResponseSagaStep.class);
    private final PaymentResponseHelper paymentResponseHelper;

    public PaymentResponseSagaStep(PaymentResponseHelper paymentResponseHelper) {
        this.paymentResponseHelper = paymentResponseHelper;
    }

    @Override
    public void process(PaymentResponse useCase) {
        logger.info("Processing action for payment started with PaymentResponse");
        paymentResponseHelper.process(useCase);
    }

    @Override
    public void rollback(PaymentResponse useCase) {
        logger.info("Rollback action for payment started with PaymentResponse");
        paymentResponseHelper.rollback(useCase);
    }
}
