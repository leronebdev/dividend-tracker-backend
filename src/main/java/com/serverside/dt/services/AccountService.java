package com.serverside.dt.services;

import java.util.List;
import java.util.UUID;

import com.serverside.dt.dtos.AccountDTO;
import com.serverside.dt.entities.Account;

public interface AccountService {

    List<AccountDTO> getAll();

    AccountDTO getById(UUID id);

    AccountDTO getByAccountNumber(String accountNumber);
    Account getEntityByAccountNumber(String accountNumber);

    AccountDTO create(AccountDTO dto);

    AccountDTO update(String accountNumber, AccountDTO dto);

    void delete(String accountNumber);

    List<Account> createAll(List<AccountDTO> dtos);
}
