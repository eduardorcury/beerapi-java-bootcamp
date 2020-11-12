package com.digitalinnovationone.beerapi.controller;

import com.digitalinnovationone.beerapi.dto.BeerDTO;
import com.digitalinnovationone.beerapi.dto.QuantityDTO;
import com.digitalinnovationone.beerapi.exceptions.BeerNameAlreadyRegisteredException;
import com.digitalinnovationone.beerapi.exceptions.BeerNotFoundException;
import com.digitalinnovationone.beerapi.exceptions.BeerStockExceededException;
import com.digitalinnovationone.beerapi.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/beers")
public class BeerController implements BeerControllerDocs {

    private final BeerService beerService;

    @Autowired
    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BeerDTO createBeer(@RequestBody @Valid BeerDTO beerDTO) throws BeerNameAlreadyRegisteredException {
        return beerService.createBeer(beerDTO);
    }

    @GetMapping("/{name}")
    public BeerDTO findByName(@PathVariable String name) throws BeerNotFoundException {
        return beerService.findByName(name);
    }

    @GetMapping
    public List<BeerDTO> listAll() {
        return beerService.listAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable Long id) throws BeerNotFoundException {
        beerService.deleteById(id);
    }

    @PatchMapping("/{id}/increment")
    public BeerDTO increment(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws BeerStockExceededException, BeerNotFoundException {
        return beerService.increment(id, quantityDTO.getQuantity());
    }

    @PatchMapping("/{id}/decrement")
    public BeerDTO decrement(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws BeerStockExceededException, BeerNotFoundException {
        return beerService.decrement(id, quantityDTO.getQuantity());
    }
}
