package com.serverside.dt.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serverside.dt.dtos.AccountDTO;
import com.serverside.dt.services.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDTO> getById(@PathVariable("accountNumber") String accountNumber) {
        return ResponseEntity.ok(service.getByAccountNumber(accountNumber));
    }

    @PostMapping
    public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    
    @PutMapping("/{accountNumber}")
    public ResponseEntity<AccountDTO> update(@PathVariable("accountNumber") String accountNumber, @RequestBody AccountDTO dto) {
        return ResponseEntity.ok(service.update(accountNumber, dto));
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<Void> delete(@PathVariable("accountNumber") String accountNumber) {
        service.delete(accountNumber);
        return ResponseEntity.noContent().build();
    }
}