package com.serverside.dt.mappers;

import com.serverside.dt.dtos.StockDividendDetailsDTO;
import com.serverside.dt.entities.StockDividendDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockDividendDetailsMapper {

    StockDividendDetailsDTO toDto(StockDividendDetails entity);

    StockDividendDetails toEntity(StockDividendDetailsDTO dto);
}
