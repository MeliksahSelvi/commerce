package com.commerce.user.service.role.handler.adapter;

import com.commerce.user.service.role.handler.helper.RoleSaveHelper;

/**
 * @Author mselvi
 * @Created 23.04.2024
 */

public class FakeRoleSaveHelper extends RoleSaveHelper {
    public FakeRoleSaveHelper() {
        super(new FakeRoleDataAdapter());
    }
}
