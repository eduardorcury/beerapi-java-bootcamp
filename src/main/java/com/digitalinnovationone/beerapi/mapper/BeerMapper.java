package com.digitalinnovationone.beerapi.mapper;

import com.digitalinnovationone.beerapi.dto.BeerDTO;
import com.digitalinnovationone.beerapi.entity.Beer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeerMapper {

    BeerMapper INSTANCE = Mappers.getMapper(BeerMapper.class);

    Beer toModel(BeerDTO beerDTO);

    BeerDTO toDTO(Beer beer);

}
