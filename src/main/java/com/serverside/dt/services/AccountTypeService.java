package com.serverside.dt.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.serverside.dt.dtos.AccountTypeDTO;
import com.serverside.dt.mappers.AccountTypeMapper;
import com.serverside.dt.repositories.AccountTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountTypeService {

    private final AccountTypeRepository repo;
    private final AccountTypeMapper mapper;

    public List<AccountTypeDTO> getAccountTypes() {
        return repo.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}
