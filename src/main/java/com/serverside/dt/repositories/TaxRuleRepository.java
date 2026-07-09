package com.serverside.dt.repositories;

import com.serverside.dt.entities.TaxRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRuleRepository extends JpaRepository<TaxRule, Integer> {

    TaxRule findByRuleName(String ruleName);    
}
