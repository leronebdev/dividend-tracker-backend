package com.serverside.dt.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.serverside.dt.dtos.DividendEventDTO;
import com.serverside.dt.dtos.DividendEventRequestDTO;
import com.serverside.dt.dtos.DividendEventResponseDTO;
import com.serverside.dt.services.DividendEventService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dividend-events")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class DividendEventController {

    private final DividendEventService service;

    @GetMapping
    public ResponseEntity<List<DividendEventDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DividendEventDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<DividendEventDTO>> getByAccount(@PathVariable UUID accountId) {
        return ResponseEntity.ok(service.getByAccount(accountId));
    }

    @GetMapping("/stock/{stockId}")
    public ResponseEntity<List<DividendEventDTO>> getByStock(@PathVariable UUID stockId) {
        return ResponseEntity.ok(service.getByStock(stockId));
    }

    @PostMapping
    public ResponseEntity<DividendEventDTO> create(@RequestBody DividendEventDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DividendEventDTO> update(@PathVariable UUID id, @RequestBody DividendEventDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/all")
    public ResponseEntity<List<DividendEventResponseDTO>> getAllEvents() {
        return ResponseEntity.ok(service.getAllDividendEvents());
    }
    @PostMapping("/addPayoutDate")
    public ResponseEntity<Void> addPayoutDate(@RequestBody DividendEventRequestDTO dto) {
    	service.addPayoutDate(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
   

    @DeleteMapping("/{stockId}/payouts")
    public ResponseEntity<Void> removePayoutDate(
            @PathVariable("stockId") String stockId,
            @RequestParam("payoutDate") String payoutDate,
            @RequestParam("accountNumber") String accountNumber
    ) {
    	service.removePayoutDate(
                stockId,
                payoutDate,
                accountNumber
        );

        return ResponseEntity.ok().build();
    }


    

}