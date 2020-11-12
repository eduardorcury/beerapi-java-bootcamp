package com.digitalinnovationone.beerapi.builder;

import com.digitalinnovationone.beerapi.dto.BeerDTO;
import com.digitalinnovationone.beerapi.enums.BeerType;

public class MockBeerDTO {

    private static Long id = 1L;
    private static String name = "Brahma";
    private static String brand = "Ambev";
    private static int max = 40;
    private static int quantity = 10;
    private static BeerType type = BeerType.LAGER;

    public static BeerDTO build() {
        return new BeerDTO(id, name, brand, max, quantity,type);
    }

}
