package com.commerce.user.service.user.handler.adapter;

import com.commerce.user.service.user.handler.helper.UserLoginHelper;

/**
 * @Author mselvi
 * @Created 23.04.2024
 */

public class FakeUserLoginHelper extends UserLoginHelper {
    public FakeUserLoginHelper() {
        super(new FakeUserDataAdapter(), new FakeTokenCachePort(), new FakeTokenGenerateAdapter());
    }
}
