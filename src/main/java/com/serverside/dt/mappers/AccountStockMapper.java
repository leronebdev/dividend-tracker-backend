package com.serverside.dt.mappers;

import com.serverside.dt.dtos.AccountStockDTO;
import com.serverside.dt.entities.AccountStock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountStockMapper {

    AccountStockDTO toDTO(AccountStock entity);

    AccountStock toEntity(AccountStockDTO dto);
}