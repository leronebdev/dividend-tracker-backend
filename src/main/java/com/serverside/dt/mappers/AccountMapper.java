package com.serverside.dt.mappers;

import org.springframework.stereotype.Component;

import com.serverside.dt.dtos.AccountDTO;
import com.serverside.dt.entities.Account;
import com.serverside.dt.entities.AccountType;
import com.serverside.dt.entities.TaxRule;

@Component
public class AccountMapper {

    public AccountDTO toDTO(Account entity) {
        if (entity == null) return null;

        AccountType type = entity.getAccountType();
        TaxRule rule = type != null ? type.getTaxRule() : null;

        return AccountDTO.builder()
                .id(entity.getId())
                .accountNumber(entity.getAccountNumber())
                .accountName(entity.getAccountName())

                .accountTypeId(type != null ? type.getId() : null)
                .accountTypeName(type != null ? type.getTypeName() : null)

                .taxRuleId(rule != null ? rule.getId() : null)
                .taxRuleName(rule != null ? rule.getRuleName() : null)

                .createdDate(entity.getCreatedDate())
                .lastUpdatedDate(entity.getLastUpdatedDate())
                .build();
    }

    public Account toEntity(AccountDTO dto, AccountType accountType) {
        if (dto == null) return null;

        return Account.builder()
                .id(dto.getId())
                .accountNumber(dto.getAccountNumber())
                .accountName(dto.getAccountName())
                .accountType(accountType)
                .createdDate(dto.getCreatedDate())
                .lastUpdatedDate(dto.getLastUpdatedDate())
                .build();
    }
}
