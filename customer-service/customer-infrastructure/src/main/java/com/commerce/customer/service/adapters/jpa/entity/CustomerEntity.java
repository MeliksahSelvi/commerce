package com.commerce.customer.service.adapters.jpa.entity;

import com.commerce.customer.service.common.model.AbstractEntity;
import com.commerce.customer.service.customer.entity.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @Author mselvi
 * @Created 10.03.2024
 */

@Entity
@Table(name = "CUSTOMER")
public class CustomerEntity extends AbstractEntity {

    @Column(name = "FIRST_NAME", length = 100, nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", length = 100, nullable = false)
    private String lastName;

    @Column(name = "IDENTITY_NO", nullable = false, unique = true)
    private String identityNo;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    public Customer toModel(){
        return Customer.builder()
                .id(getId())
                .firstName(firstName)
                .lastName(lastName)
                .identityNo(identityNo)
                .email(email)
                .password(password)
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

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
