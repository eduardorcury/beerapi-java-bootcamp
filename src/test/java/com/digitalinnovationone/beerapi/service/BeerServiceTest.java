package com.digitalinnovationone.beerapi.service;

import com.digitalinnovationone.beerapi.builder.MockBeerDTO;
import com.digitalinnovationone.beerapi.dto.BeerDTO;
import com.digitalinnovationone.beerapi.entity.Beer;
import com.digitalinnovationone.beerapi.exceptions.BeerNameAlreadyRegisteredException;
import com.digitalinnovationone.beerapi.exceptions.BeerNotFoundException;
import com.digitalinnovationone.beerapi.exceptions.BeerStockExceededException;
import com.digitalinnovationone.beerapi.mapper.BeerMapper;
import com.digitalinnovationone.beerapi.repository.BeerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BeerServiceTest {

    private final BeerMapper beerMapper = BeerMapper.INSTANCE;

    @Mock
    private BeerRepository beerRepository;

    @InjectMocks
    private BeerService beerService;

    @Test()
    void whenNewBeerInformedItShouldBeCreated() throws BeerNameAlreadyRegisteredException {

        BeerDTO beerDTO = MockBeerDTO.build();
        Beer expectedSavedBeer = beerMapper.toModel(beerDTO);

        when(beerRepository.findByName(beerDTO.getName())).thenReturn(Optional.empty());
        when(beerRepository.save(expectedSavedBeer)).thenReturn(expectedSavedBeer);

        BeerDTO createdBeerDTO = beerService.createBeer(beerDTO);

        assertThat(createdBeerDTO.getId(), is(equalTo(beerDTO.getId())));
        assertThat(createdBeerDTO.getName(), is(equalTo(beerDTO.getName())));
        assertThat(createdBeerDTO.getBrand(), is(equalTo(beerDTO.getBrand())));

    }

    @Test
    void whenAlreadyRegisteredBeerNameInformedThenExceptionShouldBeThrown() {

        BeerDTO beerDTO = MockBeerDTO.build();
        Beer duplicatedBeer = beerMapper.toModel(beerDTO);

        when(beerRepository.findByName(beerDTO.getName())).thenReturn(Optional.of(duplicatedBeer));

        Assertions.assertThrows(BeerNameAlreadyRegisteredException.class,
                () -> beerService.createBeer(beerDTO));
    }

    @Test
    void whenValidBeerNameIsGivenThenReturnBeer() throws BeerNotFoundException {

        BeerDTO beerDTO = MockBeerDTO.build();
        Beer expectedFoundBeer = beerMapper.toModel(beerDTO);

        when(beerRepository.findByName(beerDTO.getName())).thenReturn(Optional.of(expectedFoundBeer));

        BeerDTO foundBeerDTO = beerService.findByName(beerDTO.getName());
        assertThat(foundBeerDTO, is(equalTo(beerDTO)));

    }

    @Test
    void whenNotRegisteredBeerNameIsGivenThenAndExceptionShouldBeThrown() {

        BeerDTO beerDTO = MockBeerDTO.build();

        when(beerRepository.findByName(beerDTO.getName())).thenReturn(Optional.empty());

        Assertions.assertThrows(BeerNotFoundException.class,
                () -> beerService.findByName(beerDTO.getName()));
    }

    @Test
    void whenListBeersIsCalledThenReturnListOfBeers() {

        BeerDTO beerDTO = MockBeerDTO.build();
        Beer expectedFoundBeer = beerMapper.toModel(beerDTO);

        when(beerRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundBeer));

        List<BeerDTO> foundList = beerService.listAll();

        assertThat(foundList, is(not(empty())));
        assertThat(foundList.get(0), is(equalTo(beerDTO)));
    }

    @Test
    void whenListBeersIsCalledThenReturnEmptyList() {

        when(beerRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        List<BeerDTO> foundList = beerService.listAll();

        assertThat(foundList, is(empty()));
    }

    @Test
    void whenDeleteIsCalledWithValidIdThenBeerShouldBeDeleted() throws BeerNotFoundException {

        BeerDTO beerDTO = MockBeerDTO.build();
        Beer expectedDeletedBeer = beerMapper.toModel(beerDTO);

        when(beerRepository.findById(beerDTO.getId())).thenReturn(Optional.of(expectedDeletedBeer));
        doNothing().when(beerRepository).deleteById(expectedDeletedBeer.getId());

        beerService.deleteById(beerDTO.getId());
        verify(beerRepository, times(1)).findById(beerDTO.getId());
        verify(beerRepository, times(1)).deleteById(beerDTO.getId());

    }

    @Test
    void whenDeleteIsCalledWithInvalidIdThenExceptionShouldBeThrown() {

        BeerDTO beerDTO = MockBeerDTO.build();

        when(beerRepository.findById(beerDTO.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(BeerNotFoundException.class,
                () -> beerService.deleteById(beerDTO.getId()));
    }

    @Test
    void whenIncrementIsCalledThenIncrementStock() throws BeerStockExceededException, BeerNotFoundException {

        BeerDTO beerDTO = MockBeerDTO.build();
        Beer expectedIncrementedBeer = beerMapper.toModel(beerDTO);

        when(beerRepository.findById(beerDTO.getId())).thenReturn(Optional.of(expectedIncrementedBeer));
        when(beerRepository.save(expectedIncrementedBeer)).thenReturn(expectedIncrementedBeer);

        int quantityToIncrement = 10;
        int expectedQuantityAfterIncrement = quantityToIncrement + beerDTO.getQuantity();

        BeerDTO beerAfterIncrement = beerService.increment(beerDTO.getId(), quantityToIncrement);

        assertThat(beerAfterIncrement.getQuantity(), is(equalTo(expectedQuantityAfterIncrement)));
        assertThat(beerAfterIncrement.getMax(), is(greaterThanOrEqualTo(beerAfterIncrement.getQuantity())));

    }

    @Test
    void whenIncrementIsGreaterThanMaxThenThrowException() {

        BeerDTO beerDTO = MockBeerDTO.build();
        Beer expectedBeer = beerMapper.toModel(beerDTO);

        when(beerRepository.findById(beerDTO.getId())).thenReturn(Optional.of(expectedBeer));

        int quantityToIncrement = 80;

        Assertions.assertThrows(BeerStockExceededException.class,
                () -> beerService.increment(beerDTO.getId(), quantityToIncrement));

    }

    @Test
    void whenIncrementIsCalledWithInvalidIdThenThrowException() {

        BeerDTO beerDTO = MockBeerDTO.build();

        when(beerRepository.findById(beerDTO.getId())).thenReturn(Optional.empty());

        int quantityToIncrement = 10;

        Assertions.assertThrows(BeerNotFoundException.class,
                () -> beerService.increment(beerDTO.getId(), quantityToIncrement));

    }

    @Test
    void whenDecrementIsCalledThenDecrementStock() throws BeerStockExceededException, BeerNotFoundException {

        BeerDTO beerDTO = MockBeerDTO.build();
        Beer expectedDecrementedBeer = beerMapper.toModel(beerDTO);

        when(beerRepository.findById(beerDTO.getId())).thenReturn(Optional.of(expectedDecrementedBeer));
        when(beerRepository.save(expectedDecrementedBeer)).thenReturn(expectedDecrementedBeer);

        int quantityToDecrement = 5;
        int expectedQuantityAfterDecrement = beerDTO.getQuantity() - quantityToDecrement;

        BeerDTO beerAfterDecrement = beerService.decrement(beerDTO.getId(), quantityToDecrement);

        assertThat(beerAfterDecrement.getQuantity(), is(equalTo(expectedQuantityAfterDecrement)));
        assertThat(expectedQuantityAfterDecrement, is(greaterThan(0)));

    }

    @Test
    void whenDecrementIsCalledToEmptyStockThenEmptyBeerStock() throws BeerStockExceededException, BeerNotFoundException {

        BeerDTO beerDTO = MockBeerDTO.build();
        Beer expectedDecrementedBeer = beerMapper.toModel(beerDTO);

        when(beerRepository.findById(beerDTO.getId())).thenReturn(Optional.of(expectedDecrementedBeer));
        when(beerRepository.save(expectedDecrementedBeer)).thenReturn(expectedDecrementedBeer);

        int quantityToDecrement = 10;
        int expectedQuantityAfterDecrement = beerDTO.getQuantity() - quantityToDecrement;

        BeerDTO beerAfterDecrement = beerService.decrement(beerDTO.getId(), quantityToDecrement);

        assertThat(beerAfterDecrement.getQuantity(), is(equalTo(0)));
        assertThat(expectedQuantityAfterDecrement, is(equalTo(beerAfterDecrement.getQuantity())));

    }

    @Test
    void whenDecrementIsLowerThanZeroThenThrowException() {

        BeerDTO beerDTO = MockBeerDTO.build();
        Beer expectedDecrementedBeer = beerMapper.toModel(beerDTO);

        when(beerRepository.findById(beerDTO.getId())).thenReturn(Optional.of(expectedDecrementedBeer));

        int quantityToDecrement = 80;
        Assertions.assertThrows(BeerStockExceededException.class,
                () -> beerService.decrement(beerDTO.getId(), quantityToDecrement));

    }

    @Test
    void whenDecrementIsCalledWithInvalidIdThenThrowException() {

        BeerDTO beerDTO = MockBeerDTO.build();

        when(beerRepository.findById(beerDTO.getId())).thenReturn(Optional.empty());

        int quantityToDecrement = 5;
        Assertions.assertThrows(BeerNotFoundException.class,
                () -> beerService.decrement(beerDTO.getId(), quantityToDecrement));

    }

}






