package com.commerce.order.service.common.model;

import java.util.Objects;

/**
 * @Author mselvi
 * @Created 01.03.2024
 */

public abstract class BaseModel {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BaseModel that = (BaseModel) obj;
        return id.equals(that.id);
    }
}
