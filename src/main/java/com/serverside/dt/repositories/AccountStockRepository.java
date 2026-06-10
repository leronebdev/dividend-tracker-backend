package com.serverside.dt.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serverside.dt.entities.AccountStock;

public interface AccountStockRepository extends JpaRepository<AccountStock, UUID> {

    List<AccountStock> findByAccountId(UUID accountId);
    Optional<AccountStock> findByAccountIdAndStockId(UUID accountId, UUID stockId);
    void deleteByStockIdAndAccountId(UUID stockId,UUID accountId);
    
}
