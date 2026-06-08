package com.serverside.dt.controllers;

import com.serverside.dt.dtos.AccountDTO;
import com.serverside.dt.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
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