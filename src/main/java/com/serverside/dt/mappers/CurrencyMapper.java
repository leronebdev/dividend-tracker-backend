package com.serverside.dt.mappers;

import org.mapstruct.Mapper;

import com.serverside.dt.dtos.CurrencyDTO;
import com.serverside.dt.entities.Currency;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {
    CurrencyDTO toDTO(Currency entity);
    Currency toEntity(CurrencyDTO dto);
}
