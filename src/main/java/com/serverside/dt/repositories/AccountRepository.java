package com.serverside.dt.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serverside.dt.entities.Account;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    boolean existsByAccountName(String accountName);
}