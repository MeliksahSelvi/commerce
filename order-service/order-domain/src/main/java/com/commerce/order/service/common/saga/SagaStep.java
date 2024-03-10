package com.commerce.order.service.common.saga;

import com.commerce.order.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 02.03.2024
 */

public interface SagaStep<T extends UseCase> {
    void process(T data);

    void rollback(T data);
}
