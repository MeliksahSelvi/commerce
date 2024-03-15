package com.commerce.inventory.service.common.saga;

import com.commerce.inventory.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 10.03.2024
 */

public interface SagaStep<T extends UseCase> {
    void process(T usecase);

    void rollback(T usecase);
}
