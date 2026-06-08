package com.serverside.dt.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serverside.dt.entities.DividendEvent;

public interface DividendEventRepository extends JpaRepository<DividendEvent, UUID> {

    List<DividendEvent> findByAccountId(UUID accountId);

    List<DividendEvent> findByStockId(UUID stockId);
    List<DividendEvent> findAll();

    void deleteByStockId(UUID stockId);
}