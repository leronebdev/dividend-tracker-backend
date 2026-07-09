package com.serverside.dt.services;

import com.serverside.dt.dtos.AccountDTO;
import com.serverside.dt.entities.Account;
import com.serverside.dt.entities.AccountType;
import com.serverside.dt.mappers.AccountMapper;
import com.serverside.dt.repositories.AccountRepository;
import com.serverside.dt.repositories.AccountTypeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repo;
    private final AccountTypeRepository accountTypeRepo;
    private final AccountMapper mapper;

    @Override
    public List<AccountDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public AccountDTO getById(UUID id) {
        Account account = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found: " + id));
        return mapper.toDTO(account);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDTO getByAccountNumber(String accountNumber) {
        Account account = repo.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new RuntimeException("Account not found: " + accountNumber));
        return mapper.toDTO(account);
    }
    @Override
    @Transactional(readOnly = true)
	public Account getEntityByAccountNumber(String accountNumber) {
    	 return  repo.findByAccountNumber(accountNumber)
                 .orElseThrow(() ->
                         new RuntimeException("Account not found: " + accountNumber));
	}
    @Override
    public AccountDTO create(AccountDTO dto) {

        if (repo.existsByAccountName(dto.getAccountName())) {
            throw new RuntimeException("Account already exists: " + dto.getAccountName());
        }

        AccountType type = accountTypeRepo.findById(dto.getAccountTypeId())
                .orElseThrow(() -> new RuntimeException("AccountType not found: " + dto.getAccountTypeId()));

        Account entity = mapper.toEntity(dto, type);

        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedDate(now);
        entity.setLastUpdatedDate(now);

        return mapper.toDTO(repo.save(entity));
    }

    @Override
    public AccountDTO update(String accountNumber, AccountDTO dto) {

        Account existing = repo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));

        AccountType type = accountTypeRepo.findById(dto.getAccountTypeId())
                .orElseThrow(() -> new RuntimeException("AccountType not found: " + dto.getAccountTypeId()));

        existing.setAccountName(dto.getAccountName());
        existing.setAccountType(type);
        existing.setLastUpdatedDate(LocalDateTime.now());

        return mapper.toDTO(repo.save(existing));
    }

    @Override
    @Transactional
    public void delete(String accountNumber) {
        if (!repo.existsByAccountNumber(accountNumber)) {
            throw new RuntimeException("Account not found: " + accountNumber);
        }
        repo.deleteByAccountNumber(accountNumber);
    }

    @Override
    @Transactional
    public List<Account> createAll(List<AccountDTO> dtos) {

        List<Account> saved = new ArrayList<>();

        for (AccountDTO dto : dtos) {

            AccountType type = accountTypeRepo.findById(dto.getAccountTypeId())
                    .orElseThrow(() -> new RuntimeException("AccountType not found: " + dto.getAccountTypeId()));

            Account account = Account.builder()
                    .accountNumber(dto.getAccountNumber())
                    .accountName(dto.getAccountName())
                    .accountType(type)
                    .createdDate(LocalDateTime.now())
                    .lastUpdatedDate(LocalDateTime.now())
                    .build();

            saved.add(repo.save(account));
        }

        return saved;
    }

	
}
