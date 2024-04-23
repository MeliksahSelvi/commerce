package com.commerce.user.service.user.handler.adapter;

import com.commerce.user.service.role.handler.adapter.FakeRoleDataAdapter;
import com.commerce.user.service.user.handler.helper.UserRegisterHelper;

/**
 * @Author mselvi
 * @Created 23.04.2024
 */

public class FakeUserRegisterHelper extends UserRegisterHelper {
    public FakeUserRegisterHelper() {
        super(new FakeEncryptingAdapter(), new FakeUserDataAdapter(), new FakeRoleDataAdapter());
    }
}
