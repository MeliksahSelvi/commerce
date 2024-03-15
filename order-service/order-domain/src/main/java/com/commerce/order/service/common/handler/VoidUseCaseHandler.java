package com.commerce.order.service.common.handler;

import com.commerce.order.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 11.03.2024
 */

public interface VoidUseCaseHandler<T extends UseCase> {

    void handle(T useCase);
}
