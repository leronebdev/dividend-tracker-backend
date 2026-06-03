package com.serverside.dt.services;

import com.serverside.dt.dtos.StockDTO;
import com.serverside.dt.entities.Stock;
import com.serverside.dt.mappers.StockMapper;
import com.serverside.dt.repositories.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockServiceTest {

    @Mock
    private StockRepository repo;

    @Mock
    private StockMapper mapper;

    @InjectMocks
    private StockService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_returnsList() {
        Stock stock = new Stock();
        StockDTO dto = new StockDTO();

        when(repo.findAll()).thenReturn(List.of(stock));
        when(mapper.toDTO(stock)).thenReturn(dto);

        List<StockDTO> result = service.getAll();

        assertEquals(1, result.size());
    }

    @Test
    void getById_returnsStock() {
        UUID id = UUID.randomUUID();
        Stock stock = new Stock();
        StockDTO dto = new StockDTO();

        when(repo.findById(id)).thenReturn(Optional.of(stock));
        when(mapper.toDTO(stock)).thenReturn(dto);

        StockDTO result = service.getById(id);

        assertNotNull(result);
    }

    @Test
    void create_savesStock() {
        StockDTO dto = new StockDTO();
        Stock entity = new Stock();
        Stock saved = new Stock();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(saved);
        when(mapper.toDTO(saved)).thenReturn(dto);

        StockDTO result = service.create(dto);

        assertNotNull(result);
        verify(repo).save(entity);
    }

    @Test
    void update_updatesStock() {
        UUID id = UUID.randomUUID();
        Stock existing = new Stock();
        StockDTO dto = new StockDTO();

        when(repo.findById(id)).thenReturn(Optional.of(existing));
        when(repo.save(existing)).thenReturn(existing);
        when(mapper.toDTO(existing)).thenReturn(dto);

        StockDTO result = service.update(id, dto);

        assertNotNull(result);
        verify(repo).save(existing);
    }

    @Test
    void delete_removesStock() {
        UUID id = UUID.randomUUID();
        when(repo.existsById(id)).thenReturn(true);

        service.delete(id);

        verify(repo).deleteById(id);
    }
}