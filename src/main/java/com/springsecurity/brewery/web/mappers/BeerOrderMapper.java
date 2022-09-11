

package com.springsecurity.brewery.web.mappers;

import com.springsecurity.brewery.domain.BeerOrder;
import com.springsecurity.brewery.web.model.BeerOrderDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class}, componentModel = "spring")
public interface BeerOrderMapper {

    BeerOrderDto beerOrderToDto(BeerOrder beerOrder);

    BeerOrder dtoToBeerOrder(BeerOrderDto dto);
}
