package com.serverside.dt.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.serverside.dt.dtos.DividendEventDTO;
import com.serverside.dt.dtos.DividendEventRequestDTO;
import com.serverside.dt.dtos.DividendEventResponseDTO;
import com.serverside.dt.entities.DividendEvent;

public interface DividendEventService {

    List<DividendEventDTO> getAll();

    DividendEventDTO getById(UUID id);

    List<DividendEventDTO> getByAccount(UUID accountId);

    List<DividendEventDTO> getByStock(UUID stockId);

    DividendEventDTO create(DividendEventDTO dto);

    DividendEventDTO update(UUID id, DividendEventDTO dto);
    List<DividendEventResponseDTO> getAllDividendEvents();
    DividendEvent saveDividendEvent(DividendEvent dividendEvent);
    void delete(UUID id);
    void addPayoutDate(DividendEventRequestDTO dto);
    void removePayoutDate(String stockId, String payoutDate, String accountId);

	void delete(String stockId, LocalDate payoutDate);
}
