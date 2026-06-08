package com.serverside.dt.repositories;

import com.serverside.dt.entities.DividendFrequency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DividendFrequencyRepository extends JpaRepository<DividendFrequency, Integer> {
   
    // If you also want lookup by name (optional)
    Optional<DividendFrequency> findByName(String name);
    Optional<DividendFrequency> findByPeriodsPerYear(Integer payPeriodsPerYear);
}
