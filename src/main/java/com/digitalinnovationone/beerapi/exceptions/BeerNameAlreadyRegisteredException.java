package com.digitalinnovationone.beerapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BeerNameAlreadyRegisteredException extends Exception {

    public BeerNameAlreadyRegisteredException(String name) {
        super(String.format("Beer with name %s already registered.", name));
    }

}
