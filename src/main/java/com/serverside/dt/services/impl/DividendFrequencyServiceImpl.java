package com.serverside.dt.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.serverside.dt.entities.DividendFrequency;
import com.serverside.dt.repositories.DividendFrequencyRepository;
import com.serverside.dt.services.DividendFrequencyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DividendFrequencyServiceImpl
        implements DividendFrequencyService {

    private final DividendFrequencyRepository dividendFrequencyRepository;

    @Override
    public List<DividendFrequency> getAll() {

        return dividendFrequencyRepository.findAll();
    }

    @Override
    public Optional<DividendFrequency> getById(Integer id) {

        return dividendFrequencyRepository.findById(id);
    }

    @Override
    public Optional<DividendFrequency> getByName(String name) {

        return dividendFrequencyRepository.findByName(name);
    }

    @Override
    public Optional<DividendFrequency> getByPeriodsPerYear(
            Integer periodsPerYear) {

        return dividendFrequencyRepository.findByPeriodsPerYear(
                periodsPerYear);
    }
}