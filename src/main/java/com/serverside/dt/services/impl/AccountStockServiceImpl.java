package com.serverside.dt.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serverside.dt.dtos.AccountStockDTO;
import com.serverside.dt.dtos.StockAccountProjectionDTO;
import com.serverside.dt.dtos.StockRequestDTO;
import com.serverside.dt.entities.Account;
import com.serverside.dt.entities.AccountStock;
import com.serverside.dt.entities.Currency;
import com.serverside.dt.entities.DividendEvent;
import com.serverside.dt.entities.DividendFrequency;
import com.serverside.dt.entities.Sector;
import com.serverside.dt.entities.Stock;
import com.serverside.dt.entities.StockDividendDetails;
import com.serverside.dt.mappers.AccountStockMapper;
import com.serverside.dt.repositories.AccountRepository;
import com.serverside.dt.repositories.AccountStockRepository;
import com.serverside.dt.repositories.CurrencyRepository;
import com.serverside.dt.repositories.DividendEventRepository;
import com.serverside.dt.repositories.DividendFrequencyRepository;
import com.serverside.dt.repositories.StockAccountViewCustomRepository;
import com.serverside.dt.repositories.StockAccountViewRepository;
import com.serverside.dt.repositories.StockDividendDetailsRepository;
import com.serverside.dt.repositories.StockRepository;
import com.serverside.dt.repositories.projections.StockAccountProjection;
import com.serverside.dt.services.AccountStockService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountStockServiceImpl implements AccountStockService {

    private final AccountStockRepository accountStockRepository;
    private final AccountRepository accountRepository;
    private final CurrencyRepository currencyRepository;
    private final StockRepository stockRepository;
    private final AccountStockMapper accountMapper;
    private final StockDividendDetailsRepository stockDividendDetailsRepository;
	private final DividendFrequencyRepository dividendFrequencyRepository;
	private final DividendEventRepository dividendEventRepository;
	private final StockAccountViewRepository stockAccountViewRepository;
	private final StockAccountViewCustomRepository stockAccountViewCustomRepository;

    @Override
    public List<AccountStockDTO> getAll() {
        return accountStockRepository.findAll()
                .stream()
                .map(accountMapper::toDTO)
                .toList();
    }

    @Override
    public AccountStockDTO getById(UUID id) {
        AccountStock entity = accountStockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AccountStock not found: " + id));
        return accountMapper.toDTO(entity);
    }

    @Override
    public List<AccountStockDTO> getByAccountId(UUID accountId) {
        return accountStockRepository.findByAccountId(accountId)
                .stream()
                .map(accountMapper::toDTO)
                .toList();
    }

    @Override
	public AccountStockDTO create(AccountStockDTO dto) {
    	dto.setCreatedDate(LocalDateTime.now());
    	dto.setLastUpdatedDate(LocalDateTime.now());
        AccountStock entity = accountMapper.toEntity(dto);
        AccountStock saved = accountStockRepository.save(entity);
        return accountMapper.toDTO(saved);
    }

    @Override
    public AccountStockDTO update(UUID id, AccountStockDTO dto) {
        AccountStock existing = accountStockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AccountStock not found: " + id));

        existing.setAccountId(dto.getAccountId());
        existing.setStockId(dto.getStockId());
        existing.setShares(dto.getShares());
        existing.setAveragePrice(dto.getAveragePrice());

        AccountStock updated = accountStockRepository.save(existing);
        return accountMapper.toDTO(updated);
    }

    @Override
    public void delete(UUID id) {
        if (!accountStockRepository.existsById(id)) {
            throw new RuntimeException("AccountStock not found: " + id);
        }
        accountStockRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void createNewStockAccount(StockRequestDTO dto) {

        // 1. Resolve account
        Account account = accountRepository.findByAccountNumber(dto.getAccount())
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + dto.getAccount()));

        // 2. Resolve currency
        Currency currency = currencyRepository.findByCode(dto.getCurrency())
                .orElseThrow(() -> new IllegalArgumentException("Currency not found: " + dto.getCurrency()));

        // 3. Resolve dividend frequency
        DividendFrequency frequency = dividendFrequencyRepository
                .findByPeriodsPerYear(Integer.valueOf(dto.getPayoutFrequency()))
                .orElseThrow(() -> new IllegalArgumentException("Frequency not found: " + dto.getPayoutFrequency()));

        // 4. Sector is optional / simple
        Sector sector = null; // or a default if you want
        // If you later add sector to the DTO, resolve it here:
        // sector = sectorRepository.findById(dto.getSectorId()).orElse(null);

        // 5. Create or update Stock
        UUID stockId = UUID.fromString(dto.getId());

        Stock stock = stockRepository.findById(stockId)
                .orElseGet(() -> Stock.builder()
                        .id(stockId)
                        .ticker(dto.getTicker())
                        .companyName(dto.getCompany())                        
                        //.exDate(dto.getExDividendDate() != null ? LocalDate.parse(dto.getExDividendDate()) : null)
                        .currencyCode(currency.getCode())
                        .createdDate(LocalDateTime.now())
                        .lastUpdatedDate(LocalDateTime.now())
                        .build()
                );
                

        stock.setTicker(dto.getTicker());
        stock.setCompanyName(dto.getCompany());        
        stock.setCurrencyCode(dto.getCurrency());
        stock.setPayoutFrequency(frequency.getId());
        stockRepository.save(stock);

        // 6. Create or update StockDividendDetails
        StockDividendDetails details = stockDividendDetailsRepository
                .findByStockId(stock.getId())
                .orElseGet(() -> StockDividendDetails.builder()
                        //.id(UUID.randomUUID())
                        .stockId(stock.getId())
                        .dividendPerShare(dto.getDividendPerShare())
                        //.dividendYield(dto.getDividendYield())
                        //.d(frequency.getId())
                        .exDate(dto.getExDividendDate() != null ? LocalDate.parse(dto.getExDividendDate()) : null)                        
                        .createdDate(LocalDateTime.now())
                        .lastUpdatedDate(LocalDateTime.now())
                        .build()
                );

        //details.setDividendPerShare(dto.getDividendPerShare());
       // details.setDividendYield(dto.getDividendYield());
       // details.setDividendFrequency(frequency);
       // details.setExDate(dto.getExDividendDate()!= null ? LocalDate.parse(dto.getExDividendDate()) : null);
       // details.setPayoutDate(dto.getLastPayoutDate() != null ? LocalDate.parse(dto.getLastPayoutDate()) : null);

        StockDividendDetails sdd = stockDividendDetailsRepository.save(details);

        // 7. Create or update AccountStock
        AccountStock accountStock = accountStockRepository
                .findByAccountIdAndStockId(account.getId(), stock.getId())
                .orElseGet(() -> AccountStock.builder()
                        .accountId(account.getId())
                        .stockId(stock.getId())
                        .createdDate(LocalDateTime.now())
                        .build()
                );

        accountStock.setShares(dto.getShares());
        accountStock.setPurchasedDate(dto.getPurchaseDate() != null ? LocalDate.parse(dto.getPurchaseDate()) : null);
        accountStock.setAveragePrice(dto.getAvgCost());
        accountStock.setLastUpdatedDate(LocalDateTime.now());

        accountStockRepository.save(accountStock);

       // LocalDate payoutDate = LocalDate.parse(dateStr);

        DividendEvent event = DividendEvent.builder()                        
                .stockId(stock.getId())
                .stockDividendDetailId(sdd.getId())
                .accountId(account.getId())                        
                .sharesAtEvent(dto.getShares())                        
                .totalAmount(dto.getDividendPerShare().multiply(dto.getShares()))                        
                .createdDate(LocalDateTime.now())
                .lastUpdatedDate(LocalDateTime.now())
                .build();

        dividendEventRepository.save(event);
        // 8. Persist historical payout dates
//        if (dto.getPayoutDates() != null) {
//            dto.getPayoutDates().forEach(dateStr -> {
//
//                LocalDate payoutDate = LocalDate.parse(dateStr);
//
//                DividendEvent event = DividendEvent.builder()                        
//                        .stockId(stock.getId())
//                        .stockDividendDetailId(sdd.getId())
//                        .accountId(account.getId())                        
//                        .sharesAtEvent(dto.getShares())                        
//                        .totalAmount(dto.getDividendPerShare().multiply(dto.getShares()))                        
//                        .createdDate(LocalDateTime.now())
//                        .lastUpdatedDate(LocalDateTime.now())
//                        .build();
//
//                dividendEventRepository.save(event);
//            });

        // 9. FXHistory (optional)
        // fxHistoryRepository.save(...)
   // }

    }
    @Transactional
    @Override
    public void updateStockAccount(StockRequestDTO dto) {

        // 1. Resolve account
        Account account = accountRepository.findByAccountNumber(dto.getAccount())
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + dto.getAccount()));

        // 2. Resolve currency
        Currency currency = currencyRepository.findByCode(dto.getCurrency())
                .orElseThrow(() -> new IllegalArgumentException("Currency not found: " + dto.getCurrency()));

        // 3. Resolve dividend frequency
        DividendFrequency frequency = dividendFrequencyRepository
                .findByPeriodsPerYear(Integer.valueOf(dto.getPayoutFrequency()))
                .orElseThrow(() -> new IllegalArgumentException("Frequency not found: " + dto.getPayoutFrequency()));

        // 4. Resolve stock
        UUID stockId = UUID.fromString(dto.getId());

        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found: " + dto.getId()));

        // ---- UPDATE STOCK ----
        stock.setTicker(dto.getTicker());
        stock.setCompanyName(dto.getCompany());
        stock.setCurrencyCode(currency.getCode());
        stock.setPayoutFrequency(frequency.getId());
        stock.setLastUpdatedDate(LocalDateTime.now());

        stockRepository.save(stock);

        // ---- UPDATE DIVIDEND DETAILS ----
        StockDividendDetails details = stockDividendDetailsRepository
                .findTopByStockIdOrderByLastUpdatedDateDesc(stockId)
                .orElseThrow(() -> new IllegalArgumentException("Dividend details not found for stock: " + dto.getId()));

        details.setDividendPerShare(dto.getDividendPerShare());
        details.setExDate(dto.getExDividendDate() != null ? LocalDate.parse(dto.getExDividendDate()) : null);
        details.setLastUpdatedDate(LocalDateTime.now());

        stockDividendDetailsRepository.save(details);

        // ---- UPDATE ACCOUNT STOCK ----
        AccountStock accountStock = accountStockRepository
                .findByAccountIdAndStockId(account.getId(), stockId)
                .orElseThrow(() -> new IllegalArgumentException("AccountStock not found"));

        accountStock.setShares(dto.getShares());
        accountStock.setAveragePrice(dto.getAvgCost());
        accountStock.setPurchasedDate(dto.getPurchaseDate() != null ? LocalDate.parse(dto.getPurchaseDate()) : null);
        accountStock.setSoldDate(dto.getSoldDate() != null && !dto.getSoldDate().equals("") ? LocalDate.parse(dto.getSoldDate()) : null);
        accountStock.setLastUpdatedDate(LocalDateTime.now());

        accountStockRepository.save(accountStock);
    }

    public List<StockAccountProjectionDTO> getAllStockAccountsFromView() {
    	List<StockAccountProjectionDTO> results = stockAccountViewCustomRepository.fetchAllFromView();
    	results.stream().forEach(result ->{
			BigDecimal dividendYield = calculateDividendYield(result);
			result.setDividendYield(dividendYield);
    	});
    	return results;
    }

    @Transactional
    public void deleteStockAccount(String stockIdStr) {

        UUID stockId = UUID.fromString(stockIdStr);

        // 1. Delete all dividend events for this stock
        dividendEventRepository.deleteByStockId(stockId);

        // 2. Delete all account-stock relationships
        accountStockRepository.deleteByStockId(stockId);

        // 3. Delete dividend details
        stockDividendDetailsRepository.deleteByStockId(stockId);

        // 4. Finally delete the stock itself
        stockRepository.deleteById(stockId);
    }

    private BigDecimal calculateDividendYield(StockAccountProjectionDTO row) {

        BigDecimal dividend = row.getDividendPerShare();
        String freqStr = row.getPayoutFrequency();
        BigDecimal avgCost = row.getAvgCost();

        // Null or zero protection
        if (dividend == null || avgCost == null || avgCost.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        if (freqStr == null || freqStr.isBlank()) {
            return BigDecimal.ZERO;
        }

        int frequency;
        try {
            frequency = Integer.parseInt(freqStr);
        } catch (NumberFormatException ex) {
            return BigDecimal.ZERO;
        }

        if (frequency <= 0) {
            return BigDecimal.ZERO;
        }

        // yield = (dividendPerShare * frequency) / avgCost
        BigDecimal yield = dividend
                .multiply(BigDecimal.valueOf(frequency))
                .divide(avgCost, 6, RoundingMode.HALF_UP); // internal precision

        // Convert to percentage with 2 decimals
        BigDecimal percent = yield
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP);

        return percent.compareTo(BigDecimal.ZERO) > 0 ? percent : BigDecimal.ZERO;
    }
}
