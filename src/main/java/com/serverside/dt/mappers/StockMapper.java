package com.serverside.dt.mappers;

import com.serverside.dt.dtos.StockDTO;
import com.serverside.dt.entities.Stock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockMapper {

    StockDTO toDTO(Stock entity);

    Stock toEntity(StockDTO dto);
}