package com.serverside.dt.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	@PutMapping("/update")
	public ResponseEntity<Void> update(@RequestBody DividendDetailTO detail) {
		service.update(detail);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/stock/{stockId}/account/{accountNumber}")
	public ResponseEntity<Map<String, List<DividendDetailTO>>> getAllDividendDetails(
			@PathVariable("stockId") String stockId, @PathVariable("accountNumber") String accountNumber) {
		return ResponseEntity.ok(service.getAllDividendDetails(accountNumber, stockId));
	}

	// ----------------------------------------------------
	// DELETE ONE DETAIL
	// ----------------------------------------------------("stockId")
	@DeleteMapping("/dividendDetailId/{dividendDetailId}")
	public ResponseEntity<Void> delete(@PathVariable("dividendDetailId") String id,@RequestParam("stockId") String stockId, @RequestParam("accountNumber") String accountNumber) {
		service.delete(id,stockId,accountNumber);
		return ResponseEntity.noContent().build();
	}
}
