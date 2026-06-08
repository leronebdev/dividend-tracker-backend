package com.serverside.dt.services;

import com.serverside.dt.dtos.AccountDTO;
import com.serverside.dt.entities.Account;
import com.serverside.dt.mappers.AccountMapper;
import com.serverside.dt.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository repo;

    @Mock
    private AccountMapper mapper;

    @InjectMocks
    private AccountService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_returnsList() {
        Account entity = new Account();
        AccountDTO dto = new AccountDTO();

        when(repo.findAll()).thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        List<AccountDTO> result = service.getAll();

        assertEquals(1, result.size());
    }

    @Test
    void getById_returnsAccount() {
        UUID id = UUID.randomUUID();
        Account entity = new Account();
        AccountDTO dto = new AccountDTO();

        when(repo.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        AccountDTO result = service.getById(id);

        assertNotNull(result);
    }

    @Test
    void create_savesAccount() {
        AccountDTO dto = new AccountDTO();
        Account entity = new Account();
        Account saved = new Account();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(saved);
        when(mapper.toDTO(saved)).thenReturn(dto);

        AccountDTO result = service.create(dto);

        assertNotNull(result);
        verify(repo).save(entity);
    }

    @Test
    void update_updatesAccount() {
    	String accountNumber = "1231";
        Account existing = new Account();
        AccountDTO dto = new AccountDTO();

        when(repo.findByAccountNumber(accountNumber)).thenReturn(Optional.of(existing));
        when(repo.save(existing)).thenReturn(existing);
        when(mapper.toDTO(existing)).thenReturn(dto);

        AccountDTO result = service.update(accountNumber, dto);

        assertNotNull(result);
        verify(repo).save(existing);
    }

    @Test
    void delete_removesAccount() {
       String accountNumber = "1231";
        when(repo.existsByAccountNumber	(accountNumber)).thenReturn(true);

        service.delete(accountNumber);

        verify(repo).deleteByAccountNumber(accountNumber);
    }
}