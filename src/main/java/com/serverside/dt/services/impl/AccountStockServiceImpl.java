package com.serverside.dt.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.serverside.dt.dtos.AccountStockDTO;
import com.serverside.dt.entities.AccountStock;
import com.serverside.dt.mappers.AccountStockMapper;
import com.serverside.dt.repositories.AccountStockRepository;
import com.serverside.dt.services.AccountStockService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountStockServiceImpl implements AccountStockService {

    private final AccountStockRepository repository;
    private final AccountStockMapper mapper;

    @Override
    public List<AccountStockDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public AccountStockDTO getById(UUID id) {
        AccountStock entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("AccountStock not found: " + id));
        return mapper.toDTO(entity);
    }

    @Override
    public List<AccountStockDTO> getByAccountId(UUID accountId) {
        return repository.findByAccountId(accountId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
	public AccountStockDTO create(AccountStockDTO dto) {
    	dto.setCreatedDate(LocalDateTime.now());
    	dto.setLastUpdatedDate(LocalDateTime.now());
        AccountStock entity = mapper.toEntity(dto);
        AccountStock saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public AccountStockDTO update(UUID id, AccountStockDTO dto) {
        AccountStock existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("AccountStock not found: " + id));

        existing.setAccountId(dto.getAccountId());
        existing.setStockId(dto.getStockId());
        existing.setShares(dto.getShares());
        existing.setAveragePrice(dto.getAveragePrice());

        AccountStock updated = repository.save(existing);
        return mapper.toDTO(updated);
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("AccountStock not found: " + id);
        }
        repository.deleteById(id);
    }
}
