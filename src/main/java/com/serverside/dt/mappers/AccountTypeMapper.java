package com.serverside.dt.mappers;

import org.springframework.stereotype.Component;
import com.serverside.dt.dtos.AccountTypeDTO;
import com.serverside.dt.entities.AccountType;

@Component
public class AccountTypeMapper {

    public AccountTypeDTO toDTO(AccountType entity) {
        if (entity == null) return null;

        return AccountTypeDTO.builder()
                .accountTypeId(entity.getId())
                .typeName(entity.getTypeName())
                .build();
    }
}
