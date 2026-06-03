package com.serverside.dt.services;

import com.serverside.dt.dtos.DividendEventDTO;
import com.serverside.dt.entities.DividendEvent;
import com.serverside.dt.mappers.DividendEventMapper;
import com.serverside.dt.repositories.DividendEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DividendEventService {

    private final DividendEventRepository repo;
    private final DividendEventMapper mapper;

    public List<DividendEventDTO> getAll() {
        return repo.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    public DividendEventDTO getById(UUID id) {
        DividendEvent event = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("DividendEvent not found: " + id));
        return mapper.toDTO(event);
    }

    public List<DividendEventDTO> getByAccount(UUID accountId) {
        return repo.findByAccountId(accountId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    public List<DividendEventDTO> getByStock(UUID stockId) {
        return repo.findByStockId(stockId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    public DividendEventDTO create(DividendEventDTO dto) {
        DividendEvent entity = mapper.toEntity(dto);
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastUpdatedDate(LocalDateTime.now());
        return mapper.toDTO(repo.save(entity));
    }

    public DividendEventDTO update(UUID id, DividendEventDTO dto) {
        DividendEvent existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("DividendEvent not found: " + id));

        existing.setAccountId(dto.getAccountId());
        existing.setStockId(dto.getStockId());
        existing.setShares(dto.getShares());
        existing.setDividendPerShare(dto.getDividendPerShare());
        existing.setAmount(dto.getAmount());
        existing.setCurrency(dto.getCurrency());
        existing.setPayoutDate(dto.getPayoutDate());
        existing.setFrequencyId(dto.getFrequencyId());
        existing.setSource(dto.getSource());
        existing.setLastUpdatedDate(LocalDateTime.now());

        return mapper.toDTO(repo.save(existing));
    }

    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("DividendEvent not found: " + id);
        }
        repo.deleteById(id);
    }
}