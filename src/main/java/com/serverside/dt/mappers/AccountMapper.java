package com.serverside.dt.mappers;

import org.mapstruct.Mapper;

import com.serverside.dt.dtos.AccountDTO;
import com.serverside.dt.entities.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDTO toDTO(Account entity);

    Account toEntity(AccountDTO dto);
}