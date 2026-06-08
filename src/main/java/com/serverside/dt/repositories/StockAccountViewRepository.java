package com.serverside.dt.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.serverside.dt.entities.StockAccountView;
import com.serverside.dt.repositories.projections.StockAccountProjection;

public interface StockAccountViewRepository extends Repository<StockAccountView, UUID> {

    @Query(
        value = "SELECT * FROM vw_stock_request",
        nativeQuery = true
    )
    List<StockAccountProjection> findAllStockAccounts();
}
