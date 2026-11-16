package com.serverside.dt.services;

import java.util.List;
import java.util.Optional;

import com.serverside.dt.entities.DividendFrequency;

public interface DividendFrequencyService {

    List<DividendFrequency> getAll();

    Optional<DividendFrequency> getById(Integer id);

    Optional<DividendFrequency> getByName(String name);

    Optional<DividendFrequency> getByPeriodsPerYear(Integer periodsPerYear);
}
