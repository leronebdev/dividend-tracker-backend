package com.serverside.dt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serverside.dt.entities.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
	

}
