package com.serverside.dt.repositories;

import com.serverside.dt.entities.AccountStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccountStockRepository extends JpaRepository<AccountStock, UUID> {

    List<AccountStock> findByAccountId(UUID accountId);
}
