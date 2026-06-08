package com.serverside.dt.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serverside.dt.dtos.AccountDTO;
import com.serverside.dt.entities.Account;
import com.serverside.dt.mappers.AccountMapper;
import com.serverside.dt.repositories.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repo;
    private final AccountMapper mapper;

    public List<AccountDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public AccountDTO getById(UUID id) {
        Account account = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found: " + id));
        return mapper.toDTO(account);
    }

    public AccountDTO create(AccountDTO dto) {
        if (repo.existsByAccountName(dto.getAccountName())) {
            throw new RuntimeException("Account already exists: " + dto.getAccountName());
        }

        Account entity = mapper.toEntity(dto);
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedDate(now);
        entity.setLastUpdatedDate(now);
        return mapper.toDTO(repo.save(entity));
    }

    public AccountDTO update(String accountNumber, AccountDTO dto) {
        Account existing = repo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));

        existing.setAccountName(dto.getAccountName());
        existing.setAccountType(dto.getAccountType());        
        existing.setLastUpdatedDate(LocalDateTime.now());
        // created_date should NOT change

        return mapper.toDTO(repo.save(existing));
    }

    @Transactional
    public void delete(String accountNumber) {
        if (!repo.existsByAccountNumber(accountNumber)) {
            throw new RuntimeException("Account not found: " + accountNumber);
        }
        repo.deleteByAccountNumber(accountNumber);
    }
    @Transactional(readOnly = true)
    public AccountDTO getByAccountNumber(String accountNumber) {
        Account account = repo.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new RuntimeException("Account not found: " + accountNumber));
        return mapper.toDTO(account);
    }
    @Transactional
    public List<Account> createAll(List<AccountDTO> dtos) {
        List<Account> saved = new ArrayList<>();

        for (AccountDTO dto : dtos) {
            Account account = new Account();
            account.setAccountNumber(dto.getAccountNumber());
            account.setAccountName(dto.getAccountName());
            account.setAccountType(dto.getAccountType());
            account.setCreatedDate(LocalDateTime.now());

            // Save one by one
            Account result = repo.save(account);
            saved.add(result);
        }

        // If any save() throws, the entire transaction rolls back
        return saved;
    }
}