package com.serverside.dt.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serverside.dt.dtos.DividendDetailTO;
import com.serverside.dt.services.DividendDetailService;

@RestController
@RequestMapping("/api/dividend-details")
public class DividendDetailController {

	private final DividendDetailService service;

	public DividendDetailController(DividendDetailService service) {
		this.service = service;
	}

	// ----------------------------------------------------
	// CREATE NEW DIVIDEND DETAIL
	// ----------------------------------------------------
	@PostMapping("/add")
	public ResponseEntity<DividendDetailTO> create(@RequestBody DividendDetailTO detail) {
		return ResponseEntity.ok(service.create(detail));
	}

	@GetMapping("/stock/{stockId}/account/{accountNumber}")
	public ResponseEntity<Map<String, List<DividendDetailTO>>> getAllDividendDetails(
			@PathVariable("stockId") String stockId, @PathVariable("accountNumber") String accountNumber) {
		return ResponseEntity.ok(service.getAllDividendDetails(accountNumber, stockId));
	}

	// ----------------------------------------------------
	// DELETE ONE DETAIL
	// ----------------------------------------------------
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
