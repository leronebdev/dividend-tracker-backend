package com.serverside.dt.repositories;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serverside.dt.entities.StockDividendDetails;

public interface StockDividendDetailsRepository extends JpaRepository<StockDividendDetails, UUID> {

    Optional<StockDividendDetails> findByStockId(UUID stockId);
    Optional<StockDividendDetails> findTopByStockIdOrderByExDateDesc(UUID stockId);
    boolean existsByStockId(UUID stockId);
    void deleteByStockId(UUID stockId);
    Optional<StockDividendDetails> findByStockIdAndPayoutDate(UUID stockId, LocalDate payoutDate);
    Optional<StockDividendDetails> findTopByStockIdOrderByLastUpdatedDateDesc(UUID stockId);

}
