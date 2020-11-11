package com.digitalinnovationone.beerapi.service;

import com.digitalinnovationone.beerapi.dto.BeerDTO;
import com.digitalinnovationone.beerapi.entity.Beer;
import com.digitalinnovationone.beerapi.exceptions.BeerNameAlreadyRegisteredException;
import com.digitalinnovationone.beerapi.exceptions.BeerNotFoundException;
import com.digitalinnovationone.beerapi.exceptions.BeerStockExceededException;
import com.digitalinnovationone.beerapi.mapper.BeerMapper;
import com.digitalinnovationone.beerapi.repository.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BeerService {

    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper = BeerMapper.INSTANCE;

    @Autowired
    public BeerService(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    public BeerDTO createBeer(BeerDTO beerDTO) throws BeerNameAlreadyRegisteredException {

        verifyIfNameIsAlreadyRegistered(beerDTO.getName());
        Beer beer = beerMapper.toModel(beerDTO);
        Beer savedBeer = beerRepository.save(beer);
        return beerMapper.toDTO(savedBeer);

    }

    public BeerDTO findByName(String beerName) throws BeerNotFoundException {
        Beer beer = beerRepository.findByName(beerName).orElseThrow(() -> new BeerNotFoundException(beerName));
        return beerMapper.toDTO(beer);
    }

    public List<BeerDTO> listAll() {
        return beerRepository.findAll().stream().map(beerMapper::toDTO).collect(Collectors.toList());
    }

    public void deleteById(Long id) throws BeerNotFoundException {
        verifyIfExists(id);
        beerRepository.deleteById(id);
    }

    public BeerDTO increment(Long id, int quantityToIncrement) throws BeerStockExceededException, BeerNotFoundException {
        Beer beer = verifyIfExists(id);
        int newQuantity = quantityToIncrement + beer.getQuantity();
        if (newQuantity <= beer.getMax()) {
            beer.setQuantity(newQuantity);
            Beer savedBeer = beerRepository.save(beer);
            return beerMapper.toDTO(savedBeer);
        }
        throw new BeerStockExceededException(id, quantityToIncrement);
    }

    public BeerDTO decrement(Long id, int quantityToDecrement) throws BeerStockExceededException, BeerNotFoundException {
        Beer beer = verifyIfExists(id);
        int newQuantity = beer.getQuantity() - quantityToDecrement;
        if (newQuantity >= 0) {
            beer.setQuantity(newQuantity);
            Beer savedBeer = beerRepository.save(beer);
            return beerMapper.toDTO(savedBeer);
        }
        throw new BeerStockExceededException(id, quantityToDecrement);
    }

    private Beer verifyIfExists(Long id) throws BeerNotFoundException {
        return beerRepository.findById(id).orElseThrow(() -> new BeerNotFoundException(id));
    }

    private void verifyIfNameIsAlreadyRegistered(String name) throws BeerNameAlreadyRegisteredException {
        Optional<Beer> optionalBeer = beerRepository.findByName(name);
        if (optionalBeer.isPresent()) {
            throw new BeerNameAlreadyRegisteredException(name);
        }
    }


}









