package com.serverside.dt.services;

import com.serverside.dt.dtos.StockDTO;
import com.serverside.dt.entities.Stock;
import com.serverside.dt.mappers.StockMapper;
import com.serverside.dt.repositories.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository repo;
    private final StockMapper mapper;

    public List<StockDTO> getAll() {
        return repo.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    public StockDTO getById(UUID id) {
        Stock entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found: " + id));
        return mapper.toDTO(entity);
    }

    public StockDTO create(StockDTO dto) {
        Stock entity = mapper.toEntity(dto);
        entity.setId(UUID.randomUUID());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastUpdatedDate(LocalDateTime.now());
        return mapper.toDTO(repo.save(entity));
    }

    public StockDTO update(UUID id, StockDTO dto) {
        Stock existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found: " + id));

        existing.setTicker(dto.getTicker());
        existing.setCompanyName(dto.getCompanyName());
        //existing.setSectorId(dto.getSectorId());
        //existing.setExDate(dto.getExDate());
        existing.setCurrencyCode(dto.getCurrencyCode());
        existing.setLastUpdatedDate(LocalDateTime.now());

        return mapper.toDTO(repo.save(existing));
    }

    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Stock not found: " + id);
        }
        repo.deleteById(id);
    }
}