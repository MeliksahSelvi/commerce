package com.commerce.user.service.role.handler;

import com.commerce.user.service.common.DomainComponent;
import com.commerce.user.service.common.handler.VoidUseCaseHandler;
import com.commerce.user.service.role.handler.helper.RoleDeleteHelper;
import com.commerce.user.service.role.usecase.RoleDelete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

@DomainComponent
public class RoleDeleteVoidUseCaseHandler implements VoidUseCaseHandler<RoleDelete> {

    private static final Logger logger= LoggerFactory.getLogger(RoleDeleteVoidUseCaseHandler.class);

    private final RoleDeleteHelper roleDeleteHelper;

    public RoleDeleteVoidUseCaseHandler(RoleDeleteHelper roleDeleteHelper) {
        this.roleDeleteHelper = roleDeleteHelper;
    }

    @Override
    public void handle(RoleDelete useCase) {
        logger.info("Role delete action started by id: {}",useCase.roleId());
        roleDeleteHelper.delete(useCase);
    }
}
