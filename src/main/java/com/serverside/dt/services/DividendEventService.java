package com.serverside.dt.services;

import java.util.List;
import java.util.UUID;

import com.serverside.dt.dtos.DividendEventDTO;
import com.serverside.dt.dtos.DividendEventRequestDTO;
import com.serverside.dt.dtos.DividendEventResponseDTO;

public interface DividendEventService {

    List<DividendEventDTO> getAll();

    DividendEventDTO getById(UUID id);

    List<DividendEventDTO> getByAccount(UUID accountId);

    List<DividendEventDTO> getByStock(UUID stockId);

    DividendEventDTO create(DividendEventDTO dto);

    DividendEventDTO update(UUID id, DividendEventDTO dto);
    List<DividendEventResponseDTO> getAllDividendEvents();

    void delete(UUID id);
    void addPayoutDate(DividendEventRequestDTO dto);
}
