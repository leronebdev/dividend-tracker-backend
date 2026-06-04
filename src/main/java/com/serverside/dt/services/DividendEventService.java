package com.serverside.dt.services;

import java.util.List;
import java.util.UUID;

import com.serverside.dt.dtos.DividendEventDTO;

public interface DividendEventService {

    List<DividendEventDTO> getAll();

    DividendEventDTO getById(UUID id);

    List<DividendEventDTO> getByAccount(UUID accountId);

    List<DividendEventDTO> getByStock(UUID stockId);

    DividendEventDTO create(DividendEventDTO dto);

    DividendEventDTO update(UUID id, DividendEventDTO dto);

    void delete(UUID id);
}
