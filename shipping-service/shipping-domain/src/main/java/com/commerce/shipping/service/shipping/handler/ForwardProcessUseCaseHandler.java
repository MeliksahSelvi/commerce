package com.commerce.shipping.service.shipping.handler;

import com.commerce.shipping.service.common.DomainComponent;
import com.commerce.shipping.service.common.handler.UseCaseHandler;
import com.commerce.shipping.service.shipping.model.Shipping;
import com.commerce.shipping.service.shipping.handler.helper.ForwardProcessHelper;
import com.commerce.shipping.service.shipping.usecase.ForwardProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

@DomainComponent
public class ForwardProcessUseCaseHandler implements UseCaseHandler<Shipping, ForwardProcess> {

    private static final Logger logger = LoggerFactory.getLogger(ForwardProcessUseCaseHandler.class);
    private final ForwardProcessHelper forwardProcessHelper;

    public ForwardProcessUseCaseHandler(ForwardProcessHelper forwardProcessHelper) {
        this.forwardProcessHelper = forwardProcessHelper;
    }

    @Override
    public Shipping handle(ForwardProcess useCase) {
        logger.info("Forward process action started by orderId: {}", useCase.orderId());
        return forwardProcessHelper.forward(useCase);
    }
}
