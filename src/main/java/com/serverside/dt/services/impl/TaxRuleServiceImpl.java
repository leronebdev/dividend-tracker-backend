package com.serverside.dt.services.impl;

import org.springframework.stereotype.Service;

import com.serverside.dt.entities.TaxRule;
import com.serverside.dt.repositories.TaxRuleRepository;
import com.serverside.dt.services.TaxRuleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaxRuleServiceImpl implements TaxRuleService {

    private final TaxRuleRepository taxRepo;

    @Override
    public TaxRule getById(Integer id) {
        return taxRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("TaxRule not found: " + id));
    }
}
