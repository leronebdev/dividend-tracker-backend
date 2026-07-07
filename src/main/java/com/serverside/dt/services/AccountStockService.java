package com.serverside.dt.services;

import java.util.List;
import java.util.UUID;

import com.serverside.dt.dtos.AccountStockDTO;
import com.serverside.dt.dtos.StockAccountProjectionDTO;
import com.serverside.dt.dtos.StockRequestDTO;


public interface AccountStockService {
    List<AccountStockDTO> getAll();
    List<AccountStockDTO>getByStyockId(String stockId);
    AccountStockDTO getById(UUID id);
    List<AccountStockDTO> getByAccountId(UUID accountId);
    AccountStockDTO create(AccountStockDTO dto);
    AccountStockDTO getByStockIdAndAccountId(UUID stockId, UUID accountId);
    AccountStockDTO update(UUID id, AccountStockDTO dto);
    void delete(UUID id);
	void createNewStockAccount(StockRequestDTO dto);
	List<StockAccountProjectionDTO> getAllStockAccountsFromView();
	void updateStockAccount(StockRequestDTO dto);
	 void deleteStockAccount(String stockId, String accountNumber);
}

