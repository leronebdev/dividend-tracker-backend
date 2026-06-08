package com.serverside.dt.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serverside.dt.entities.StockDividendDetails;

public interface StockDividendDetailsRepository extends JpaRepository<StockDividendDetails, UUID> {

    Optional<StockDividendDetails> findByStockId(UUID stockId);

    boolean existsByStockId(UUID stockId);
    void deleteByStockId(UUID stockId);
}
