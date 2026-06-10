package com.serverside.dt.controllers;


import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serverside.dt.dtos.AccountStockDTO;
import com.serverside.dt.dtos.StockAccountProjectionDTO;
import com.serverside.dt.dtos.StockRequestDTO;
import com.serverside.dt.services.AccountStockService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stockAccounts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AccountStockController {

    private final AccountStockService accountStockService;

    @GetMapping
    public ResponseEntity<List<AccountStockDTO>> getAll() {
        return ResponseEntity.ok(accountStockService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountStockDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(accountStockService.getById(id));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<AccountStockDTO>> getByAccount(@PathVariable UUID accountId) {
        return ResponseEntity.ok(accountStockService.getByAccountId(accountId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStock(
            @PathVariable("id") String id,
            @RequestBody StockRequestDTO dto
    ) {
    	accountStockService.updateStockAccount(dto);
        return ResponseEntity.ok().build();
    }
    @PostMapping
    public ResponseEntity<Void> createStock(@RequestBody StockRequestDTO dto) {
        accountStockService.createNewStockAccount(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{stockId}/{accountNumber}")
    public ResponseEntity<Void> delete(@PathVariable("stockId") String stockId, @PathVariable("accountNumber") String accountNumber) {
        accountStockService.deleteStockAccount(stockId,accountNumber);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/all")
    public List<StockAccountProjectionDTO> getAllStockAccounts() {
        return accountStockService.getAllStockAccountsFromView();

    }

}

