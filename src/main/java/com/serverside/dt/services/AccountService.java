package com.serverside.dt.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

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
        entity.setCreatedDate(LocalDateTime.now());

        return mapper.toDTO(repo.save(entity));
    }

    public AccountDTO update(UUID id, AccountDTO dto) {
        Account existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found: " + id));

        existing.setAccountName(dto.getAccountName());
        existing.setAccountType(dto.getAccountType());
        // created_date should NOT change

        return mapper.toDTO(repo.save(existing));
    }

    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Account not found: " + id);
        }
        repo.deleteById(id);
    }
}