package com.serverside.dt.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serverside.dt.entities.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    Optional<Currency> findByCode(String code);
}