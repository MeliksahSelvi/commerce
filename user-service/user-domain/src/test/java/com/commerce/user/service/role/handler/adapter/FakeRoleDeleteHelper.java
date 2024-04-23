package com.commerce.user.service.role.handler.adapter;

import com.commerce.user.service.role.handler.helper.RoleDeleteHelper;

/**
 * @Author mselvi
 * @Created 23.04.2024
 */

public class FakeRoleDeleteHelper extends RoleDeleteHelper {
    public FakeRoleDeleteHelper() {
        super(new FakeRoleDataAdapter());
    }
}
