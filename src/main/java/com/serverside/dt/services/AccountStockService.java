package com.serverside.dt.services;

import java.util.List;
import java.util.UUID;

import com.serverside.dt.dtos.AccountStockDTO;

public interface AccountStockService {
    List<AccountStockDTO> getAll();
    AccountStockDTO getById(UUID id);
    List<AccountStockDTO> getByAccountId(UUID accountId);
    AccountStockDTO create(AccountStockDTO dto);
    AccountStockDTO update(UUID id, AccountStockDTO dto);
    void delete(UUID id);
}

