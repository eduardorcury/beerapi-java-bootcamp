package com.digitalinnovationone.beerapi.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class QuantityDTO {

    @NotNull
    @Max(100)
    private int quantity;

    QuantityDTO() {

    }

    public QuantityDTO(@NotNull @Max(100) int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static QuantityDTOBuilder builder() {
        return new QuantityDTOBuilder();
    }

    private static class QuantityDTOBuilder {

        @NotNull
        @Max(100)
        private int quantity;

        QuantityDTOBuilder() {

        }

        public QuantityDTOBuilder quantity(@NotNull @Max(100) int quantity) {
            this.quantity = quantity;
            return this;
        }

        public QuantityDTO build() {
            return new QuantityDTO(quantity);
        }

    }

    @Override
    public String toString() {
        return "QuantityDTO{" +
                "quantity=" + quantity +
                '}';
    }

}
