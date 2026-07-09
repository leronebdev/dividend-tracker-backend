package com.serverside.dt.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serverside.dt.dtos.AccountTypeDTO;
import com.serverside.dt.services.AccountTypeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/account-types")
@RequiredArgsConstructor
public class AccountTypeController {

    private final AccountTypeService service;

    @GetMapping
    public List<AccountTypeDTO> getAll() {
        return service.getAccountTypes();
    }
}
