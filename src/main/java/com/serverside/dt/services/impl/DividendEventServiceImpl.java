package com.serverside.dt.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serverside.dt.dtos.DividendEventDTO;
import com.serverside.dt.dtos.DividendEventRequestDTO;
import com.serverside.dt.dtos.DividendEventResponseDTO;
import com.serverside.dt.entities.Account;
import com.serverside.dt.entities.AccountStock;
import com.serverside.dt.entities.DividendEvent;
import com.serverside.dt.entities.DividendFrequency;
import com.serverside.dt.entities.Stock;
import com.serverside.dt.entities.StockDividendDetails;
import com.serverside.dt.mappers.DividendEventMapper;
import com.serverside.dt.repositories.AccountRepository;
import com.serverside.dt.repositories.AccountStockRepository;
import com.serverside.dt.repositories.DividendEventRepository;
import com.serverside.dt.repositories.DividendFrequencyRepository;
import com.serverside.dt.repositories.StockDividendDetailsRepository;
import com.serverside.dt.repositories.StockRepository;
import com.serverside.dt.services.DividendEventService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DividendEventServiceImpl implements DividendEventService {

    private final DividendEventRepository repo;
    private final DividendEventMapper mapper;

    private final AccountRepository accountRepo;
    private final AccountStockRepository accountStockRepo;
    private final StockRepository stockRepo;
    private final DividendFrequencyRepository dividendFrequencyRepo;
    private final StockDividendDetailsRepository stockDividendDetailsRepo;
    private final DividendEventRepository dividendEventRepository;

    

    @Override
    public List<DividendEventDTO> getAll() {
        return repo.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public DividendEventDTO getById(UUID id) {
        DividendEvent entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("DividendEvent not found: " + id));
        return mapper.toDTO(entity);
    }

    @Override
    public List<DividendEventDTO> getByAccount(UUID accountId) {
        return repo.findByAccountId(accountId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<DividendEventDTO> getByStock(UUID stockId) {
        return repo.findByStockId(stockId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public DividendEventDTO create(DividendEventDTO dto) {

        // Validate FK references
        if (!accountRepo.existsById(dto.getAccountId())) {
            throw new RuntimeException("Account not found: " + dto.getAccountId());
        }

        if (!stockRepo.existsById(dto.getStockId())) {
            throw new RuntimeException("Stock not found: " + dto.getStockId());
        }

        if (!stockDividendDetailsRepo.existsById(dto.getStockDividendDetailId())) {
            throw new RuntimeException("StockDividendDetails not found: " + dto.getStockDividendDetailId());
        }

        DividendEvent entity = mapper.toEntity(dto);

        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedDate(now);
        entity.setLastUpdatedDate(now);

        DividendEvent saved = repo.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public DividendEventDTO update(UUID id, DividendEventDTO dto) {
        DividendEvent existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("DividendEvent not found: " + id));

        // Validate FK references
        if (!accountRepo.existsById(dto.getAccountId())) {
            throw new RuntimeException("Account not found: " + dto.getAccountId());
        }

        if (!stockRepo.existsById(dto.getStockId())) {
            throw new RuntimeException("Stock not found: " + dto.getStockId());
        }

        if (!stockDividendDetailsRepo.existsById(dto.getStockDividendDetailId())) {
            throw new RuntimeException("StockDividendDetails not found: " + dto.getStockDividendDetailId());
        }

        existing.setAccountId(dto.getAccountId());
        existing.setStockId(dto.getStockId());
        existing.setStockDividendDetailId(dto.getStockDividendDetailId());
        existing.setSharesAtEvent(dto.getSharesAtEvent());
        existing.setTotalAmount(dto.getTotalAmount());
        existing.setFxRate(dto.getFxRate());
        existing.setLastUpdatedDate(LocalDateTime.now());

        DividendEvent saved = repo.save(existing);
        return mapper.toDTO(saved);
    }

    @Override
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("DividendEvent not found: " + id);
        }
        repo.deleteById(id);
    }
    public List<DividendEventResponseDTO> getAllDividendEvents() {

        List<DividendEvent> events = dividendEventRepository.findAll();

        List<DividendEventResponseDTO>dividendEventResponse = events.stream().map(event -> {

            UUID stockId = event.getStockId();
            UUID accountId = event.getAccountId();
            UUID detailId = event.getStockDividendDetailId();

            Stock stock = stockRepo.findById(stockId)
                    .orElseThrow(() -> new IllegalStateException("Stock not found: " + stockId));

            StockDividendDetails details = stockDividendDetailsRepo.findById(detailId)
                    .orElseThrow(() -> new IllegalStateException("Dividend details not found: " + detailId));

            Account account = accountRepo.findById(accountId)
                    .orElseThrow(() -> new IllegalStateException("Account not found: " + accountId));

            AccountStock accountStock = accountStockRepo
                    .findByAccountIdAndStockId(accountId, stockId)
                    .orElseThrow(() -> new IllegalStateException("AccountStock not found"));

            DividendFrequency frequency = dividendFrequencyRepo
                    .findById(stock.getPayoutFrequency())
                    .orElseThrow(() -> new IllegalStateException("Frequency not found"));

            return DividendEventResponseDTO.builder()
                    .id(event.getId().toString())
                    .account(account.getAccountNumber())
                    .amount(details.getDividendPerShare().multiply(accountStock.getShares()))
                    .currency(stock.getCurrencyCode())
                    .dividendPerShare(details.getDividendPerShare())
                    .frequency(String.valueOf(frequency.getPeriodsPerYear()))
                    .payoutDate(details.getPayoutDate())
                    .shares(accountStock.getShares())
                    .source("historical")
                    .stockId(stock.getId().toString())
                    .ticker(stock.getTicker())
                    .build();
        }).toList();
		return dividendEventResponse;
    }
    @Transactional
    public void addPayoutDate(DividendEventRequestDTO dto) {

        UUID stockId = UUID.fromString(dto.getStockId());

        // 1. Resolve stock
        Stock stock = stockRepo.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found: " + dto.getStockId()));

        // 2. Resolve account
        Account account = accountRepo.findByAccountNumber(dto.getAccount())
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + dto.getAccount()));

        // 3. Resolve frequency
        DividendFrequency frequency = dividendFrequencyRepo
                .findByPeriodsPerYear(Integer.valueOf(dto.getFrequency()))
                .orElseThrow(() -> new IllegalArgumentException("Frequency not found: " + dto.getFrequency()));

        // 4. Create NEW StockDividendDetails row
        StockDividendDetails details = StockDividendDetails.builder()
                .stockId(stock.getId())
                .dividendPerShare(dto.getDividendPerShare())
                .exDate(null) // client does not send ex-date for manual payout additions
                .payoutDate(LocalDate.parse(dto.getPayoutDate()))
                .createdDate(LocalDateTime.now())
                .lastUpdatedDate(LocalDateTime.now())
                .build();

        stockDividendDetailsRepo.save(details);

        // 5. Create NEW DividendEvent row
        DividendEvent event = DividendEvent.builder()
                .id(UUID.fromString(dto.getId()))
                .stockId(stock.getId())
                .stockDividendDetailId(details.getId())
                .accountId(account.getId())
                .sharesAtEvent(new BigDecimal(dto.getShares()))
                .fxRate(new BigDecimal(1.36))
                .totalAmount(dto.getAmount())
                .createdDate(LocalDateTime.now())
                .lastUpdatedDate(LocalDateTime.now())
                .build();

        dividendEventRepository.save(event);
    }

    

}
