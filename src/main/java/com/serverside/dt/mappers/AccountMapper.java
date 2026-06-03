package com.serverside.dt.mappers;

import com.serverside.dt.dtos.AccountDTO;
import com.serverside.dt.entities.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDTO toDTO(Account entity);

    Account toEntity(AccountDTO dto);
}