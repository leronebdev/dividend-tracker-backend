package com.serverside.dt.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.serverside.dt.dtos.DividendEventDTO;
import com.serverside.dt.entities.DividendEvent;
import com.serverside.dt.mappers.DividendEventMapper;
import com.serverside.dt.repositories.AccountRepository;
import com.serverside.dt.repositories.DividendEventRepository;
import com.serverside.dt.repositories.StockDividendDetailsRepository;
import com.serverside.dt.repositories.StockRepository;
import com.serverside.dt.services.DividendEventService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DividendEventServiceImpl implements DividendEventService {

    private final DividendEventRepository repo;
    private final DividendEventMapper mapper;

    private final AccountRepository accountRepo;
    private final StockRepository stockRepo;
    private final StockDividendDetailsRepository stockDividendDetailsRepo;

    @Override
    public List<DividendEventDTO> getAll() {
        return repo.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public DividendEventDTO getById(UUID id) {
        DividendEvent entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("DividendEvent not found: " + id));
        return mapper.toDTO(entity);
    }

    @Override
    public List<DividendEventDTO> getByAccount(UUID accountId) {
        return repo.findByAccountId(accountId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<DividendEventDTO> getByStock(UUID stockId) {
        return repo.findByStockId(stockId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public DividendEventDTO create(DividendEventDTO dto) {

        // Validate FK references
        if (!accountRepo.existsById(dto.getAccountId())) {
            throw new RuntimeException("Account not found: " + dto.getAccountId());
        }

        if (!stockRepo.existsById(dto.getStockId())) {
            throw new RuntimeException("Stock not found: " + dto.getStockId());
        }

        if (!stockDividendDetailsRepo.existsById(dto.getStockDividendDetailId())) {
            throw new RuntimeException("StockDividendDetails not found: " + dto.getStockDividendDetailId());
        }

        DividendEvent entity = mapper.toEntity(dto);

        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedDate(now);
        entity.setLastUpdatedDate(now);

        DividendEvent saved = repo.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public DividendEventDTO update(UUID id, DividendEventDTO dto) {
        DividendEvent existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("DividendEvent not found: " + id));

        // Validate FK references
        if (!accountRepo.existsById(dto.getAccountId())) {
            throw new RuntimeException("Account not found: " + dto.getAccountId());
        }

        if (!stockRepo.existsById(dto.getStockId())) {
            throw new RuntimeException("Stock not found: " + dto.getStockId());
        }

        if (!stockDividendDetailsRepo.existsById(dto.getStockDividendDetailId())) {
            throw new RuntimeException("StockDividendDetails not found: " + dto.getStockDividendDetailId());
        }

        existing.setAccountId(dto.getAccountId());
        existing.setStockId(dto.getStockId());
        existing.setStockDividendDetailId(dto.getStockDividendDetailId());
        existing.setSharesAtEvent(dto.getSharesAtEvent());
        existing.setTotalAmount(dto.getTotalAmount());
        existing.setFxRate(dto.getFxRate());
        existing.setLastUpdatedDate(LocalDateTime.now());

        DividendEvent saved = repo.save(existing);
        return mapper.toDTO(saved);
    }

    @Override
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("DividendEvent not found: " + id);
        }
        repo.deleteById(id);
    }
}
