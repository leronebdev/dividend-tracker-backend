package com.serverside.dt.mappers;

import org.mapstruct.Mapper;

import com.serverside.dt.dtos.DividendDetailTO;
import com.serverside.dt.dtos.StockDividendDetailsDTO;
import com.serverside.dt.entities.StockDividendDetails;

@Mapper(componentModel = "spring")
public interface StockDividendDetailsMapper {

    StockDividendDetailsDTO toDto(StockDividendDetails entity);

    StockDividendDetails toEntity(StockDividendDetailsDTO dto);
    
    StockDividendDetailsDTO fromTO(DividendDetailTO dto);
}
