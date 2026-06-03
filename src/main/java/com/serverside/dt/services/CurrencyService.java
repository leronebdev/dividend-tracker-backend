package com.serverside.dt.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.serverside.dt.dtos.CurrencyDTO;
import com.serverside.dt.entities.Currency;
import com.serverside.dt.mappers.CurrencyMapper;
import com.serverside.dt.repositories.CurrencyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository repo;
    private final CurrencyMapper mapper;

    public List<CurrencyDTO> getAll() {
        return repo.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    public CurrencyDTO getById(Integer id) {
        Currency entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Currency not found: " + id));
        return mapper.toDTO(entity);
    }

    public CurrencyDTO create(CurrencyDTO dto) {
        Currency entity = mapper.toEntity(dto);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastUpdatedDate(LocalDateTime.now());
        return mapper.toDTO(repo.save(entity));
    }

    public CurrencyDTO update(Integer id, CurrencyDTO dto) {
        Currency existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Currency not found: " + id));

        existing.setCode(dto.getCode());
        existing.setName(dto.getName());
        existing.setSymbol(dto.getSymbol());
        existing.setExchangeRateToCad(dto.getExchangeRateToCad());
        existing.setLastUpdatedDate(LocalDateTime.now());

        return mapper.toDTO(repo.save(existing));
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}