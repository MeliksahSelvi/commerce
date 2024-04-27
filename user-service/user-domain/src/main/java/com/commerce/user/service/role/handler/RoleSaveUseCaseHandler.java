package com.commerce.user.service.role.handler;

import com.commerce.user.service.common.DomainComponent;
import com.commerce.user.service.common.handler.UseCaseHandler;
import com.commerce.user.service.role.model.Role;
import com.commerce.user.service.role.handler.helper.RoleSaveHelper;
import com.commerce.user.service.role.usecase.RoleSave;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 01.04.2024
 */

@DomainComponent
public class RoleSaveUseCaseHandler implements UseCaseHandler<Role, RoleSave> {

    private static final Logger logger= LoggerFactory.getLogger(RoleSaveUseCaseHandler.class);
    private final RoleSaveHelper roleSaveHelper;

    public RoleSaveUseCaseHandler(RoleSaveHelper roleSaveHelper) {
        this.roleSaveHelper = roleSaveHelper;
    }

    @Override
    public Role handle(RoleSave useCase) {
        logger.info("Role save action started by roleType: {}",useCase.roleType());
        return roleSaveHelper.save(useCase);
    }
}
