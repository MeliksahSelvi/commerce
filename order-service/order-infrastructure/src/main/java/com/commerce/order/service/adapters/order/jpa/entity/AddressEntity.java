package com.commerce.order.service.adapters.order.jpa.entity;

import com.commerce.order.service.common.model.BaseEntity;
import com.commerce.order.service.order.model.Address;
import jakarta.persistence.*;

/**
 * @Author mselvi
 * @Created 05.03.2024
 */

@Entity
@Table(name = "ADDRESS")
public class AddressEntity extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID",nullable = false,unique = true)
    private OrderEntity order;

    @Column(name = "CITY", length = 50,nullable = false)
    private String city;

    @Column(name = "COUNTY", length = 50,nullable = false)
    private String county;

    @Column(name = "NEIGHBORHOOD", length = 50,nullable = false)
    private String neighborhood;

    @Column(name = "STREET", length = 100,nullable = false)
    private String street;

    @Column(name = "POSTAL_CODE", length = 5)
    private String postalCode;

    public Address toModel() {
        return Address.builder()
                .id(getId())
                .city(city)
                .county(county)
                .neighborhood(neighborhood)
                .street(street)
                .postalCode(postalCode)
                .build();
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
