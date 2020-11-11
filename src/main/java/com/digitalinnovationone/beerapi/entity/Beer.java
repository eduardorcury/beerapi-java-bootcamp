package com.digitalinnovationone.beerapi.entity;

import com.digitalinnovationone.beerapi.enums.BeerType;

import javax.persistence.*;

@Entity
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private int max;

    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BeerType beerType;

    public Beer() {

    }

    public Beer(Long id, String name, String brand, int max, int quantity, BeerType beerType) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.max = max;
        this.quantity = quantity;
        this.beerType = beerType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BeerType getBeerType() {
        return beerType;
    }

    public void setBeerType(BeerType beerType) {
        this.beerType = beerType;
    }

    @Override
    public String toString() {
        return "Beer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", max=" + max +
                ", quantity=" + quantity +
                ", beerType=" + beerType +
                '}';
    }
}
