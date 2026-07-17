package com.serverside.dt.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serverside.dt.entities.StockDividendDetails;

public interface StockDividendDetailsRepository extends JpaRepository<StockDividendDetails, UUID> {

    Optional<List<StockDividendDetails>> findByStockId(UUID stockId);
    Optional<StockDividendDetails> findTopByStockIdOrderByExDateDesc(UUID stockId);
    Optional<StockDividendDetails> findTopByStockIdAndExDateOrderByExDateDesc(UUID stockId, LocalDate exDate);
    boolean existsByStockIdAndPayoutDate(UUID stockId, LocalDate payoutDate);
    boolean existsByStockId(UUID stockId);
    void deleteByStockId(UUID stockId);
    StockDividendDetails findByStockIdAndPayoutDate(UUID stockId, LocalDate payoutDate);
    Optional<StockDividendDetails> findTopByStockIdOrderByLastUpdatedDateDesc(UUID stockId);

}
