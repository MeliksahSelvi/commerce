package com.commerce.payment.service.adapters.payment.json;

import java.io.Serializable;

/**
 * @Author mselvi
 * @Created 26.03.2024
 */

public class JavaPojo implements Serializable {

    private String name;
    private String surName;

    public JavaPojo(String name, String surName) {
        this.name = name;
        this.surName = surName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }
}
