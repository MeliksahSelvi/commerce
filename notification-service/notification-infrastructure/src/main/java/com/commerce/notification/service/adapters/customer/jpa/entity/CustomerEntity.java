package com.commerce.notification.service.adapters.customer.jpa.entity;

import com.commerce.notification.service.common.model.BaseEntity;
import com.commerce.notification.service.customer.model.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @Author mselvi
 * @Created 26.04.2024
 */

@Entity
@Table(name = "CUSTOMER")
public class CustomerEntity extends BaseEntity {

    @Column(name = "FIRST_NAME", length = 100, nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", length = 100, nullable = false)
    private String lastName;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    public Customer toModel() {
        return Customer.builder()
                .id(getId())
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .build();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
