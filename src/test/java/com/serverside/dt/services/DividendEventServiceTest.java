package com.serverside.dt.services;

import com.serverside.dt.dtos.DividendEventDTO;
import com.serverside.dt.entities.DividendEvent;
import com.serverside.dt.mappers.DividendEventMapper;
import com.serverside.dt.repositories.DividendEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DividendEventServiceTest {

    @Mock
    private DividendEventRepository repo;

    @Mock
    private DividendEventMapper mapper;

    @InjectMocks
    private DividendEventService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_returnsList() {
        DividendEvent entity = new DividendEvent();
        DividendEventDTO dto = new DividendEventDTO();

        when(repo.findAll()).thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        List<DividendEventDTO> result = service.getAll();

        assertEquals(1, result.size());
    }

    @Test
    void getById_returnsEvent() {
        UUID id = UUID.randomUUID();
        DividendEvent entity = new DividendEvent();
        DividendEventDTO dto = new DividendEventDTO();

        when(repo.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        DividendEventDTO result = service.getById(id);

        assertNotNull(result);
    }

    @Test
    void getByAccount_returnsList() {
        UUID accountId = UUID.randomUUID();
        DividendEvent entity = new DividendEvent();
        DividendEventDTO dto = new DividendEventDTO();

        when(repo.findByAccountId(accountId)).thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        List<DividendEventDTO> result = service.getByAccount(accountId);

        assertEquals(1, result.size());
    }

    @Test
    void create_savesEvent() {
        DividendEventDTO dto = new DividendEventDTO();
        DividendEvent entity = new DividendEvent();
        DividendEvent saved = new DividendEvent();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(saved);
        when(mapper.toDTO(saved)).thenReturn(dto);

        DividendEventDTO result = service.create(dto);

        assertNotNull(result);
        verify(repo).save(entity);
    }

    @Test
    void update_updatesEvent() {
        UUID id = UUID.randomUUID();
        DividendEvent existing = new DividendEvent();
        DividendEventDTO dto = new DividendEventDTO();

        when(repo.findById(id)).thenReturn(Optional.of(existing));
        when(repo.save(existing)).thenReturn(existing);
        when(mapper.toDTO(existing)).thenReturn(dto);

        DividendEventDTO result = service.update(id, dto);

        assertNotNull(result);
        verify(repo).save(existing);
    }

    @Test
    void delete_removesEvent() {
        UUID id = UUID.randomUUID();
        when(repo.existsById(id)).thenReturn(true);

        service.delete(id);

        verify(repo).deleteById(id);
    }
}