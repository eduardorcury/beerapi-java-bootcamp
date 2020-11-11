package com.digitalinnovationone.beerapi.dto;

import com.digitalinnovationone.beerapi.enums.BeerType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BeerDTO {

    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    @Size(min = 1, max = 100)
    private String brand;

    @NotNull
    @Max(50)
    private int max;

    @NotNull
    @Max(100)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @NotNull
    private BeerType beerType;

    public BeerDTO() {

    }

    public BeerDTO(Long id,
                   @NotNull @Size(min = 1, max = 100) String name,
                   @NotNull @Size(min = 1, max = 100) String brand,
                   @NotNull @Max(50) int max,
                   @NotNull @Max(100) int quantity,
                   @NotNull BeerType beerType) {

        this.id = id;
        this.name = name;
        this.brand = brand;
        this.max = max;
        this.quantity = quantity;
        this.beerType = beerType;
    }

    public static BeerDTOBuilder builder() {
        return new BeerDTOBuilder();
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
        return "BeerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", max=" + max +
                ", quantity=" + quantity +
                ", beerType=" + beerType +
                '}';
    }

    public static class BeerDTOBuilder {

        private Long id;

        @NotNull
        @Size(min = 1, max = 100)
        private String name;

        @NotNull
        @Size(min = 1, max = 100)
        private String brand;

        @NotNull
        @Max(50)
        private int max;

        @NotNull
        @Max(100)
        private int quantity;

        @Enumerated(EnumType.STRING)
        @NotNull
        private BeerType beerType;

        public BeerDTOBuilder() {

        }

        public BeerDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BeerDTOBuilder name(@NotNull @Size(min = 1, max = 100) String name) {
            this.name = name;
            return this;
        }

        public BeerDTOBuilder brand(@NotNull @Size(min = 1, max = 100) String brand) {
            this.brand = brand;
            return this;
        }

        public BeerDTOBuilder max(@NotNull @Max(50) int max) {
            this.max = max;
            return this;
        }

        public BeerDTOBuilder quantity(@NotNull @Max(100) int quantity) {
            this.quantity = quantity;
            return this;
        }

        public BeerDTOBuilder beerType(@NotNull BeerType beerType) {
            this.beerType = beerType;
            return this;
        }

        public BeerDTO build() {
            return new BeerDTO(id, name, brand, max, quantity, beerType);
        }

    }

}
