package com.commerce.user.service.user.handler.adapter;

import com.commerce.user.service.user.handler.helper.UserDeleteHelper;

/**
 * @Author mselvi
 * @Created 23.04.2024
 */

public class FakeUserDeleteHelper extends UserDeleteHelper {
    public FakeUserDeleteHelper() {
        super(new FakeUserDataAdapter());
    }
}
