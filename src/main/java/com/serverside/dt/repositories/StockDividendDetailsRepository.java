package com.serverside.dt.repositories;

import com.serverside.dt.entities.StockDividendDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StockDividendDetailsRepository extends JpaRepository<StockDividendDetails, UUID> {

    List<StockDividendDetails> findByStockId(UUID stockId);

    boolean existsByStockId(UUID stockId);
}
